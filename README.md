# Kafka Java Hello World

Sample program for Kafka implementation of Java. 
I create these samples. 

* Create a topic
* Producer 
* Consumer
* Producer / Consumer with Avro schema

# How to generate Avro schema

It should work with `mvn avro:schema` However, it ignores the configuration of the pom.

```
mvn avro:schema --debug -DoutputDirectory=/Users/ushio/Codes/Kafka/spike/src/main/java
```

