package ru.bmstu.aspirant;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 26.06.13
 * Time: 4:02
 * To change this template use File | Settings | File Templates.
 */
public class Main {
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

}
