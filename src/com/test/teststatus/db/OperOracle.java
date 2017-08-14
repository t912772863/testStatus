package com.test.teststatus.db;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * 数据持久与查询等操作
 * Created by Administrator on 2017/8/7 0007.
 */
public class OperOracle extends DBManager {
    /**
     * 日志工具
     */
    private static final Logger logger = Logger.getLogger(OperOracle.class);

    /**
     * 获取厂商信息
     *
     * @return 厂商信息.其中K:厂商的appkey. V:厂商信息
     */
    public static HashMap<String, Object> getCompanyInfo() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashMap<String, Object> tmp = null;
        try {
            connection = DBManager.getConnection("localmysql");

            pstmt = connection.prepareStatement("Select * from user");
            rs = pstmt.executeQuery();
            tmp = new HashMap<String, Object>();
            while (rs.next()) {
                System.out.println(rs.getLong("ID"));
                System.out.println(rs.getString("USER_NAME"));
                System.out.println(rs.getString("PASSWORD"));
                System.out.println(rs.getString("MOBILE"));
                System.out.println(rs.getDate("CREATE_TIME"));
                System.out.println(rs.getDate("UPDATE_TIME"));
                System.out.println(rs.getInt("STATUS"));

            }
        } catch (Exception e) {
            logger.error("error", e);
        } finally {
            DBManager.close(rs, pstmt, connection);
        }
        return tmp;
    }



}
