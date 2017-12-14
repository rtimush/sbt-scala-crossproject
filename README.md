sbt-scala-crossproject [![Build Status](https://travis-ci.org/rtimush/sbt-scala-crossproject.svg?branch=master)](https://travis-ci.org/rtimush/sbt-scala-crossproject)
==================

An extension to [sbt-crossproject](https://github.com/portable-scala/sbt-crossproject) SBT plugin, that allows you
to cross-build for different scala versions.

Purpose
-------

The [sbt-crossproject](https://github.com/portable-scala/sbt-crossproject) plugin allows
you to cross-build for different platforms, such as JVM, Scala.js or Scala Native. This plugin
allows you to treat a particular scala versions as a platform,
so you can cross-build for "Scala 2.11 on JVM" and "Scala 2.12 on JVM".

SBT already has a built-in support for cross-compiling for multiple Scala versions,
but it stays aside from the regular tasks evaluation. With `sbt-scala-crossproject`
builds for different scala version are treated as a separate SBT modules.
This makes it possible to:

 - Build them in parallel,
 - Have different set of supported scala version for different submodules.

Example
-------

```
// Some library code cross-built for 2.11 and 2.12
lazy val lib =
  crossProject(ScalaPlatform("2.11"), ScalaPlatform("2.12"))
    .crossType(CrossType.Pure)
    .settings(/* ... */)
    .scalaSettings("2.11")(scalaVersion := "2.11.12")
    .scalaSettings("2.12")(scalaVersion := "2.12.4")

lazy val lib_2_11 = lib.scala("2.11")    
lazy val lib_2_12 = lib.scala("2.12")

// Apache Spark application built for 2.11 only
lazy val sparkApp =
  crossProject(ScalaPlatform("2.11"))
    .crossType(CrossType.Pure)
    .dependsOn(lib)
    .settings(/* ... */)
    .scalaSettings("2.11")(scalaVersion := "2.11.12")

lazy val sparkApp_2_11 = sparkApp.scala("2.11")
```
You can also look at the [plugin tests](https://github.com/rtimush/sbt-scala-crossproject/tree/master/src/sbt-test/).

Installation
------------

### Stable version
Add the following line to your `project/plugins.sbt`:

```
addSbtPlugin("com.timushev.sbt" % "sbt-scala-crossproject" % "0.1.0")
```

### Snapshot version
Choose one of versions available on [BinTray](https://bintray.com/rtimush/sbt-plugin-snapshots/sbt-scala-crossproject/view)
or the [latest](https://bintray.com/rtimush/sbt-plugin-snapshots/sbt-scala-crossproject/_latestVersion) one.
Then add the following line to your `project/plugins.sbt`:

```
resolvers += Resolver.bintrayIvyRepo("rtimush", "sbt-plugin-snapshots")
addSbtPlugin("com.timushev.sbt" % "sbt-scala-crossproject" % "x.x.x-y+gzzzzzzz")
```

Note, that snapshots are not updated automatically.