package pub.skanabargi.ETL

import org.apache.spark.sql.{SQLContext, SparkSession}
import com.spotify.spark.bigquery._

class Spark(master: String, appName: String) extends Serializable {

  val sparksession =
    SparkSession
      .builder()
      .master(master)
      .appName(appName)
      .getOrCreate()

  def getSparkSQLContext(): SQLContext = {

    val sqlContext = sparksession.sqlContext

    sqlContext.sql("set spark.sql.shuffle.partitions=1")

    // Set up GCP credentials
    sqlContext.setGcpJsonKeyFile("/path/to/GCP/key.json")

    // Set up BigQuery project and bucket
    sqlContext.setBigQueryProjectId("GCP_PROJ_ID")
    sqlContext.setBigQueryGcsBucket("GCPBucket")

    // Set up BigQuery dataset location, default is US
    sqlContext.setBigQueryDatasetLocation("GCP_PROJ_ID:BQ_DATASET")

    // Set the checkpoint direcotory
    // val yarnTags = sparkConf.get("spark.yarn.tags")
    //val jobId = yarnTags.split(",").filter(_.startsWith("dataproc_job")).head

    return sparksession.sqlContext
  }

}
