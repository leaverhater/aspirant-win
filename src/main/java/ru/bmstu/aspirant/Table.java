package ru.bmstu.aspirant;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.bmstu.aspirant.win.ButtonColumn;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 20.05.13
 * Time: 15:04
 */
public class Table {
    private int rowNum;
    private int colNum = 3;
    private String id;
    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> labels = new ArrayList<String>();
    private ArrayList<JTable> tables = new ArrayList<JTable>();
    private ArrayList<JButton> addRowButtons = new ArrayList<JButton>();
    //    private ArrayList<JPanel> cards = new ArrayList<JPanel>();
    private JScrollPane scroller = new JScrollPane();
    private String data[][];
    private String header;
    private static String mainPath = Main.getMainPath();

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
        JPanel tempJPanel = new JPanel();
        tempJPanel.setLayout(new BoxLayout(tempJPanel, BoxLayout.Y_AXIS));
        for (String tempLabel : labels) {
            JTable tempJTable = new JTable(new MyTableModel(titles));
            TableColumnModel tempColumnModel = tempJTable.getColumnModel();
            tempColumnModel.getColumn(0).setMaxWidth(70);
            tempColumnModel.getColumn(2).setMaxWidth(70);
            tempColumnModel.getColumn(3).setMaxWidth(100);
            tempColumnModel.getColumn(4).setMaxWidth(150);
            tempColumnModel.getColumn(4).setMinWidth(150);
            tempColumnModel.getColumn(5).setMaxWidth(150);
            tempColumnModel.getColumn(5).setMinWidth(150);


            tempJPanel.add(new JLabel(tempLabel));

            ButtonColumn delButtonColumn = new ButtonColumn(tempJTable, delete, 4);
            ButtonColumn addButtonColumn = new ButtonColumn(tempJTable, add, 5);
            delButtonColumn.setMnemonic(KeyEvent.VK_D);
            addButtonColumn.setMnemonic(KeyEvent.VK_D);
            tempJPanel.add(tempJTable.getTableHeader());
            tempJPanel.add(tempJTable);
            JButton tempJButton = new JButton("Добавить");
            tempJButton.addActionListener(new AddRowButtonListener(tempJTable));
            tempJPanel.add(tempJButton);

            tables.add(tempJTable);
        }
        this.scroller = new JScrollPane(tempJPanel);
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public JScrollPane getScroller() {
        return this.scroller;
    }

    public String getHeader() {
        return this.header;
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<JTable> getJTables() {
        return this.tables;
    }


    public static ArrayList<Table> getTables() {
        ArrayList<Table> tables = new ArrayList<Table>();
        File tablesXml = new File(Main.getJarPath() + "config/tables.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(tablesXml);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        NodeList tableList = doc.getElementsByTagName("table");
        for (int i = 0; i < tableList.getLength(); i++) {
            Node tableNode = tableList.item(i);
            Element tableElement = (Element) tableNode;
            Table tempTable = new Table();
            tempTable.setLabels(XmlProc.getStringsFromNode(tableNode, "label"));
            tempTable.setTitles(XmlProc.getStringsFromNode(tableNode, "title"));
            tempTable.setHeader(tableElement.getElementsByTagName("header").item(0).getTextContent());
//            tempTable.id = tableElement.getElementsByTagName("id").item(0).getTextContent();
            tempTable.id = tableElement.getAttribute("id");
            tables.add(tempTable);
        }


        return tables;
    }

    public ArrayList<String> getDocxTables(Table table) {
        ArrayList<String> docxTables = new ArrayList<String>();
        ArrayList<JTable> tempJTables = table.getJTables();
        for (JTable tempJTable : tempJTables) {
            String allRows = "";
            String docxTable = new String();
            int rowCnt = tempJTable.getRowCount();
            System.out.println(rowCnt);
            for (int i = 0; i < rowCnt; i++) {
                String valueone = (String) tempJTable.getModel().getValueAt(i, 0);
                String valuetwo = (String) tempJTable.getModel().getValueAt(i, 1);
                String valuethree = (String) tempJTable.getModel().getValueAt(i, 2);
                if ((Boolean)tempJTable.getModel().getValueAt(i,3))
                    allRows = allRows + getDocxBoldRow(valueone, valuetwo, valuethree); else
                    allRows = allRows + getDocxRow(valueone, valuetwo, valuethree);
            }
            docxTable = getTableTemplate().replaceAll("pasteTrHere", allRows);
            docxTables.add(docxTable);
        }
        return docxTables;
    }

    private static String getTableTemplate() {
        String tableTemplate = new String();
        try {
            tableTemplate = FileUtils.readFileToString(new File(Main.getJarPath() + Main.tableTemplateFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tableTemplate;
    }

    private static String getRowTemplate() {
        String rowTemplate = new String();
        try {
            rowTemplate = FileUtils.readFileToString(new File(Main.getJarPath() + Main.rowTemplateFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowTemplate;
    }

    private static String getBoldRowTemplate() {
        String boldRowTemplate = new String();
        try {
            boldRowTemplate = FileUtils.readFileToString(new File(Main.getJarPath() + Main.boldRowTemplateFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boldRowTemplate;
    }

    private static String getDocxRow(String valueone, String valuetwo, String valuethree) {
        String docxRow = getRowTemplate();
        if (valueone == null)
            valueone = "";
        if (valuetwo == null)
            valuetwo = "";
        if (valuethree == null)
            valuethree = "";

        return docxRow.replaceAll("valueone", valueone).replaceAll("valuetwo", valuetwo).replaceAll("valuethree", valuethree);
    }

    private static String getDocxBoldRow(String valueone, String valuetwo, String valuethree) {
        String docxBoldRow = getBoldRowTemplate();
        if (valueone == null)
            valueone = "";
        if (valuetwo == null)
            valuetwo = "";
        if (valuethree == null)
            valuethree = "";
        return docxBoldRow.replaceAll("valueone", valueone).replaceAll("valuetwo", valuetwo).replaceAll("valuethree", valuethree);
    }

    class MyTableModel extends AbstractTableModel {
//        private String[] columnNames;
//        private Object[][] data;
        private ArrayList<String> columnNames;
        private ArrayList<ArrayList<Object>> data;
        private ArrayList<Object> emptyLine;


        public MyTableModel(ArrayList<String> titles) {
//            this.columnNames = new String[titles.size()];
//            this.data = new Object[1][titles.size()];
//            this.columnNames = titles.toArray(this.columnNames);
            this.columnNames = new ArrayList<String>(titles);
            this.columnNames.add("Выделить");
            this.columnNames.add("");
            this.columnNames.add("");
            data = new ArrayList<ArrayList<Object>>();
            emptyLine = new ArrayList<Object>();
            for (int i = 0; i < getColumnCount() - 3; i++)
                emptyLine.add("");
            emptyLine.add(false);
            emptyLine.add("Удалить");
            emptyLine.add("Вставить");
            this.data.add(new ArrayList<Object>(emptyLine));
        }

        public int getColumnCount() {
            return columnNames.size();
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames.get(col);
        }

        public Object getValueAt(int row, int col) {
            return data.get(row).get(col);
        }

        public Class getColumnClass(int c) {
//            if (c < getColumnCount() - 2)
//                return String.class;
//            else return Boolean.class;
            if (c == 3)
                return Boolean.class; else
                return String.class;
        }

        public void addRow(int rowNum) {
            ArrayList<Object> newLine = new ArrayList<Object>(emptyLine);
//            for (int i = 0; i < getColumnCount() - 1; i++)
//                newLine.add("");
//            newLine.add(false);
            data.add(rowNum, newLine);
            this.fireTableDataChanged();

        }

        public void delRow(int rowNum) {
            data.remove(rowNum);
            this.fireTableDataChanged();
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            // change made here
            data.get(row).set(col,value);
            fireTableCellUpdated(row, col);
        }
    }

    Action delete = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            ((MyTableModel)table.getModel()).delRow(modelRow);
        }
    };

    Action add = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            ((MyTableModel)table.getModel()).addRow(modelRow);
        }
    };

    public static class AddRowButtonListener implements ActionListener {
        private JTable table;

        public AddRowButtonListener(JTable table) {
            this.table = table;
        }

        public void actionPerformed(ActionEvent e) {
            ((MyTableModel)table.getModel()).addRow(table.getRowCount());
        }
    }




}
