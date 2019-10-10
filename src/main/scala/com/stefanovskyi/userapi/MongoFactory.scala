package com.stefanovskyi.userapi

import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import com.typesafe.config.ConfigFactory

object MongoFactory {
  private val DATABASE = ConfigFactory.load().getString("userapi.mongodb.database")
  private val COLLECTION = ConfigFactory.load().getString("userapi.mongodb.collection")
  private val URI = ConfigFactory.load().getString("userapi.mongodb.uri")

  lazy val client: MongoClient = MongoClient(URI)
  lazy val database: MongoDatabase = client.getDatabase(DATABASE)
  lazy val collection: MongoCollection[Document] = database.getCollection(COLLECTION)
}