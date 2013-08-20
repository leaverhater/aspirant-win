package ru.bmstu.aspirant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 20.05.13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class Table {
    private int rowNum;
    private int colNum = 3;
    private int id;
    private String data[][];
    private static String mainPath = Main.getMainPath();
    public Table(int id) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(mainPath+"content"+(id + 3)+".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rowNum = Integer.parseInt(prop.getProperty("rowsNum"));
        this.id = id;
        this.data = new String[rowNum][colNum];
    }
    private void setData(int row, int column, String value) {
        data[row][column] = value;
    }
    public String getTable() {
        String sumString = new String();
        Properties prop4 = new Properties();
//        String mainPath = Main.getMainPath();
        String templateFolderPath = mainPath + "Templates/";
        try {
            prop4.load(new FileInputStream(mainPath + "content"+(3+id)+".properties"));
            sumString = FileUtils.readFileToString(new File(templateFolderPath + "tableBegin.txt"));
            for (int i = 0; i < rowNum; i++) {
                    sumString += prop4.getProperty("index" + (i+1));;
                    sumString += FileUtils.readFileToString(new File(templateFolderPath + "after1col.txt"));
                    sumString += prop4.getProperty("discipline" + (i+1));
                    sumString += FileUtils.readFileToString(new File(templateFolderPath + "after2col.txt"));
                    sumString += prop4.getProperty("time" + (i+1));
                    if (i == rowNum - 1) {
                        sumString += FileUtils.readFileToString(new File(templateFolderPath + "tableEnd.txt"));
                    } else {
                        sumString += FileUtils.readFileToString(new File(templateFolderPath + "btwRows.txt"));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return sumString;
    }
    public static String getTableToReplace() {
        String tabletoreplace = new String();
        try {
            tabletoreplace = FileUtils.readFileToString(new File(mainPath+"tableToReplace.txt"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return tabletoreplace;
    }
    public static String replaceTableString(String sumString) {
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        Table table3 = new Table(3);
        String table = table1.getTable();
        try {
            table += FileUtils.readFileToString(new File(mainPath+"Templates/table2"));
            table += table2.getTable();
            table += FileUtils.readFileToString(new File(mainPath+"Templates/table3"));
            table += table3.getTable();
            if (Fields.getFieldValue("time").equals("4 года")) {
                Table table4 = new Table(4);
                table += FileUtils.readFileToString(new File(mainPath+"Templates/table4"));
                table += table4.getTable();
            }


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return sumString.replaceAll(Table.getTableToReplace(), table);
    }


}
