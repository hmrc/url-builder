import play.core.PlayVersion

lazy val `url-builder` = (project in file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .disablePlugins(PlayLayoutPlugin)
  .settings(majorVersion := 4)
  .settings(scalaVersion := "2.12.13")
  .settings(makePublicallyAvailableOnBintray := true)
  .settings(
    libraryDependencies ++= PlayCrossCompilation.dependencies(
      shared = Seq(
        "com.typesafe.play"        %% "play"               % PlayVersion.current,
        "com.typesafe.play"        %% "play-guice"         % PlayVersion.current,
        "org.scalatest"            %% "scalatest"          % "3.2.3"             % "test",
        "com.vladsch.flexmark"     %  "flexmark-all"       % "0.36.8"            % "test"
      ),
      play26 = Seq(
        "org.scalatestplus.play"   %% "scalatestplus-play" % "3.1.2"             % "test"
      ),
      play27 = Seq(
        "org.scalatestplus.play"   %% "scalatestplus-play" % "4.0.3"             % "test"
      ),
      play28 = Seq(
        "org.scalatestplus.play"   %% "scalatestplus-play" % "5.0.0"             % "test"
      )
    )
  ).settings(PlayCrossCompilation.playCrossCompilationSettings)
