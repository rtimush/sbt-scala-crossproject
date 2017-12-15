import java.io.ByteArrayOutputStream

lazy val check = taskKey[Unit]("check settings are applied")
lazy val expected = settingKey[String]("expected output")
lazy val stdout = settingKey[ByteArrayOutputStream]("run output")

def assertEquals[T](actual: T, expected: T): Unit = {
  assert(actual == expected, s"actual: $actual, expected: $expected")
}

lazy val bar =
  crossProject(ScalaPlatform("2.11"),
               ScalaPlatform("2.12"),
               ScalaPlatform("2.13"))
    .crossType(CrossType.Pure)
    .settings(
      description := "common settings"
    )
    .scalaSettings(
      description := "jvm settings"
    )
    .scalaSettings("2.11")(
      scalaVersion := "2.11.12",
      description := "2.11 settings"
    )
    .scalaSettings("2.12")(
      scalaVersion := "2.12.4",
      description := "2.12 settings"
    )

lazy val bar211 = bar.scala("2.11")
lazy val bar212 = bar.scala("2.12")
lazy val bar213 = bar.scala("2.13")

lazy val foo =
  crossProject(ScalaPlatform("2.11"), ScalaPlatform("2.12"))
    .crossType(CrossType.Pure)
    .dependsOn(bar)
    .settings(
      stdout := new ByteArrayOutputStream(),
      fork in run := true,
      outputStrategy := Some(CustomOutput(stdout.value)),
      check := {
        (run in Compile).toTask("").value
        assertEquals(stdout.value.toString.trim, expected.value)
      }
    )
    .scalaSettings("2.11")(
      scalaVersion := "2.11.12",
      expected := "2.11"
    )
    .scalaSettings("2.12")(
      scalaVersion := "2.12.4",
      expected := "2.12"
    )

lazy val foo211 = foo.scala("2.11")
lazy val foo212 = foo.scala("2.12")

check := {
  assertEquals((description in bar211).value, "2.11 settings")
  assertEquals((description in bar212).value, "2.12 settings")
  assertEquals((description in bar213).value, "jvm settings")
}
