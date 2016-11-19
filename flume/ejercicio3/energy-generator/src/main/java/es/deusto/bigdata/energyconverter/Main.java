package es.deusto.bigdata.energyconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import es.deusto.bigdata.flume.bean.DataPointEnergy;

public class Main {

	private static final String IN_FILE_PATH = "/tmp/data.csv";
	private final static File IN_FILE = new File(IN_FILE_PATH);
	private static final String OUT_FILE_PATH = "/tmp/out.csv";
	private final static File OUT_FILE = new File(OUT_FILE_PATH);

	public static void main(String[] args) throws Exception {
		if(OUT_FILE.exists()){
			recreate();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(IN_FILE))) {
			try (PrintWriter writer = new PrintWriter(OUT_FILE, "UTF-8")) {
				String line;
				while ((line = br.readLine()) != null) {
					DataPointEnergy dataPoint = new DataPointEnergy(line);
					writer.println(dataPoint.getCsvLine());
				}
			}
		}
	}

	private static void recreate() throws IOException {
		OUT_FILE.delete();
		OUT_FILE.createNewFile();
	}

}
