package com.stefanovskyi.userapi

import cats.Applicative
import cats.implicits._
import com.stefanovskyi.userapi.UserService.User
import io.circe.literal._
import io.circe.{Encoder}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait UserService[F[_]]{
  def getUser: F[User]
}

object UserService {
  implicit def apply[F[_]](implicit ev: UserService[F]): UserService[F] = ev

  case class User(name: String, age: Int, email: String)

  object User {
    implicit val userEncoder: Encoder[User] =
      Encoder.instance { user: User =>
        json"""{
              "name": ${user.name},
              "age": ${user.age},
              "email": ${user.email}
              }"""
      }

    implicit def userEntityEncoder[F[_] : Applicative]: EntityEncoder[F, User] =
      jsonEncoderOf[F, User]
  }

  def impl[F[_]: Applicative]: UserService[F] = new UserService[F]{
    def getUser: F[User] =
        User("tom", 21, "tom21@gmail.com").pure[F]
  }
}