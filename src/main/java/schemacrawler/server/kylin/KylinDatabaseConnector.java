package schemacrawler.server.kylin;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import schemacrawler.tools.iosource.ClasspathInputResource;

public final class KylinDatabaseConnector extends DatabaseConnector {

	public KylinDatabaseConnector() throws IOException {

		super(new DatabaseServerType("kylin", "Apache Kylin"),
				new ClasspathInputResource("/schemacrawler-kylin.config.properties"), (informationSchemaViewsBuilder,
						connection) -> informationSchemaViewsBuilder.fromResourceFolder("/kylin.information_schema"));
		try {
			Class.forName("org.apache.kylin.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Could not load Kylin JDBC driver", e);
		}
	}

	@Override
	public PluginCommand getHelpCommand() {
		final PluginCommand pluginCommand = super.getHelpCommand();
		pluginCommand.addOption("server", "--server=kylin%n" + "Loads SchemaCrawler plug-in for Apache Kylin", String.class)
				.addOption("host", "Host name%n" + "Optional, defaults to localhost", String.class)
				.addOption("port", "Port number%n" + "Optional, defaults to 3306", Integer.class)
				.addOption("database", "Database name", String.class);
		return pluginCommand;
	}

	@Override
	protected Predicate<String> supportsUrlPredicate() {
		return url -> Pattern.matches("jdbc:kylin:.*", url);
	}

}
