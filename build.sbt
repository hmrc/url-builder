
lazy val `url-builder` = (project in file("."))
  .settings(majorVersion := 3)
  .settings(
    scalaVersion       := "2.13.12"
  )
  .settings(isPublicArtefact := true)
  .settings(libraryDependencies ++= Seq(
    "org.playframework" %% "play" % "3.0.2",
    "uk.gov.hmrc" %% s"bootstrap-test-play-30" % "8.5.0" % Test
  ))
  .settings(ScoverageSettings())