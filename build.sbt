
lazy val `url-builder` = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(majorVersion := 3)
  .settings(scalaVersion := "2.12.13")
  .settings(makePublicallyAvailableOnBintray := true)
  .settings(
    libraryDependencies ++= PlayCrossCompilation.dependencies(
      shared = Seq(
        "org.scalatest"            %% "scalatest"             % "3.2.3"   % Test,
        "com.vladsch.flexmark"     %  "flexmark-all"          % "0.36.8"  % Test
      ),
      play26 = Seq(
        "com.typesafe.play"        %% "play"                  % "2.6.25",
        "com.typesafe.play"        %% "play-guice"            % "2.6.25",
        "org.scalatestplus.play"   %% "scalatestplus-play"    % "3.1.2"   % Test
      ),
      play27 = Seq(
        "com.typesafe.play"        %% "play"                  % "2.7.9",
        "com.typesafe.play"        %% "play-guice"            % "2.7.9",
        "org.scalatestplus.play"   %% "scalatestplus-play"    % "4.0.3"   % Test
      ),
      play28 = Seq(
        "com.typesafe.play"        %% "play"                  % "2.8.7",
        "com.typesafe.play"        %% "play-guice"            % "2.8.7",
        "com.typesafe.play"        %% "play-akka-http-server" % "2.8.7"   % Test,
        "org.scalatestplus.play"   %% "scalatestplus-play"    % "5.0.0"   % Test
      )
    )
  ).settings(PlayCrossCompilation.playCrossCompilationSettings)
