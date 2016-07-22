name := "simple-vocabulary-teacher"
version := "1.0"
scalaVersion := "2.11.7"
lazy val `simple-vocabulary-teacher` = (project in file(".")).enablePlugins(PlayScala)
routesGenerator := InjectedRoutesGenerator
routesImport += "binders.PathBinders._"
routesImport += "binders.QueryStringBinders._"
com.typesafe.sbt.SbtScalariform.scalariformSettings
libraryDependencies += filters