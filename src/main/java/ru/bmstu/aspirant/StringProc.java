package ru.bmstu.aspirant;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 17.08.13
 * Time: 22:45
 */
public class StringProc {
    public static String toDocxMultiline(String src) {
        return src.replaceAll("\n","</w:t><w:br /><w:t>");
    }

    public static String toJLabelMultiline(String src) {
        return "<html>"+src.replace("\n","<br />")+"/html";
    }

    public static String longNameToShort(String longName) {
        String shortName = new String();
        String[] arr = longName.split(" ");
        if (arr.length >= 3) {
            shortName = arr[1].charAt(0) + "." + arr[2].charAt(0) + "." + arr[0];
        }
        return shortName;
    }
}
