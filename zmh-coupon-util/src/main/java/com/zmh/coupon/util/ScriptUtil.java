package com.zmh.coupon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName: ScriptUtil
 * @Description: 脚本工具类
 * @author huawuque@taomz.com
 * @date 2020-07-11 10:44
 * 
 */
public class ScriptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptUtil.class);

    /**
     * return lua script String
     *
     * @param path
     * @return
     */
    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();
        InputStream stream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        try {
            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str).append(System.lineSeparator());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sb.toString();
    }
}
