package com.stefanovskyi.userapi

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object UserApiRoutes {
  def userRoutes[F[_]: Sync](H: User[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "user" / name =>
        for {
          greeting <- H.hello(User.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }
}