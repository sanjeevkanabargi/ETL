name := "mariaDBtoBQ"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"
//val bigQueryVersion = "1.23.0"
//val googleApiBigQueryVersion = s"v2-rev390-$bigQueryVersion"
//val gcsNioVersion = "0.54.0-alpha"
//val avro4sVersion = "1.9.0"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  // "org.apache.spark" %% "spark-mllib" % sparkVersion,
  // "org.apache.spark" %% "spark-streaming" % sparkVersion,
  //"org.apache.spark" %% "spark-hive" % sparkVersion,
  //"com.google.cloud" % "google-cloud-bigquery" % bigQueryVersion,
  // "com.google.apis" % "google-api-services-bigquery" % googleApiBigQueryVersion,
  //"com.google.cloud" % "google-cloud-nio" % gcsNioVersion,
  //"com.sksamuel.avro4s" %% "avro4s-core" % avro4sVersion
)

// https://mvnrepository.com/artifact/com.google.cloud.bigdataoss/bigquery-connector
//libraryDependencies += "com.google.cloud.bigdataoss" % "bigquery-connector" % "0.10.0-hadoop2"

// https://mvnrepository.com/artifact/com.spotify/spark-bigquery
libraryDependencies += "com.spotify" %% "spark-bigquery" % "0.2.2"

//libraryDependencies += "com.google.apis" % "google-api-services-pubsub" % "v1-rev425-1.25.0"

// https://mvnrepository.com/artifact/org.apache.bahir/spark-streaming-pubsub
//libraryDependencies += "org.apache.bahir" %% "spark-streaming-pubsub" % "2.3.0"

// https://mvnrepository.com/artifact/org.scala-lang/scala-library
libraryDependencies += "org.scala-lang" % "scala-library" % "2.10.0-M3"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
