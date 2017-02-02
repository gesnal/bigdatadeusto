package es.deusto.bigdata.spark;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class WordCount {

	@SuppressWarnings("serial")
	public static void main(String[] args) throws Exception {
		String inputFile = "C:\\tmp\\spark\\input.txt";
		String outputFile = "C:\\tmp\\spark\\output";

		// Create a Java Spark Context.
		SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaRDD<String> inputFileLines = sc.textFile(inputFile);
		JavaRDD<String> words = inputFileLines.flatMap(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String inputFileLine) {
				return Arrays.asList(inputFileLine.split(" "));
			}
		});

		// Transform into word and count.
		JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
			@SuppressWarnings("unchecked")
			public Tuple2<String, Integer> call(String word) {
				return new Tuple2(word, 1);
			}
		}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer x, Integer y) {
				return x + y;
			}
		});
		// Save the word count back out to a text file, causing evaluation.
		counts.saveAsTextFile(outputFile);
	}
}
