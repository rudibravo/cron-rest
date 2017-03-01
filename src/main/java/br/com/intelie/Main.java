package br.com.intelie;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ext.ContextResolver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

import br.com.intelie.controller.Jobs;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/api/";

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws SchedulerException
	 */
	public static void main(String[] args) {

		try {
			System.out.println("Desafio para desenvolvedor Java Sr");

			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), createApp(),
					false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					Jobs.getInstance().getScheduler().stop();
					server.shutdownNow();
				}
			}));
			server.start();

			System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

			Thread.currentThread().join();

		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static ResourceConfig createApp() {
		return new ResourceConfig().packages("br.com.intelie").register(createMoxyJsonResolver());
	}

	public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
		final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
		Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
		namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
		return moxyJsonConfig.resolver();
	}
}
