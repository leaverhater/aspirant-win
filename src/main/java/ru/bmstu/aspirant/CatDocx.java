package ru.bmstu.aspirant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 18.03.13
 * Time: 1:18
 */
public class CatDocx {
    private static String mainPath = Main.getMainPath();
    private static String templateFolderPath = mainPath + "Templates/";
    private static String propPath = mainPath;
    private static String docPath = mainPath + "base/";
    private static String docXmlFile = mainPath + "document.xml";
    private static String fieldsFile = mainPath + "fields.txt";
    private static String finalString = new String();
    private static String tmpstr = new String();
    private static String time = new String();

    /**
     * Возвращает строку - содержимое файла document.xml в случае оконного приложения       *
     * @return Строка - содержимое файла document.xml в случае оконного приложения
     */
    public static String sumStringWin() {
        String sumString = new String();
        return sumString;
    }

    /**
     * Генерирует docx файл
     */
    public static void concatDocx() {
        String sumString = sumStringWin();
        try {
            finalString = new String(sumString.getBytes("utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
// File to write
        File file = new File(docPath+"word/document.xml");
        try {
            // Write the file
            FileUtils.writeStringToFile(file, finalString);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
