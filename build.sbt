sbtPlugin := true

name := "sbt-scala-crossproject"
organization := "com.timushev.sbt"

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

scriptedLaunchOpts += s"-Dplugin.version=${version.value}"

crossSbtVersions := Seq("1.0.4")

enablePlugins(GitVersioning)
git.useGitDescribe := true
git.gitTagToVersionNumber := {
  case VersionNumber(Seq(x, y, z), Seq(), Seq()) => Some(s"$x.$y.$z")
  case VersionNumber(Seq(x, y, z), Seq(since, commit), Seq()) =>
    Some(s"$x.$y.${z + 1}-$since+$commit")
  case _ => None
}

addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "0.3.0")
