package com.stefanovskyi.userapi

import cats.effect.Sync
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

      case PUT -> Root / "users" =>
        Accepted("User has been edited")

      case DELETE -> Root / "users" / id =>
        Ok(s"Delete user by id: $id")
    }
  }
}