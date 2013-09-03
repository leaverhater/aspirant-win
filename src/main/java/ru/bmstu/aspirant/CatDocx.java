package ru.bmstu.aspirant;

import org.apache.commons.io.FileUtils;
import ru.bmstu.aspirant.win.Card;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 18.03.13
 * Time: 1:18
 */
public class CatDocx {
    private static String mainPath = Main.getJarPath();
    private static String docPath = mainPath + "config/base/";
    private static String docXmlFile = mainPath + "config/document.xml";
    private static String timeStr = new String();
    private static int time;

    /**
     * Возвращает строку - содержимое файла document.xml в случае оконного приложения       *
     *
     * @return Строка - содержимое файла document.xml в случае оконного приложения
     */
    public static String sumString(ArrayList<Card> cards, ArrayList<Table> tables) {
        String sumString = new String();
        timeStr = Field.getValueFromFieldId(cards, 0, "time");
        time = Integer.parseInt(timeStr.substring(0, 1));
        System.out.println("Time is " + time);
        try {
            sumString = FileUtils.readFileToString(new File(docXmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Card tempCard : cards) {
            ArrayList<Field> fields = tempCard.getFields();
            for (Field tempField : fields) {
                sumString = sumString.replaceAll(tempField.getId() + "field", tempField.getValue());
                System.out.println("Replaced " + tempField.getId() + "field with" + tempField.getValue());
            }
        }
        for (Table tempTable : tables) {
            ArrayList<String> tempDocxTables = tempTable.getDocxTables(tempTable);
            int tableCount = 0;
            for (String tempDocxTable : tempDocxTables) {
                sumString = sumString.replaceAll(tempTable.getId() + tableCount, tempDocxTable);
                System.out.println("Replaced " + tempTable.getId() + tableCount);
                tableCount++;
            }
        }
        return sumString;
    }

    /**
     * Генерирует docx файл
     */
    public static void concatDocx(ArrayList<Card> cards, ArrayList<Table> tables) {
        String sumString = sumString(cards, tables);
        String finalString = new String();
        try {
            finalString = new String(sumString.getBytes("utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(docPath + "word/document.xml");
        try {
            FileUtils.writeStringToFile(file, finalString);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
