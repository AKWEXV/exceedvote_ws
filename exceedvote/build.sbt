name := "exceedvote"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.jayway.restassured" % "rest-assured" % "2.1.0"
)

play.Project.playJavaSettings
