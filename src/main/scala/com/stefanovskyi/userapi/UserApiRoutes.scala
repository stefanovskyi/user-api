package com.stefanovskyi.userapi

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.mongodb.scala.{Completed, Document, MongoCollection, Observable, Observer}

object UserApiRoutes {
  def userRoutes[F[_]: Sync](H: UserService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "user" / name =>

        val collection: MongoCollection[Document] = MongoFactory.collection

        val user1: Document = Document(
          "name" -> "bradly",
          "age" -> "29",
          "email" -> "bradly@example.com"
        )

        val insertObservable: Observable[Completed] = collection.insertOne(user1)

        insertObservable.subscribe(new Observer[Completed] {
          override def onNext(result: Completed): Unit = println(s"onNext: $result")
          override def onError(e: Throwable): Unit = println(s"onError: $e")
          override def onComplete(): Unit = println("onComplete")
        })

        println(collection.find().collect()
          .subscribe((results: Seq[Document]) => println(s"Found: #${results.size}"))
        )

        for {
          greeting <- H.hello(UserService.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }

    HttpRoutes.of[F] {
         case GET -> Root / "user" => Ok("Get All users")
       }

    HttpRoutes.of[F] {
         case GET -> Root / "user" / id => Ok(s"Get user by $id")
       }

    HttpRoutes.of[F] {
         case POST -> Root / "user" => Ok("Create new user")
       }

    HttpRoutes.of[F] {
         case DELETE -> Root / "user" / id => Ok(s"Delete user by id: $id")
       }
  }
}