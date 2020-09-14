package org.deutsche_telekom_it_solutions.java_school.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();

        webApplicationContext.setServletContext(servletContext);
        webApplicationContext.scan("org/deutsche_telekom_it_solutions/java_school/config");
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        ServletRegistration.Dynamic appServlet = servletContext
                .addServlet("mvc", new DispatcherServlet(webApplicationContext));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }

}
