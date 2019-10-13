val Http4sVersion = "0.20.7"
val CirceVersion = "0.11.1"
val MongoDriverVersion = "2.7.0"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"
val typeSafeConfig = "1.2.1"

lazy val root = (project in file("."))
  .settings(
    organization := "com.stefanovskyi",
    name := "userapi",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-blaze-server"    % Http4sVersion,
      "org.http4s"            %% "http4s-blaze-client"    % Http4sVersion,
      "org.http4s"            %% "http4s-circe"           % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"             % Http4sVersion,
      "org.http4s"            %% "http4s-json4s-native"   % Http4sVersion,
      "org.http4s"            %% "http4s-json4s-jackson"  % Http4sVersion,
      "org.http4s"            %% "http4s-argonaut"        % Http4sVersion,
      "io.circe"              %% "circe-generic"          % CirceVersion,
      "io.circe"              %% "circe-literal"          % CirceVersion,
      "com.github.alexarchambault" %% "argonaut-shapeless_6.2" % "1.2.0-M6",
      "org.mongodb.scala"     %% "mongo-scala-driver"     % MongoDriverVersion,
      "com.typesafe"          % "config"                  % typeSafeConfig,
      "org.specs2"            %% "specs2-core"            % Specs2Version % "test",
      "ch.qos.logback"        %  "logback-classic"        % LogbackVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)
