package ru.bmstu.aspirant.win;

import ru.bmstu.aspirant.CatDocx;
import ru.bmstu.aspirant.Table;
import ru.bmstu.aspirant.ZipIt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 17.08.13
 * Time: 22:05
 */
public class WinApp {
    private static ArrayList<Card> cards = new ArrayList<Card>();
    private static ArrayList<Table> tables = new ArrayList<Table>();
//    private static ArrayList<JScrollPane> tableScrollers = new ArrayList<JScrollPane>();

    public WinApp () {
//        cards = Card.getCards();
//        tables = Table.getTables();
    }

    public static void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
//        for (Card tempCard:cards) {
//            tabbedPane.addTab(tempCard.getTitle(), tempCard.getScroller());
//        }
        for (int i = 0; i < cards.size() - 1; i++) {
            tabbedPane.addTab(cards.get(i).getTitle(), cards.get(i).getScroller());
        }
        for (Table tempTable:tables) {
            tabbedPane.addTab(tempTable.getHeader(), tempTable.getScroller());
        }
        Card finalCard = cards.get(cards.size()-1);
        JScrollPane finalScroller = finalCard.getScroller();
        tabbedPane.setMaximumSize(new Dimension(512, 700));
        JPanel finalTab = new JPanel();
        finalTab.setLayout(new BoxLayout(finalTab, BoxLayout.Y_AXIS));
        JButton genButton = new JButton("Генерировать");
        genButton.addActionListener(new GenButtonListener());
        finalCard.getPanel().add(genButton);
        tabbedPane.addTab("Готово",finalScroller);
        pane.add(tabbedPane, BorderLayout.CENTER);

    }

    public static void createGUI() {
        JFrame frame = new JFrame("План аспиранта");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentToPane(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public static class GenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CatDocx.concatDocx(cards, tables);

        }
    }

    public static class TimeRadioSwitch implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }


    public static void main(String[] args) {
        cards = Card.getCards();
        tables = Table.getTables();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
