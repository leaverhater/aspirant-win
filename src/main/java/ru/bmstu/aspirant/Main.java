package ru.bmstu.aspirant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 26.06.13
 * Time: 4:02
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static String tableTemplateFile = "config/tableTemplate.txt";
    public static String rowTemplateFile = "config/rowTemplate.txt";
    public static String boldRowTemplateFile = "config/boldRowTemplate.txt";
    public static String getMainPath() {
        String mainPath = new String();
        Properties prop = new Properties();
        try {
            prop.load(Main.class.getClassLoader().getResourceAsStream("main.properties"));
            mainPath = prop.getProperty("mainPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPath;
    }

    public static String getJarPath() {
        String jarPath = new String();
        jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            jarPath = URLDecoder.decode(jarPath.substring(0, jarPath.length()-8), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return jarPath;

    }

}
