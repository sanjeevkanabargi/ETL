package pub.skanabargi.ETL

import java.util.Properties

import pub.skanabargi.code.mariaDBToBQ.URL
import org.apache.spark.sql.{DataFrame, SQLContext}

class JDBC(url: String, sc: SQLContext, jdbcConnProp: Properties)
    extends Serializable {

  def readTable(tableName: String): DataFrame = {
    return sc.read.jdbc(url, tableName, jdbcConnProp).coalesce(1)
  }

  def getDataFromTable(tableName: String): DataFrame = {

    println("sqlcontext is null.. !!!!" + sc)

    try {
      sc.emptyDataFrame
    } catch {

      case x: Exception => {

        // Display this if exception is found
      }
    }
    var dataframe_mysql = sc.emptyDataFrame
    try {
      dataframe_mysql = sc.read.jdbc(URL, tableName, jdbcConnProp)

    } catch {
      // Case statement
      case x: Exception => {

        println("Exception: Table not found.")
      }
    }

    return dataframe_mysql.coalesce(1)
  }

}
