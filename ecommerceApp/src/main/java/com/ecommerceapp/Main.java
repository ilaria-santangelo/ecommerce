package com.ecommerceapp;

import com.ecommerceapp.servlet.*;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.servlets.DefaultServlet;

import java.io.File;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        String contextPath = "";
        String docBase = new File("").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        Tomcat.addServlet(context, "registerServlet", new RegisterServlet());
        context.addServletMappingDecoded("/register", "registerServlet");

        Tomcat.addServlet(context, "userServlet", new UserServlet());
        context.addServletMappingDecoded("/userServlet", "userServlet");

        Tomcat.addServlet(context, "loginServlet", new UserServlet());
        context.addServletMappingDecoded("/loginServlet", "loginServlet");

        Tomcat.addServlet(context, "productServlet", new ProductServlet());
        context.addServletMappingDecoded("/productServlet", "productServlet");

        Tomcat.addServlet(context, "getProductServlet", new GetProductServlet());
        context.addServletMappingDecoded("/getProductServlet", "getProductServlet");

        Tomcat.addServlet(context, "getImageServlet", new GetImageServlet());
        context.addServletMappingDecoded("/getImageServlet", "getImageServlet");

        Tomcat.addServlet(context, "getSingleProductServlet", new GetSingleProductServlet());
        context.addServletMappingDecoded("/getSingleProductServlet", "getSingleProductServlet");

        Tomcat.addServlet(context, "orderServlet", new OrderServlet());
        context.addServletMappingDecoded("/orderServlet", "orderServlet");

        Tomcat.addServlet(context, "getOrdersServlet", new GetOrdersServlet());
        context.addServletMappingDecoded("/getOrdersServlet", "getOrdersServlet");

        Tomcat.addServlet(context, "getProductsCustomer", new GetProductsCustomer());
        context.addServletMappingDecoded("/getProductsCustomer", "getProductsCustomer");

        Tomcat.addServlet(context, "getOrdersCustomerServlet", new GetOrdersCustomerServlet());
        context.addServletMappingDecoded("/getOrdersCustomerServlet", "getOrdersCustomerServlet");

        Tomcat.addServlet(context, "reviewServlet", new ReviewServlet());
        context.addServletMappingDecoded("/reviewServlet", "reviewServlet");

        Tomcat.addServlet(context, "default", new DefaultServlet());
        context.addServletMappingDecoded("/", "default");

        Tomcat.addServlet(context, "chat", new ChatServlet());
        context.addServletMappingDecoded("/chat", "chat");

        tomcat.start();
        System.out.println("📡 HTTP Tomcat Embedded listening on port 8080!");
        tomcat.getServer().await();
    }
}
