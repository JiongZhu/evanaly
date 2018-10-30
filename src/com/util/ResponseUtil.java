package com.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:响应工具类
 * @Author:wx6_2
 * @Date:2017/5/20
 **/
public class ResponseUtil {
    public static void write(HttpServletResponse response, String content) {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(content);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
