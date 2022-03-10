
val scala2_12 = "2.12.15"
val scala2_13 = "2.13.7"

lazy val `url-builder` = (project in file("."))
  .settings(majorVersion := 3)
  .settings(
    scalaVersion       := scala2_12,
    crossScalaVersions := Seq(scala2_12, scala2_13),
  )
  .settings(isPublicArtefact := true)
  .settings(
    libraryDependencies ++= PlayCrossCompilation.dependencies(
      shared = Seq(
        "org.scalatest"            %% "scalatest"             % "3.2.3"   % Test,
        "com.vladsch.flexmark"     %  "flexmark-all"          % "0.36.8"  % Test
      ),
      play28 = Seq(
        "com.typesafe.play"        %% "play"                  % "2.8.8",
        "com.typesafe.play"        %% "play-guice"            % "2.8.8",
        "com.typesafe.play"        %% "play-akka-http-server" % "2.8.8"   % Test,
        "org.scalatestplus.play"   %% "scalatestplus-play"    % "5.1.0"   % Test
      )
    )
  )
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(ScoverageSettings())
