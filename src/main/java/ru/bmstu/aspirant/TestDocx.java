package ru.bmstu.aspirant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 22.05.13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class TestDocx {
    public static void main(String[] args) {
        //concatDocx();
        //ZipIt.createDocx();

    }
    private static void concatDocx() {
        // Files to read
        String templateFolderPath = "/mnt/data/docx/temp/";
        String sumString = null;
        try {
            sumString = FileUtils.readFileToString(new File(templateFolderPath + "beforeTable.txt"));
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "tableBegin.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "after1col.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "after2col.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "btwRows.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "after1col.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "after2col.txt"));
            sumString += "1";
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "tableEnd.txt"));
            sumString += FileUtils.readFileToString(new File(templateFolderPath + "afterTable.txt"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
// File to write
        File file = new File("/home/vadya/example/word/document.xml");

// Read the file like string

        try {
            // Write the file
            FileUtils. writeStringToFile(file, sumString);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
