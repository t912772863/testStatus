package com.test.teststatus.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/18 0018.
 */
public class SessionFilter implements Filter {
    private static final Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    public static HttpSession getSession(String sessionId){
        return sessionMap.get(sessionId);
    }

    public static void removeSession(String sessionId){
        HttpSession session = sessionMap.get(sessionId);
        session.invalidate();
        sessionMap.remove(sessionId);
    }
}
