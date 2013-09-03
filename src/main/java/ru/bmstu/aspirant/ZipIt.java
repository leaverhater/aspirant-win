package ru.bmstu.aspirant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 18.03.13
 * Time: 2:35
 * To change this template use File | Settings | File Templates.
 */
public class ZipIt
{
    private List<String> fileList;
    private static String mainPath = Main.getJarPath();
    private static String OUTPUT_ZIP_FILE = mainPath + "plan.docx";
    private static String SOURCE_FOLDER = mainPath + "config/base";
    private Properties prop = new Properties();

    ZipIt(){
        fileList = new ArrayList<String>();
    }

    public static void createDocx(String name)
    {
        ZipIt zipIt = new ZipIt();
        zipIt.generateFileList(new File(SOURCE_FOLDER));
        zipIt.zipFolder(mainPath+"config/"+name+".docx");
        System.out.println(mainPath+"config/"+name);


    }

    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipFolder(String zipFile){

        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for(String file : this.fileList){

                System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in =
                        new FileInputStream(SOURCE_FOLDER + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            System.out.println("Done");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){

        //add file only
        if(node.isFile()){
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
        return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    public List<String> getFileList()
    {
        return fileList;
    }
    public static String getDocxName()
    {
        return OUTPUT_ZIP_FILE;
    }
}
