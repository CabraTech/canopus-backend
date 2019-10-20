package com.cabratech.canopus.data.mongo

import com.mongodb.MongoClient
import org.bson.BsonDocument
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.json.JsonParseException
import org.bson.types.ObjectId

class MongoDataService(mongoClient: MongoClient, database: String) {
    private val database = mongoClient.getDatabase(database)

    fun allFromCollection(collection: String):
            ArrayList<Map<String, Any>> {
        val mongoResult =
            database.getCollection(collection, Document::class.java)
        val result = ArrayList<Map<String, Any>>()
        mongoResult.find()
            .forEach {
                val asMap: Map<String, Any> = mongoDocumentToMap(it)
                result.add(asMap)
            }
        return result
    }

    private fun mongoDocumentToMap(document: Document): Map<String, Any> {
        val asMap: MutableMap<String, Any> = document.toMutableMap()
        if (asMap.containsKey("_id")) {
            val id = asMap.getValue("_id")
            if (id is ObjectId) {
                asMap.set("_id", id.toHexString())
            }
        }
        return asMap
    }

    fun saveNewDocument(collection: String, document: String): String {
        try {
            val bsonDocument = BsonDocument.parse(document)
            // we create the id ourselves
            bsonDocument.remove("_id")
            val oid = ObjectId()
            bsonDocument.put("_id", BsonObjectId(oid))
            database.getCollection(collection, BsonDocument::class.java)
                .insertOne(bsonDocument)
            return oid.toHexString()
        } catch (ex: JsonParseException) {
            return "Invalid JSON: ${ex.localizedMessage}"
        }
    }

    fun getDocumentById(collection: String, id: String?): Map<String, Any>? {
        if (!ObjectId.isValid(id)) {
            return null
        }
        val document = database.getCollection(collection)
            .find(Document("_id", ObjectId(id)))
        if (document != null && document.first() != null) {
            return mongoDocumentToMap(document.first())
        }
        return null
    }

    fun updateExistingDocument(
        collection: String,
        id: String?,
        document: String
    ): Pair<Int, String> {
        try {
            if (!ObjectId.isValid(id)) {
                return Pair(0, "ID not found")
            }
            val bsonDocument = BsonDocument.parse(document)
            bsonDocument.remove("_id")
            val filter = BsonDocument("_id", BsonObjectId(ObjectId(id)))
            val updatedValues =
                database.getCollection(collection, BsonDocument::class.java)
                    .replaceOne(filter, bsonDocument).modifiedCount
            if (updatedValues < 1) {
                return Pair(0, "ID not found")
            } else {
                return Pair(1, "success")
            }
        } catch (ex: JsonParseException) {
            return Pair(-1, "Invalid JSON: ${ex.localizedMessage}")
        }
    }

    fun deleteDocument(collection: String, id: String?): Pair<Int, String> {
        if (!ObjectId.isValid(id)) {
            return Pair(0, "ID not found")
        }
        val filter = BsonDocument("_id", BsonObjectId(ObjectId(id)))
        val updatedValues = database.getCollection(collection)
            .deleteOne(filter).deletedCount
        if (updatedValues < 1) {
            return Pair(0, "ID not found")
        } else {
            return Pair(1, "success")
        }
    }
}


