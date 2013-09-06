package ru.bmstu.aspirant.win;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.bmstu.aspirant.Field;
import ru.bmstu.aspirant.Main;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 29.08.13
 * Time: 15:55
 */
public class Card {
    private ArrayList<Field> fields;
    private String title;
    private int id;
    private JScrollPane scroller;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public void setScroller(JScrollPane scroller) {
        this.scroller = scroller;
    }

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Field> getFields() {
        return this.fields;
    }

    public JScrollPane getScroller() {
        return this.scroller;
    }


    public static ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<Card>();
        File cardsXml = new File(Main.getJarPath() + "config/cards.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(cardsXml);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        NodeList tabList = doc.getElementsByTagName("tab");
        String typeString = new String();
        for (int i = 0; i < tabList.getLength(); i++) {
            Node tabNode = tabList.item(i);
            Element tabElement = (Element) tabNode;
            NodeList fieldList = tabElement.getElementsByTagName("field");
            Card tempCard = new Card();
            tempCard.setTitle(tabElement.getAttribute("title"));
            String idString = tabElement.getAttribute("id");
            if (idString != "")
                tempCard.setId(Integer.parseInt(idString));
            ArrayList<Field> tempFields = Field.getFieldsFromNodeList(fieldList);
            tempCard.setFields(tempFields);

            JPanel tempPanel = new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
            for (Field tempField : tempFields) {
                int tabNum = tempField.getTab();
                if (tempField.getType().equals("text")) {
                    tempPanel.add(new JLabel(tempField.getLabel()));
                    JTextField tempJTextField = new JTextField();
                    tempJTextField.setMaximumSize(new Dimension(800, 20));
                    tempField.setComponent(tempJTextField);
                    tempPanel.add(tempField.getComponent());
                } else if (tempField.getType().equals("radio")) {
                    tempPanel.add(new JLabel(tempField.getLabel()));
                    tempField.setComponent(new JRadioButton(tempField.getOption1()));
                    ((JRadioButton)tempField.getComponent()).setSelected(true);
                    ButtonGroup tempGroup = new ButtonGroup();
                    JRadioButton tempRadioButton = new JRadioButton(tempField.getOption2());
                    tempGroup.add((JRadioButton) tempField.getComponent());
                    tempGroup.add(tempRadioButton);
                    tempPanel.add(tempField.getComponent());
                    tempPanel.add(tempRadioButton);
                } else if (tempField.getType().equals("textarea")) {
                    tempPanel.add(new JLabel(tempField.getLabel()));
                    tempField.setComponent(new JTextArea());
                    tempPanel.add(new JScrollPane(tempField.getComponent()));
                }
            }
            JScrollPane tempScroller = new JScrollPane(tempPanel);
            tempCard.setScroller(tempScroller);
            cards.add(tempCard);
        }
        return cards;
    }

}
