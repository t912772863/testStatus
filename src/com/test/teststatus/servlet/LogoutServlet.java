package com.test.teststatus.servlet;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class LogoutServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(LogoutServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req,resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {

            String sessionId = request.getSession().getId();
            response.sendRedirect("http://localhost:8086/singlesign/login/logout?sessionId="+sessionId);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
