ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"
lazy val root = (project in file("."))
  .settings(
    name := "ProyectoB2_1",
    libraryDependencies ++= Seq(
      "io.reactivex" %% "rxscala" % "0.27.0",
      "com.lihaoyi" %% "scalarx" % "0.4.1",
      "com.nrinaudo" %% "kantan.csv" % "0.6.1",
      "com.nrinaudo" %% "kantan.csv-generic" % "0.6.1",
      "com.typesafe.play" %% "play-json" % "2.9.2"
    )
  )
