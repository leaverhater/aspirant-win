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
    private static String docXmlFile;
    private static String outXmlFile = mainPath + "base/word/document.xml";
    private static String timeStr = new String();
    private static String name;

    /**
     * Возвращает строку - содержимое файла document.xml в случае оконного приложения       *
     *
     * @return Строка - содержимое файла document.xml в случае оконного приложения
     */
    public CatDocx () {

    }
    public static String sumString(ArrayList<Card> cards, ArrayList<Table> tables) {
        String sumString = new String();
        timeStr = Field.getValueFromFieldId(cards, "time");
//        time = Integer.parseInt(timeStr.substring(0, 1));
        docXmlFile = mainPath + "config/document" + timeStr.substring(0, 1) + ".xml";
        System.out.println( mainPath + "config/document" + timeStr.substring(0, 1) + ".xml");
        name = Field.getValueFromFieldId(cards, "name");
        String manager = Field.getValueFromFieldId(cards, "manager");
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
        sumString = sumString.replaceAll("nameShortfield", StringProc.longNameToShort(name));
        System.out.println("Replaced nameShortField with "+ StringProc.longNameToShort(name));
        sumString = sumString.replaceAll("managerShortfield", StringProc.longNameToShort(manager));
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
        File file = new File(outXmlFile);
        try {
            FileUtils.writeStringToFile(file, finalString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZipIt.createDocx(StringProc.longNameToShort(name));
    }
}
