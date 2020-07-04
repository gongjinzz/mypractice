import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.SparkSession
import org.apache.phoenix.spark._

object Demo2 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Demo2").setMaster("local[2]")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    //    var ds = spark.read.format("org.apache.phoenix.spark")
    //      .option("table", "itxw.table")
    //      .option("zkUrl", "10.12.51.187:2181,10.12.51.188:2181,10.12.51.189:2181").load
    //
    //    ds.show()
    //
    //
    import org.apache.phoenix.spark._
    //    ds.saveToPhoenix("")


    //    val rdd: RDD[Map[String, AnyRef]] = spark.sparkContext.phoenixTableAsRDD("t", Seq("name"),
    //      zkUrl = Some("10.12.51.187:2181,10.12.51.188:2181,10.12.51.189:2181"))
    //
    //    rdd.foreach(a => {
    //      println(a)
    //    })

    //    val df = spark.sqlContext.read.format("jdbc")
    //      .option("driver", "org.apache.phoenix.jdbc.PhoenixDriver")
    //      .option("url", "jdbc:phoenix:mini1:2181")
    //      .option("dbtable", "test")
    //      .load()

    //    import org.apache.phoenix.spark.datasource.v2.PhoenixDataSource
    //    val df = spark.sqlContext
    //      .read
    //      .format("phoenix")
    //      .options(Map("table" -> "TABLE1", PhoenixDataSource.ZOOKEEPER_URL -> "phoenix-server:2181"))
    //      .load


    //    val df = spark.sqlContext.phoenixTableAsDataFrame(
    //      "test:student",
    //      Seq("info"),
    //      zkUrl = Some("mini1,mini2,mini3:2181")
    //    )

    //    df.show()


    val conf2 = new Configuration()
    conf2.addResource("phoenix/core-site.xml")
    conf2.addResource("phoenix/hbase-site.xml")
    conf2.addResource("phoenix/hdfs-site.xml")


    val tableName = "student"

    val df = spark.sqlContext.phoenixTableAsDataFrame(
      tableName,
      Seq("info"),
      conf = conf2,
      zkUrl = Some("mini1,mini2,mini3:2181")
    )
    println(df.first())
  }
}
