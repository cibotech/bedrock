import xerial.sbt.Sonatype._

enablePlugins(ScalaJSPlugin)
import _root_.org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._

val projectName = "bedrock"
val scalaV = "2.12.4"
val org = "io.github.cibotech"
val scalatestV = "3.0.1"

publishArtifact := false

crossScalaVersions := Seq("2.12.4")

val noPublish: Seq[Setting[_]] = Seq(
    publishArtifact := false,
    publish / skip := true,
    publishLocal := {}
)

lazy val publishSettings: Seq[Setting[_]] = Seq(
  organization := org,
  organizationName := "CiBO Technologies",
  organizationHomepage := Some(new java.net.URL("http://www.cibotechnologies.com")),
  licenses += ("BSD 3-Clause", url("https://opensource.org/licenses/BSD-3-Clause")),
  sonatypeProjectHosting := Some(GitHubHosting("cibotech", "bedrock", "devops@cibotechnologies.com")),
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeProfileName := "io.github.cibotech",
  pomIncludeRepository := { _ => false },
  publishTo := sonatypePublishToBundle.value,
  publishMavenStyle := true)

lazy val `bedrock-root` = project
  .in(file("."))
  .aggregate(bedrockJS, bedrockJVM, `bedrock-plots`)
  .settings(licenseSettings)
  .settings(noPublish)

val commonSettings = Seq(
  organization := org
)

lazy val sharedLibs = Seq()



lazy val licenseSettings = Seq(
  homepage := Some(url("https://www.github.com/cibotech/bedrock")),
  startYear := Some(2018),
  description := "A Scala combinator-based ui library.",
  headerLicense := Some(HeaderLicense.BSD3Clause("2018", "CiBO Technologies, Inc."))
)

lazy val scalaJSReact = Seq(
  "com.github.japgolly.scalajs-react" %%%! "core" % "1.1.0",
  "com.github.japgolly.scalajs-react" %%%! "extra" % "1.1.0",
  "com.github.japgolly.scalajs-react" %%%! "test" % "1.1.0" % "test"
)

lazy val reactJSDependency = Seq(
  "org.webjars.bower" % "react" % "15.6.1"
    / "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",
  "org.webjars.bower" % "react" % "15.6.1"
    / "react-dom.js"
    minified "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",
  "org.webjars.bower" % "react" % "15.6.1"
    / "react-dom-server.js"
    minified "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer"
)

lazy val bedrock = crossProject
  .in(file("."))
  .settings(publishSettings)
  .settings(licenseSettings)
  .settings(
    name := projectName,
    organization := org,
    scalaVersion := scalaV,
    libraryDependencies ++= sharedLibs, // Shared within cross-project
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions"),
    crossScalaVersions := Seq("2.12.4"),
    scalaJSUseMainModuleInitializer := true,
    mainClass in Compile := Some("com.cibo.uidocumentation.DocumentationApp"),
  )
  .settings(commonSettings)
  .jvmSettings(
    libraryDependencies ++= Seq() // JVM Only
  )
  .jsSettings(
    jsDependencies ++= reactJSDependency,
    jsEnv in Test := new PhantomJS2Env(scalaJSPhantomJSClassLoader.value),
    libraryDependencies ++= Seq(
      "org.scala-js" %%%! "scalajs-dom" % "0.9.3",
      "io.github.cquiroz" %%%! "scala-java-time" % "2.0.0-M13",
      "io.github.cquiroz" %%%! "scala-java-time-tzdb" % "2.0.0-M13_2018c",
      "org.scalatest" %%%! "scalatest" % scalatestV % "test"
    ),
    libraryDependencies ++= scalaJSReact
  ) // JS Only

lazy val `bedrock-plots` = project
  .in(file("bedrock-plots"))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(licenseSettings)
  .settings(
    name := "bedrock-plots",
    jsDependencies ++= reactJSDependency,
    scalaVersion := scalaV,
    jsEnv in Test := new PhantomJS2Env(scalaJSPhantomJSClassLoader.value),
    libraryDependencies ++= Seq(
      "com.cibo" %%%! "evilplot" % "0.4.1"
    ),
    libraryDependencies ++= scalaJSReact,
    resolvers ++= Seq(
      "Artifactory Realm" at "https://cibotech.jfrog.io/cibotech/libs-local",
      Resolver.bintrayRepo("cibotech", "public")
    )
  )
  .enablePlugins(ScalaJSPlugin)

lazy val shared = Project(s"$projectName-shared", file("shared"))
  .settings(
    commonSettings ++ Seq(
      publishArtifact := false,
      libraryDependencies ++= sharedLibs ++ Seq()
    ))

lazy val bedrockJVM = bedrock.jvm
lazy val bedrockJS = bedrock.js
