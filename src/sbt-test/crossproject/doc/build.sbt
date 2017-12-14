// Some library code cross-built for 2.11 and 2.12
lazy val lib =
  crossProject(ScalaPlatform("2.11"), ScalaPlatform("2.12"))
    .crossType(CrossType.Pure)
    .settings(/* ... */)
    .scalaSettings("2.11")(scalaVersion := "2.11.12")
    .scalaSettings("2.12")(scalaVersion := "2.12.4")

lazy val `lib-2.11` = lib.scala("2.11")
lazy val `lib-2.12` = lib.scala("2.12")

// Apache Spark application built for 2.11 only
lazy val sparkApp =
  crossProject(ScalaPlatform("2.11"))
    .crossType(CrossType.Pure)
    .dependsOn(lib)
    .settings(/* ... */)
    .scalaSettings("2.11")(scalaVersion := "2.11.12")

lazy val `spark-app-2.11` = sparkApp.scala("2.11")