enablePlugins(ScalaJSPlugin)
import _root_.org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._

val projectName = "bedrock"
val scalaV = "2.12.4"
val org = "com.cibo"
val scalatestV = "3.0.1"

publishArtifact := false

lazy val buildVersion =
  sys.env.getOrElse("TRAVIS_BUILD_NUMBER", (System.currentTimeMillis() / 1000).toString)

val versionNumber = s"0.2.$buildVersion"
crossScalaVersions := Seq("2.12.4")

lazy val `bedrock-root` = project
  .in(file("."))
  .aggregate(bedrockJS, bedrockJVM, `bedrock-plots`)
  .settings(
    publishArtifact := false
  )

val commonSettings = Seq(
  version := versionNumber,
  organization := org
)

lazy val sharedLibs = Seq()


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
