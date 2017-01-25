package es.desuto.bigdata.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        conf.set("yarn.resourcemanager.address", "bigd-hadoop2.deusto.es:8050"); // see step 3
        conf.set("mapreduce.framework.name", "yarn"); 
        conf.set("fs.defaultFS", "hdfs://bigd-hadoop2.deusto.es/"); // see step 2
        conf.set("yarn.application.classpath",        
                     "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,"
                        + "$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,"
                        + "$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,"
                        + "$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*");
        Job job = new Job(conf);
        job.setJarByClass(WordCount.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapperClass(WordCountMapper.class);
//        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path("C:\\tmp\\a.csv"));
        FileOutputFormat.setOutputPath(job, new Path("C:\\tmp\\b.csv"));

        return (job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new WordCount(), args);
        System.exit(res);
    }
}
