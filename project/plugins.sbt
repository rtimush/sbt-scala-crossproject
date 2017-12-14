addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.14")
libraryDependencies += { "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value }