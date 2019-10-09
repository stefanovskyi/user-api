package com.stefanovskyi.userapi

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait User[F[_]]{
  def hello(n: User.Name): F[User.Greeting]
}

object User {
  implicit def apply[F[_]](implicit ev: User[F]): User[F] = ev

  final case class Name(name: String) extends AnyVal
  final case class Greeting(greeting: String) extends AnyVal

  object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
      final def apply(a: Greeting): Json = Json.obj(
        ("message", Json.fromString(a.greeting)),
      )
    }
    implicit def greetingEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_]: Applicative]: User[F] = new User[F]{
    def hello(n: User.Name): F[User.Greeting] =
        Greeting("Hello, " + n.name).pure[F]
  }
}