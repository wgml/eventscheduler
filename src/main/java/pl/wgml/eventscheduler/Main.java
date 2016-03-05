package pl.wgml.eventscheduler;

import org.apache.catalina.startup.Tomcat;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

public class Main {

  public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

  public static void main(String[] args) throws Exception {
    ClassLoader cl = ClassLoader.getSystemClassLoader();

    URL[] urls = ((URLClassLoader)cl).getURLs();

    for(URL url: urls){
      System.out.println(url.getFile());
    }

    String contextPath = "/";
    String appBase = ".";
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(Integer.valueOf(port.orElse("8080") ));
    tomcat.getHost().setAppBase(appBase);
    tomcat.addWebapp(contextPath, appBase);
    tomcat.start();
    tomcat.getServer().await();
  }
}
