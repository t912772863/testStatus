package com.test.teststatus;

import com.test.teststatus.db.OperOracle;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class StatusServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(StatusServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        logger.info("process in class StatusServlet, method init");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try{
            logger.info("Thread: "+Thread.currentThread().getName());
            Map map = req.getParameterMap();
            for(Object s : map.keySet()){
                logger.info("param: "+s+" = "+((String[])map.get(s))[0]);
            }
            resp.getWriter().write("<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<root>"+
                        "<RetCode>0</RetCode>"+
                        "<RetMsg>success</RetMsg>"+
                    "</root>");

            OperOracle.getCompanyInfo();

        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }

    }
}