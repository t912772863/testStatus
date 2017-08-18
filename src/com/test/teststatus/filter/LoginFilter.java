package com.test.teststatus.filter;


import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

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


        // 将请求转换成HttpServletRequest 请求
        HttpServletRequest hreq = (HttpServletRequest)servletRequest;
        HttpServletResponse hrsp = (HttpServletResponse) servletResponse;

        String uri = hreq.getRequestURL().toString();
        if(uri.contains("logout") || uri.contains("cancelSession")){
            // 退出登录方法, 这时不再拦截
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        HttpSession session = hreq.getSession(true);
        if(session.getAttribute("user") == null){
            String token = hreq.getParameter("token");
            if( token!= null){
                // 如果有token, 则说明是由认证中心返回的, 再去验证一下这个token是否为真正的
                String result = testGet("http://localhost:8086/singlesign/login/check_token?token="+token);
                JSONObject json = JSONObject.fromObject(result);
                boolean index = json.getBoolean("data");
                if(index){
                    // token有效, 创建用户session
                    session.setAttribute("user", token);
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }
            // 没有登录, 重定向到登录页面  http://localhost:8082/singlesign/   http://localhost:8086/singlesign/
            String url = hreq.getRequestURL().toString()+"?";

            Map<String ,Object> map = hreq.getParameterMap();
            for (Map.Entry<String , Object> entry:map.entrySet()) {
                url += entry.getKey()+"="+((String[])entry.getValue())[0]+"&";
            }
            url = url.substring(0, url.length()-1);

            // 带着本次请求的url, 跳转到认证中心去
            hrsp.sendRedirect("http://localhost:8086/singlesign/login/to_index?url="+url);
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

    /**
     * get请求
     */
    private static String testGet(String url) {
        String result = "";
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);//这里发送get请求
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            org.apache.http.HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
