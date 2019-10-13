package com.stefanovskyi.userapi

import cats.effect.{IO, Sync}
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher

object UserApiRoutes {
  object SortingParamMatcher extends QueryParamDecoderMatcher[String]("sort")

  def userRoutes[F[_]: Sync](H: UserService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "users" :? SortingParamMatcher(sort) =>
        Ok(s"Get all users sorted $sort")

      case GET -> Root / "users" =>
        Ok("Get all users")

      case POST -> Root / "users" =>
        Created("Create new user")
//        Conflict(s"User with $name already exist")

      case PUT -> Root / "users" =>
        Accepted("User has been edited")
//        BadRequest("There is no such user")

      case DELETE -> Root / "users" / id =>
        NoContent()
//        BadRequest(s"Delete user by id: $id")
    }
  }
}