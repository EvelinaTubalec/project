package com.leverx.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class DispatcherServletInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(ApplicationConfig.class);

    ServletRegistration.Dynamic servlet =
        servletContext.addServlet("dispatcher", new DispatcherServlet(context));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");
  }
}
