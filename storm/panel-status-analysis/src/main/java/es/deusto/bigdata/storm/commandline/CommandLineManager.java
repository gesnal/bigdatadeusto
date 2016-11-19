package es.deusto.bigdata.storm.commandline;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class CommandLineManager {

	private final static GnuParser parser = new GnuParser();
	private final static Option option = new Option("c", "External configuration path");

	public static Config getConfig(String[] args) {
		Config externalConfig = ConfigFactory.empty();
		CommandLine cli;
		try {
			cli = getCommandLine(args);
			File configFile = new File(cli.getOptionValue(option.getValue()));
			externalConfig = externalConfig.withFallback(ConfigFactory.parseFile(configFile));
			return externalConfig;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return externalConfig;
	}

	private static CommandLine getCommandLine(String[] args) throws ParseException {
		Options options = new Options();

		options.addOption(option);
		return parser.parse(options, args);
	}

}
