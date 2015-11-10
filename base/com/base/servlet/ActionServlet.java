package com.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ActionServlet extends HttpServlet {
    public void init() throws ServletException {
        System.out.println("action servlet init......................");
    }
    public void destroy() {
        System.out.println("action servlet destroy......................");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("action get");
        // process(RequestContext.get(), false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("action post");
        // process(RequestContext.get(), true);
    }
    
    
}
