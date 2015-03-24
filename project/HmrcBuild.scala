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


object HmrcBuild extends Build {

  import _root_.play.core.PlayVersion
  import uk.gov.hmrc.DefaultBuildSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val appVersion = "0.8.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.typesafe.play" %% "play" % PlayVersion.current % "provided",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    "org.pegdown" % "pegdown" % "1.4.2" % "test"
  )

  lazy val `url-builder` = (project in file("."))
    .settings(version := appVersion)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(appVersion),
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      crossScalaVersions := Seq("2.11.6")
    )
    .settings(SbtBuildInfo(): _*)
    .settings(POMMetadata())
    .settings(Headers(): _ *)
}

object Headers {
  import de.heikoseeberger.sbtheader.SbtHeader.autoImport._
  def apply() = Seq(
      headers := Map(
          "scala" ->(
              HeaderPattern.cStyleBlockComment,
              """|/*
                 | * Copyright 2015 HM Revenue & Customs
                 | *
                 | * Licensed under the Apache License, Version 2.0 (the "License");
                 | * you may not use this file except in compliance with the License.
                 | * You may obtain a copy of the License at
                 | *
                 | *   http://www.apache.org/licenses/LICENSE-2.0
                 | *
                 | * Unless required by applicable law or agreed to in writing, software
                 | * distributed under the License is distributed on an "AS IS" BASIS,
                 | * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                 | * See the License for the specific language governing permissions and
                 | * limitations under the License.
                 | */
                 |
                 |""".stripMargin
                )
          ),
      (compile in Compile) <<= (compile in Compile) dependsOn (createHeaders in Compile),
      (compile in Test) <<= (compile in Test) dependsOn (createHeaders in Test)
    )
 }

object POMMetadata {
  def apply() = {
      pomExtra :=
        <url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
          <licenses>
            <license>
              <name>Apache 2</name>
              <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
            </license>
          </licenses>
          <scm>
            <connection>scm:git@github.com:hmrc/url-builder.git</connection>
            <developerConnection>scm:git@github.com:hmrc/url-builder.git</developerConnection>
            <url>git@github.com:hmrc/url-builder.git</url>
          </scm>
          <developers>
            <developer>
              <id>steve-e</id>
              <name>Steve Etherington</name>
              <url>http://www.equalexperts.com/</url>
            </developer>
          </developers>
  }
}
