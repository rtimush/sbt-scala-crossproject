package com.timushev.sbt.scalacrossproject

import sbt.Keys._
import sbt._
import sbtcrossproject.{CrossProject, Platform}

case class ScalaPlatform(version: String) extends Platform {
  def identifier: String = s"scala-$version"
  def sbtSuffix: String  = s"_${version.replace('.', '_')}"
  def enable(project: Project): Project =
    project
      .settings(
        Seq(Compile, Test).flatMap(inConfig(_) {
          unmanagedResourceDirectories ++= {
            unmanagedSourceDirectories.value
              .map(src => (src / ".." / "resources").getCanonicalFile)
              .filterNot(unmanagedResourceDirectories.value.contains)
              .distinct
          }
        })
      )

  @deprecated("Will be removed", "0.3.0")
  val crossBinary: CrossVersion = CrossVersion.binary

  @deprecated("Will be removed", "0.3.0")
  val crossFull: CrossVersion = CrossVersion.full
}

trait ScalaCross {

  def ScalaPlatform(version: String) =
    com.timushev.sbt.scalacrossproject.ScalaPlatform(version)

  implicit class ScalaCrossProjectOps(project: CrossProject) {

    def scala(version: String): Project =
      project.projects(ScalaPlatform(version))

    def scalaSettings(ss: Def.SettingsDefinition*): CrossProject =
      scalaConfigure(_.settings(ss: _*))

    def scalaSettings(version: String)(
        ss: Def.SettingsDefinition*): CrossProject =
      scalaConfigure(version)(_.settings(ss: _*))

    def scalaConfigure(transformer: Project => Project): CrossProject =
      project.configurePlatforms(
        project.projects.keys.filter(_.isInstanceOf[ScalaPlatform]).toSeq: _*
      )(transformer)

    def scalaConfigure(
        version: String
    )(transformer: Project => Project): CrossProject =
      project.configurePlatform(ScalaPlatform(version))(transformer)

  }

}
