package com.test.teststatus.servlet;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.test.teststatus.db.OperOracle.getCompanyInfo;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class InitServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(InitServlet.class);
    @Override
    public void init() throws ServletException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        logger.info("process in class StatusServlet, method init");

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            logger.info("Thread: "+Thread.currentThread().getName());
            getCompanyInfo();

            Map map = req.getParameterMap();
            for(Object s : map.keySet()){
                logger.info("param: "+s+" = "+((String[])map.get(s))[0]);
            }
            resp.getWriter().write("<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<root>"+
                    "<RetCode>0</RetCode>"+
                    "<RetMsg>success</RetMsg>"+
                    "</root>");

        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }

    }
}
