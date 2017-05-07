
val commonSettings = Seq(
  scalaVersion := "2.12.2"
)

lazy val common = project
  .settings(commonSettings)
  .enablePlugins(ScalaJSPlugin)

lazy val timeout = project
  .settings(commonSettings)
  .settings(scalaJSUseMainModuleInitializer := true)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(common)

lazy val promise = project
  .settings(commonSettings)
  .settings(scalaJSUseMainModuleInitializer := true)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(common)

lazy val immediate = project
  .settings(commonSettings)
  .settings(scalaJSUseMainModuleInitializer := true)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(common)

lazy val root = project.aggregate(
  timeout,
  promise,
  immediate
)
