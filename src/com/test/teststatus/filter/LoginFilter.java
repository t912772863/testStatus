package com.test.teststatus.filter;


import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *   filter能够在一个请求到达servlet之前预处理用户请求，也可以在离开servlet时处理http响应：
 在执行servlet之前，首先执行filter程序，并为之做一些预处理工作；
 根据程序需要修改请求和响应；
 在servlet被调用之后截获servlet的执行
 *
 *
 * Created by Administrator on 2017/8/16 0016.
 */
public class LoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LoginFilter is init");
        this.filterConfig = filterConfig;
    }

    /**
     * 该方法完成实际的过滤操作，当客户端请求方法与过滤器设置匹配的URL时，Servlet容器将先调用过滤器的doFilter方法。FilterChain用户访问后续过滤器。
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 获取ServletContext 对象，用于记录日志
        ServletContext context = this.filterConfig.getServletContext();
        // 将请求转换成HttpServletRequest 请求
        HttpServletRequest hreq = (HttpServletRequest)servletRequest;
        HttpServletResponse hrsp = (HttpServletResponse) servletResponse;

        HttpSession session = hreq.getSession(false);
        if(session == null || session.getAttribute("user") == null){
            // 没有登录, 重定向到登录页面  http://localhost:8082/singlesign/
            String uri = hreq.getRequestURI();

            hrsp.sendRedirect("http://www.baidu.com");
            return;
        }else {
            // 登录了, 继续后面的请求
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    /**
     * Servlet容器在销毁过滤器实例前调用该方法，在该方法中释放Servlet过滤器占用的资源。
     */
    @Override
    public void destroy() {

    }
}
