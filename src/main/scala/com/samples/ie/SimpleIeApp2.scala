package com.samples.ie


import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

object SimpleIeApp2 {
  def main(args: Array[String]) {

    println("Executing job")


    // See: https://github.com/InsightEdge/insightedge-examples/tree/master/src/main/scala/org/insightedge/examples/basic
    val initConfig = InsightEdgeConfig.fromSparkConf(new SparkConf())


    val settings = Array( new SparkConf().get("spark.master", InsightEdgeConfig.SPARK_MASTER_LOCAL_URL_DEFAULT),
      initConfig.spaceName)

    val Array(master, space) = settings

    println(s"master: $master")
    println(s"space: $space")

    val sc = SparkSession.builder
      .appName("SimpleIeApp")
      .master(master)
      .insightEdgeConfig(initConfig)
      .getOrCreate()

    val value1 = sc.sparkContext.getConf.get("spark.key1")
    println("Value1 is: " + value1)

    //spark.stop()
    sc.stopInsightEdgeContext()

  }
}
