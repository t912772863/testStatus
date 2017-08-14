package com.test.teststatus;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public class DemoMain {
    private static Logger logger = Logger.getLogger("DemoMain");

    public static void main(String[] args) {
      while (true){
          try {
              Thread.sleep(5000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          System.out.println("当前时间: "+System.currentTimeMillis());
          logger.info("当前时间: "+System.currentTimeMillis());
      }
    }
}
