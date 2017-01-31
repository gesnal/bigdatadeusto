package es.desuto.bigdata.mapreduce.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordSizeCountMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    
    // protected to allow unit testing
    protected IntWritable wordsize = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
        	String word = tokenizer.nextToken();
        	int length = word.length();
            wordsize.set(length);
            context.write(wordsize, one);
        }
    }
}