/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt.Keys._
import sbt._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.SbtArtifactory

object HmrcBuild extends Build {

  import _root_.play.core.PlayVersion
  import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
  import uk.gov.hmrc.SbtArtifactory.autoImport.makePublicallyAvailableOnBintray

  val appDependencies = Seq(
    "com.typesafe.play"        %% "play"               % PlayVersion.current % "provided",
    "org.scalatest"            %% "scalatest"          % "3.0.7" % "test",
    "org.scalatestplus.play"   %% "scalatestplus-play" % "3.0.0" % "test",
    "org.pegdown"              % "pegdown"             % "1.4.2" % "test"
  )

  lazy val `url-builder` = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
    .settings(majorVersion := 3)
    .settings(makePublicallyAvailableOnBintray := true)
    .settings(
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      crossScalaVersions := Seq("2.11.6")
    )
}
