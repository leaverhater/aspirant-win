package ru.bmstu.aspirant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 26.06.13
 * Time: 1:20
 */
public class Fields {
    private static String mainPath = Main.getMainPath();

    public static void writeProps(Properties prop, int propNum) {
        try {
            prop.store(new FileOutputStream(mainPath + "content" + propNum + ".properties"), null);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String[] fieldsFromFile(String filename) {
        List<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static String getFieldValue(String field) {
        Properties prop = new Properties();
        String value = null;
        int propCnt = 1;
        while (value == null) {
            try {
                prop.load(new FileInputStream(mainPath + "content" + propCnt + ".properties"));
                value = prop.getProperty(field);
                propCnt++;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return value;
    }

    private String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Май", "Май", "Май", "Май"};
}
