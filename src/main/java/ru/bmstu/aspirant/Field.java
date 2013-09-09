package ru.bmstu.aspirant;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.bmstu.aspirant.win.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 26.06.13
 * Time: 1:20
 */
public class Field {
    private String type;
    private String label;
    private String option1;
    private String option2;
    private String id;
    private int tab;
    private String value;
    private Component component;


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public int getTab() {
        return this.tab;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        if (this.type.equals("radio"))
            return ((JRadioButton) component).isSelected() ? this.option1 : this.option2;
        else if (this.type.equals("text"))
            return ((JTextField)component).getText();
        else if (this.type.equals("textarea"))
            return StringProc.toDocxMultiline(((JTextArea)component).getText());
        else
            return "";
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return this.component;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }


    /**
     * Возвращает массив полей вкладки из NodeList
     * @param nList список полей
     * @return
     */

    public static ArrayList<Field> getFieldsFromNodeList (NodeList nList) {
        String typeString = new String();
        ArrayList<Field> fields = new ArrayList<Field>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;
            Field field = new Field();
            field.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
            typeString = eElement.getElementsByTagName("type").item(0).getTextContent();
            field.setType(typeString);
            if (typeString.equals("radio")) {
                field.setOption1(eElement.getElementsByTagName("option1").item(0).getTextContent());
                field.setOption2(eElement.getElementsByTagName("option2").item(0).getTextContent());
//                if (field.id.equals("time")) {
//                    ((JRadioButton)field.component).addActionListener(new WinApp.TimeRadioSwitch());
//                }
            }
            field.setLabel(eElement.getElementsByTagName("label").item(0).getTextContent());
            fields.add(field);
        }
        return fields;

    }

    public static String getValueFromFieldId (ArrayList<Card> cards, String id) {
        String value = new String();
        for (Card tempCard : cards) {
            ArrayList<Field> tempFields = tempCard.getFields();
            for (Field tempField : tempFields) {
                if (tempField.getId().equals(id)) {
                    value = tempField.getValue();
                }
            }
        }
        return value;
    }
}

