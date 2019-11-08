package pub.skanabargi.ETL

import org.apache.spark.sql.{DataFrame, SQLContext}
import com.spotify.spark.bigquery._

class BQ(sparkSqlContext: SQLContext) {

  def writeDataFrameToBQ(df: DataFrame, bqTableName: String): Unit = {

    //Save data in google storage for hive

    //Push data into BQ.

    //Delete old data.

    df.saveAsBigQueryTable(bqTableName)
  }
}
