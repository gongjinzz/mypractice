import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object Demo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("demo").setMaster("local[2]")

    val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()

    import org.elasticsearch.spark.sql._
    val options = Map(
      "es.nodes.wan.only" -> "true",
      "es.nodes" -> "192.168.91.4",
      "es.port" -> "19270",
      "es.mapping.id" -> "name"
    )

    import org.apache.spark.sql.functions._

    val df = spark.table("demo.person")

    val df2 = df.withColumn("column", lit("456"))

    df2.show()

    println("=============write into es=============")
    //df 写入 es
    df2.write
      .format("es")
      .options(options)
      .mode(SaveMode.Overwrite)
      .save("hive_table/docs")

    println("=============read from es==================")
    spark
      .read
      .format("es")
      .options(options)
      .load("hive_table/docs").show()

  }
}
