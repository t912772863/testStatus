package com.test.teststatus.home;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
public class UserHome {
    public static boolean login(HttpServletRequest request){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        return false;
    }
}
