package com.stefanovskyi.userapi

import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object UserApiRoutes {
  def userRoutes[F[_]: Sync](H: UserService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpRoutes.of[F] {
         case GET -> Root / "users" => Ok("Get All users")
         case GET -> Root / "users" / id => Ok(s"Get user by $id")
         case POST -> Root / "users" => Created("Create new user")
         case PUT -> Root / "users" => Accepted("User has been edited")
         case DELETE -> Root / "users" / id => Ok(s"Delete user by id: $id")
    }
  }
}