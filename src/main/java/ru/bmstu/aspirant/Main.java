package ru.bmstu.aspirant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 26.06.13
 * Time: 4:02
 */
public class Main {
    public static String tableTemplateFile = "config"+ File.separator+"tableTemplate.txt";
    public static String rowTemplateFile = "config"+File.separator+"rowTemplate.txt";
    public static String boldRowTemplateFile = "config"+File.separator+"boldRowTemplate.txt";

    public static String getJarPath() {
        String jarPath = new String();
        jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println(jarPath);
        try {
            jarPath = URLDecoder.decode(jarPath.substring(0, jarPath.length()-13), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return jarPath;

    }

}
