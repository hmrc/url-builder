

lazy val `url-builder` = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(majorVersion := 3)
  .settings(scalaVersion := "2.12.10")
  .settings(makePublicallyAvailableOnBintray := true)
  .settings(
    libraryDependencies ++= PlayCrossCompilation.dependencies(
      shared = Seq(
        "org.scalatest"            %% "scalatest"          % "3.0.7" % "test",
        "org.pegdown"              % "pegdown"             % "1.4.2" % "test"
      ),
      play25 = Seq(
        "com.typesafe.play"        %% "play"               % "2.5.19",
        "org.scalatestplus.play"   %% "scalatestplus-play" % "2.0.1" % "test"
      ),
      play26 = Seq(
        "com.typesafe.play"        %% "play"               % "2.6.20",
        "com.typesafe.play"        %% "play-guice"         % "2.6.20",
        "org.scalatestplus.play"   %% "scalatestplus-play" % "3.1.2" % "test"
      ),
      play27 = Seq(
        "com.typesafe.play"        %% "play"               % "2.7.4",
        "com.typesafe.play"        %% "play-guice"         % "2.7.4",
        "org.scalatestplus.play"   %% "scalatestplus-play" % "4.0.3" % "test"
      )
    ),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases"),
      "typesafe-releases" at "https://repo.typesafe.com/typesafe/releases/"
    ),
    crossScalaVersions := Seq("2.11.12", "2.12.10")
  ).settings(PlayCrossCompilation.playCrossCompilationSettings)
