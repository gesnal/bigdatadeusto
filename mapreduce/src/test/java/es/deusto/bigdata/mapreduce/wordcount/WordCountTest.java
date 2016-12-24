package es.deusto.bigdata.mapreduce.wordcount;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.file.tfile.Utils;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import es.deusto.bigdata.mapreduce.WordCountMapper;

public class WordCountTest extends MiniHadoopTestCase {

	public void test() throws Exception {
		JobConf conf = createJobConf();

		Path inDir = new Path("testing/jobconf/input");
		Path outDir = new Path("testing/jobconf/output");

		OutputStream os = getFileSystem().create(new Path(inDir, "text.txt"));
		Writer wr = new OutputStreamWriter(os);
		wr.write("b a\n");
		wr.close();

		conf.setJobName("mr");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(LongWritable.class);

		conf.setMapperClass(WordCountMapper.class);
		conf.setReducerClass(SumReducer.class);

		FileInputFormat.setInputPaths(conf, inDir);
		FileOutputFormat.setOutputPath(conf, outDir);

		assertTrue(JobClient.runJob(conf).isSuccessful());

		// Check the output is as expected
		Path[] outputFiles = FileUtil
				.stat2Paths(getFileSystem().listStatus(outDir, new Utils.OutputFileUtils.OutputFilesFilter()));

		assertEquals(1, outputFiles.length);

		InputStream in = getFileSystem().open(outputFiles[0]);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		assertEquals("a\t1", reader.readLine());
		assertEquals("b\t1", reader.readLine());
		assertNull(reader.readLine());
		reader.close();
	}

}
