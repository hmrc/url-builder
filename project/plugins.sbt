resolvers += Resolver.bintrayIvyRepo("hmrc", "sbt-plugin-releases")
resolvers += Resolver.bintrayRepo("hmrc", "releases")

val playPlugin = sys.env.getOrElse("PLAY_VERSION", "2.8") match {
  case "2.6" => "com.typesafe.play" % "sbt-plugin" % "2.6.25"
  case "2.7" => "com.typesafe.play" % "sbt-plugin" % "2.7.9"
  case "2.8" => "com.typesafe.play" % "sbt-plugin" % "2.8.7"
}

addSbtPlugin(playPlugin)
addSbtPlugin("uk.gov.hmrc"     %  "sbt-auto-build"             % "2.13.0" )
addSbtPlugin("uk.gov.hmrc"     %  "sbt-git-versioning"         % "2.2.0"  )
addSbtPlugin("org.scoverage"   %  "sbt-scoverage"              % "1.6.1"  )
addSbtPlugin("org.scalastyle"  %% "scalastyle-sbt-plugin"      % "1.0.0"  )
addSbtPlugin("uk.gov.hmrc"     %  "sbt-play-cross-compilation" % "2.0.0"  )
addSbtPlugin("uk.gov.hmrc"     %  "sbt-artifactory"            % "1.13.0" )
