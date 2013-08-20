package ru.bmstu.aspirant.win;

import ru.bmstu.aspirant.CatDocx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: vadya
 * Date: 17.08.13
 * Time: 22:05
 */
public class WinApp {

    final static String COMMONINFO = "Общая информация";
    final static String NAUCHISSL = "Науч.-иссл. часть";
    final static int extraWindowWidth = 100;

    public static void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        //Create the "cards".

        //Card1 - Общая информация

        JPanel card1 = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));

        JTextField name = new JTextField();
        JTextField dep = new JTextField();
        JTextField fullDep = new JTextField();
        JTextField spec = new JTextField();
        JTextField manager = new JTextField();
        JTextField consult = new JTextField();
        JTextField start = new JTextField();
        JTextField end = new JTextField();

        card1.add(new JLabel("Фамилия, имя, отчество аспиранта полностью"));
        card1.add(name);
        card1.add(new JLabel("Форма обучения"));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rbOch = new JRadioButton("Очная", true);
        JRadioButton rbZaoch = new JRadioButton("Заочная");
        bg.add(rbOch);
        bg.add(rbZaoch);
        card1.add(rbOch);
        card1.add(rbZaoch);
        card1.add(new JLabel("Кафедра (индекс)"));
        card1.add(dep);
        card1.add(new JLabel("Полное наименование кафедры"));
        card1.add(fullDep);
        card1.add(new JLabel("<html>Научная специальность <br />(указывается шифр и наименование специальности)</html>"));
        card1.add(spec);
        card1.add(new JLabel("<html>Научный руководитель<br />(фамилия, имя, отчество, ученая степень, ученое звание)</html>"));
        card1.add(manager);
        card1.add(new JLabel("<html>Научный консультант<br />(фамилия, имя, отчество, ученая степень, ученое звание)</html>"));
        card1.add(consult);
        card1.add(new JLabel("Дата зачисления в аспирантуру (ДД месяц ГГГГ)"));
        card1.add(start);
        card1.add(new JLabel("Дата окончания аспирантуры (ДД месяц ГГГГ)"));
        card1.add(end);


        //card2 - Научно-исследовательская часть


        JPanel card2 = new JPanel() {
            public Dimension getMaximumSize() {
                Dimension size = super.getMaximumSize();
                size.height = 700;
                return size;
            }
        };
        card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));



        JTextArea theme = new JTextArea(10, 30);
        JScrollPane themeScroll = new JScrollPane(theme);
        JTextArea topicality = new JTextArea(10, 30);
        JScrollPane topicalityScroll = new JScrollPane(topicality);
        JTextArea goal = new JTextArea(10, 30);
        JScrollPane goalScroll = new JScrollPane(goal);
        JTextArea newness = new JTextArea(10, 30);
        JScrollPane newnessScroll = new JScrollPane(newness);
        JTextArea value = new JTextArea(10, 30);
        JScrollPane valueScroll = new JScrollPane(value);
        JTextArea realization = new JTextArea(10, 30);
        JScrollPane realizationScroll = new JScrollPane(realization);
        JTextArea publication = new JTextArea(10, 30);
        JScrollPane publicationScroll = new JScrollPane(publication);


        card2.add(new JLabel("Тема научной работы"));
        card2.add(themeScroll);
        card2.add(new JLabel("Актуальность темы"));
        card2.add(topicalityScroll);
        card2.add(new JLabel("Цель работы"));
        card2.add(goalScroll);
        card2.add(new JLabel("Научная новизна работы"));
        card2.add(newnessScroll);
        card2.add(new JLabel("Практическая ценность"));
        card2.add(valueScroll);
        card2.add(new JLabel("Реализация результатов"));
        card2.add(realizationScroll);
        card2.add(new JLabel("Научные публикации"));
        card2.add(publicationScroll);


        //Вкладка с кнопкой генерации
        JPanel cardFinal = new JPanel();
        cardFinal.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));

        // Засовываем JPanel'ы в JScrollPane'ы, чтобы был скролл
        JScrollPane scroller1 = new JScrollPane(card1);
        JScrollPane scroller2 = new JScrollPane(card2);


        // Добавляем вкладки в JTabbedPane
        tabbedPane.addTab(COMMONINFO, scroller1);
        tabbedPane.addTab(NAUCHISSL, scroller2);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }
    public static void createGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("План аспиранта");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.

        addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    public class GenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CatDocx.concatDocx();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}
