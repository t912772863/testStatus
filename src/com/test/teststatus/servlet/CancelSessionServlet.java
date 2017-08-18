package com.test.teststatus.servlet;


import com.test.teststatus.filter.SessionFilter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class CancelSessionServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(CancelSessionServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sessionId = request.getParameter("sessionId");
            SessionFilter.removeSession(sessionId);
        } catch (Exception e) {
            logger.info("error", e);
        }

    }
}
