package com.pingao.utils;

/**
 * Created by pingao on 2018/10/23.
 */
public class ResourceUtils {
    public static String getTestResourcePath(String name) {
        return System.getProperty("user.dir") + "/src/test/resources/" + name;
    }
}
