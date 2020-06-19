package com.samples.ie

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext, SparkFiles}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

import java.util.Properties;
import java.net.URL

object SimpleIeApp {
  def main(args: Array[String]) {

    println("Executing job")

    // In Spark 2.4, if hosted on k8s and using --files http(s), the file will be downloaded to a directory
    // Use either org.apache.spark.SparkFiles.getRootDirectory() or org.apache.spark.SparkFiles.get(filename)
    // to get full path name

    val cl = Thread.currentThread.getContextClassLoader
    val url = cl.getResource("props.properties")
    val props = new Properties()
    props.load(url.openStream)

    val filename = props.getProperty("filename")

    // See: https://github.com/InsightEdge/insightedge-examples/tree/master/src/main/scala/org/insightedge/examples/basic
    val initConfig = InsightEdgeConfig.fromSparkConf(new SparkConf())

    val settings = Array( new SparkConf().get("spark.master", InsightEdgeConfig.SPARK_MASTER_LOCAL_URL_DEFAULT),
      initConfig.spaceName)

    val Array(master, space) = settings

    println(s"master: $master")
    println(s"space: $space")
    println(s"filename: $filename")

    val sc = SparkSession.builder
      .appName("SimpleIeApp")
      .master(master)
      .insightEdgeConfig(initConfig)
      .getOrCreate()

    //val fullpath = org.apache.spark.SparkFiles.get(filename)
    //println("SparkFiles.getRootDirectory: " + org.apache.spark.SparkFiles.getRootDirectory())
    //println("fullpath resolves to: " + fullpath)

    val logData = sc.read.textFile(filename).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")


    //spark.stop()
    sc.stopInsightEdgeContext()

  }
}
