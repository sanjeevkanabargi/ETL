package pub.skanabargi.code

import java.util.Properties

import org.apache.spark.sql.Row
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import com.spotify.spark.bigquery._
import org.apache.spark.sql.functions.max
import pub.skanabargi.ETL.{JDBC, Spark}
import org.apache.spark.sql.functions.{col, _}
import pub.skanabargi.ETL.{BQ, JDBC, Spark}

object mariaDBToBQ {

  val URL = "jdbc:mysql://localhost:3306/?zeroDateTimeBehavior=convertToNull"
  val DRIVER = "driver"
  val USER = "user"
  val PASS = "password"

  //Spark code to count words in file.
  def main(args: Array[String]) {

    val spark = new Spark("local[2]", "Testing")
    val sparkSqlContext = spark.getSparkSQLContext()
    val JDBCObj = new JDBC(URL, sparkSqlContext, getJDBCConnectionProp())
    val BQObj = new BQ(sparkSqlContext)

    //Preparing Global app scores.
    //READ : app_info.applications
    val table1 =
      JDBCObj
        .getDataFromTable("MY_SQL_DBNAME.table1")
        .withColumn("newColumn", lit("global"))

    //READ : app_info.reasearch_data
    val table2 = JDBCObj
      .getDataFromTable("MY_SQL_DBNAME.table2")
      .withColumnRenamed("id", "application_id")
      .withColumnRenamed("AppName", "application_name")

    //Join all read DFs.
    val joinedData =
      (table1
        .join(table2, Seq("col1", "col2"), "left"))

    BQObj.writeDataFrameToBQ(joinedData, "GCPPROJID:DATASET.BQ_TABLE_NAME")

  }

  def getTenantAppRelatedUpdates(JDBCObj: JDBC, spark: Spark): DataFrame = {

    val databases =
      JDBCObj
        .getDataFromTable("information_schema.tables")
        .select("table_schema")
        .where(col("table_schema").like("regExForDB"))

    var updatedTenantApp = spark.sparksession.emptyDataFrame

    val listOfDB = databases.collect().toList

    for (db <- listOfDB) {

      //for each tenantDB get appIDs which are changed from tenantIDdb.app_cci table.
      val database = db.get(0)
      println("Reading : " + database + ".table1")
      var tenantAppCCI = JDBCObj
        .getDataFromTable(database + ".table1")
        .select("columnsCommaSeperated")
        .distinct()
    }

    return updatedTenantApp
  }

  def getJDBCConnectionProp(): Properties = {
    var prop = new Properties()

    prop.setProperty(USER, "yourMySQLUSER")
    prop.setProperty(PASS, "Password")
    prop.setProperty(DRIVER, "com.mysql.jdbc.Driver")

    prop

  }

}
