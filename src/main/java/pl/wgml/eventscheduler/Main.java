package pl.wgml.eventscheduler;

import org.apache.catalina.startup.Tomcat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.helper.UserList;
import pl.wgml.eventscheduler.persistence.HibernateUtil;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Optional;

public class Main {

  public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

  public static void main(String[] args) throws Exception {

    hib();

    String contextPath = "/";
    String appBase = ".";
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(Integer.valueOf(port.orElse("8080") ));
    tomcat.getHost().setAppBase(appBase);
    tomcat.addWebapp(contextPath, appBase);
    tomcat.start();
    tomcat.getServer().await();
  }

  private static void hib() {
    SessionFactory sf = HibernateUtil.getSessionFactory();
    Session session = sf.openSession();
    List result = session.createQuery("from Invitation").list();
    session.close();
//
//    User user = UserList.getUsers().get(0);
//    session.beginTransaction();
//    session.save(user);
//    session.getTransaction().commit();
//    session.close();
    System.err.println(result);
  }
}
