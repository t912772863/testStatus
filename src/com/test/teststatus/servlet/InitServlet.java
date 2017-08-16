package com.test.teststatus.servlet;


import com.test.teststatus.home.UserHome;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class InitServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(InitServlet.class);
    @Override
    public void init() throws ServletException {
        logger.info("process in class StatusServlet, method init");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req,resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            // 转发到页面jsp
            request.getRequestDispatcher("/index.jsp").forward(request,response);

//            // 根据不同的请求命令, 调用不同的方法
//            String commandName = request.getParameter("commandName");
//            OutputStream out = response.getOutputStream();
//
//            if("login".equals(commandName)){
//                // 登录方法
//
//                boolean index = UserHome.login(request);
//                if(index){
//                    out.write("登录成功".getBytes());
//                }else {
//                    out.write("登录失败".getBytes());
//                }
//            }else {
//                // 找不到匹配的方法,返回未知命令
//                out.write("未知的方法命令".getBytes());
//            }
        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }

    }
}
