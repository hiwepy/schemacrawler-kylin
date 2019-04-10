package schemacrawler.server.kylin;

import java.io.IOException;
import java.util.regex.Pattern;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.iosource.ClasspathInputResource;

public final class KylinDatabaseConnector extends DatabaseConnector {
	
	protected KylinDatabaseConnector() throws IOException {
		super(new DatabaseServerType("kylin", "Apache Kylin"),
				new ClasspathInputResource("/help/Connections.kylin.txt"),
				new ClasspathInputResource("/schemacrawler-kylin.config.properties"), (informationSchemaViewsBuilder,
						connection) -> informationSchemaViewsBuilder.fromResourceFolder("/kylin.information_schema"),
				url -> Pattern.matches("jdbc:kylin:.*", url));
		try {
			Class.forName("org.apache.kylin.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Could not load Kylin JDBC driver", e);
		}
	}

}
