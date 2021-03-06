package com.barclays.indiacp;

import com.barclays.indiacp.dl.integration.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.axis.encoding.Base64;
import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.*;
import java.net.URI;

/**
 * Main class.
 * mvn exec:exec -Dexec.executable="java" -Dhost=localhost -Dport=5555 -Dpath=indiacp -Dexec.args="-classpath %classpath -Dhost=localhost -Dport=5555 -Dpath=indiacp -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 com.barclays.indiacp.Main"
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static String BASE_URI = null;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.barclays.indiacp.dl.integration package
        ResourceConfig rc = new ResourceConfig();
        rc.register(IndiaCPProgramApi.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(new IndiaCPProgramApiFactory()).to(IndiaCPProgramApi.class);
            }
        });
        rc.register(IndiaCPIssueApi.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(new IndiaCPIssueApiFactory()).to(IndiaCPIssueApi.class);
            }
        });
        rc.register(MultiPartFeature.class);
        rc.packages("com.barclays.indiacp.dl.integration");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
  public static void main(String[] args) throws IOException {
      String scheme = "http";
      String host = System.getProperty("host");
      String port = System.getProperty("port");
      String path = System.getProperty("path");
      if (host == null || port == null || path == null) {
          System.out.println("FAILURE: Cannot Start HTTP Server. Host and Port are not configured correctly.");
          System.out.println("Use the following command replacing the host and port to match the system requirements you are running on:\n" +
          "mvn exec:exec -Dexec.executable=\"java\" -Dexec.args=\"-classpath %classpath -Dhost=localhost -Dport=5555 -Dpath=indiacp com.barclays.indiacp.Main\"");
          System.out.println("OR\nUse the following command to start in the Debug Mode:\n" +
                  "mvn exec:exec -Dexec.executable=\"java\" -Dexec.args=\"-classpath %classpath -Dhost=localhost -Dport=5555 -Dpath=indiacp -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044 com.barclays.indiacp.Main\"");
          System.exit(0);
      }
      BASE_URI = scheme + "://" + host + ":" + port + "/" + path + "/";
      final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }


}
