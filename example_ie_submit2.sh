#!/usr/bin/env bash

# Example to submit uber jar on kubernetes


/home/dixson/work/ie/gigaspaces-insightedge-enterprise-15.2.0/insightedge/bin/insightedge-submit \
--master k8s://https://192.168.99.102:8443 \
--deploy-mode cluster \
--name SimpleIeApp2 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
--class com.samples.ie.SimpleIeApp2 \
--conf spark.kubernetes.container.image=gigaspaces/insightedge-enterprise:15.2 \
--properties-file prop.properties \
http://192.168.1.4:8888/insightedgeexamples3-1.0-SNAPSHOT-jar-with-dependencies.jar

