package bsu.rfe.java.group7.lab4_5.ravgeysh.varС1;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serial;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private JFileChooser fileChooser = null;
    
    private JMenuItem resetGraphicsMenuItem;
    
    private JCheckBoxMenuItem showAxisMenuItem;
    private JCheckBoxMenuItem showMarkersMenuItem;
    private JCheckBoxMenuItem showFillingMenuItem;
    private JCheckBoxMenuItem rotatedMenuItem;
    private JMenuItem saveToGraphicsItem;
    
    private final GraphicsDisplay display = new GraphicsDisplay();
    
    private boolean fileLoaded = false;

    public MainFrame() {
    	super("Обработка событий от мыши");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        
        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
	    public void actionPerformed(ActionEvent event) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showOpenDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION) {
                    openGraphics(fileChooser.getSelectedFile());
                }
            }
        };

        Action saveGraphicsAction = new AbstractAction("Сохранить график в файл") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION) {
                    saveToGraphicsFile(fileChooser.getSelectedFile());
                }
            }
        };

        Action resetGraphicsAction = new AbstractAction("Отменить все изменения") {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.display.reset();
            }
        };
        this.resetGraphicsMenuItem = fileMenu.add(resetGraphicsAction);
        this.resetGraphicsMenuItem.setEnabled(true);
        
        saveToGraphicsItem = new JMenuItem(saveGraphicsAction);
        fileMenu.add(saveToGraphicsItem);
        saveToGraphicsItem.setEnabled(false);

        fileMenu.add(openGraphicsAction);
        JMenu graphicsMenu = new JMenu("График");
        menuBar.add(graphicsMenu);
        // Создать действие для реакции на активацию элемента "Показывать
//        оси координат "

        Action showFillingAction = new AbstractAction("Заполнить площади графика") {
            public void actionPerformed(ActionEvent event) {
// свойство showAxis класса GraphicsDisplay истина,
//            если элемент меню
// showAxisMenuItem отмечен флажком, и ложь - в
//            противном случае
                display.setShowFilling(showFillingMenuItem.isSelected());
            }
        };

        showFillingMenuItem = new JCheckBoxMenuItem(showFillingAction);
        graphicsMenu.add(showFillingMenuItem);
        showFillingMenuItem.setSelected(false);

        Action rotateAction = new AbstractAction("Повернуть график") {
            public void actionPerformed(ActionEvent event) {
// свойство showAxis класса GraphicsDisplay истина,
//            если элемент меню
// showAxisMenuItem отмечен флажком, и ложь - в
//            противном случае
                display.setRotated(rotatedMenuItem.isSelected());
            }
        };
        rotatedMenuItem = new JCheckBoxMenuItem(rotateAction);
        graphicsMenu.add(rotatedMenuItem);
        rotatedMenuItem.setSelected(false);

        Action showAxisAction = new AbstractAction("Показывать оси координат") {
            public void actionPerformed(ActionEvent event) {
// свойство showAxis класса GraphicsDisplay истина,
//            если элемент меню
// showAxisMenuItem отмечен флажком, и ложь - в
//            противном случае
                display.setShowAxis(showAxisMenuItem.isSelected());
            }
        };
        showAxisMenuItem = new
                JCheckBoxMenuItem(showAxisAction);
// Добавить соответствующий элемент в меню
        graphicsMenu.add(showAxisMenuItem);
// Элемент по умолчанию включен (отмечен флажком)
        showAxisMenuItem.setSelected(true);
        // Повторить действия для элемента "Показывать маркеры точек"
        Action showMarkersAction = new AbstractAction("Показывать маркеры точек") {

            public void actionPerformed(ActionEvent event) {
// по аналогии с showAxisMenuItem
                display.setShowMarkers(showMarkersMenuItem.isSelected());
            }
        };
        showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(showMarkersMenuItem);
// Элемент по умолчанию включен (отмечен флажком)
        showMarkersMenuItem.setSelected(true);
// Зарегистрировать обработчик событий, связанных с меню "График"
        graphicsMenu.addMenuListener(new MenusListener());
        fileMenu.addMenuListener(new MenusListener());
// Установить GraphicsDisplay в цент граничной компоновки
        getContentPane().add(display, BorderLayout.CENTER);
    }

    // Считывание данных графика из существующего файла
    protected void openGraphics(File selectedFile) {
        try {
// Шаг 1 - Открыть поток чтения данных, связанный с входным
//        файловым потоком
            DataInputStream in = new DataInputStream(new
                    FileInputStream(selectedFile));
/* Шаг 2 - Зная объѐм данных в потоке ввода можно вычислить,
* сколько памяти нужно зарезервировать в массиве:
* Всего байт в потоке - in.available() байт;
* Размер одного числа Double - Double.SIZE бит, или
Double.SIZE/8 байт;
* Так как числа записываются парами, то число пар меньше в
2 раза
*/
            double[][] graphicsData = new
                    double[in.available() / (Double.SIZE / 8) / 2][];
// Шаг 3 - Цикл чтения данных (пока в потоке есть данные)
            int i = 0;
            while (in.available() > 0) {
// Первой из потока читается координата точки X
                double x = in.readDouble();
// Затем - значение графика Y в точке X
                double y = in.readDouble();
// Прочитанная пара координат добавляется в массив
                graphicsData[i++] = new double[]{x, y};
            }
// Шаг 4 - Проверка, имеется ли в списке в результате чтения
//            хотя бы одна пара координат
            if (graphicsData.length > 0) {
// Да - установить флаг загруженности данных
                fileLoaded = true;
// Вызывать метод отображения графика
                display.displayGraphics(graphicsData);
            }
// Шаг 5 - Закрыть входной поток
            in.close();
        } catch (FileNotFoundException ex) {
// В случае исключительной ситуации типа "Файл не найден"
//            показать сообщение об ошибке
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Указанный файл не найден",
                    " Ошибка загрузки данных",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (IOException ex) {
// В случае ошибки ввода из файлового потока показать
//            сообщение об ошибке
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Ошибка чтения координат точек из файла",
                    " Ошибка загрузки данных",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    protected void saveToGraphicsFile(File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new
                    FileOutputStream(selectedFile));
            for (int i = 0; i < display.getGraphicsData().length; i++) {
                out.writeDouble(display.getGraphicsData()[i][0]);
                out.writeDouble(display.getGraphicsData()[i][1]);
            }
            out.close();
        } catch (Exception ignored) {
        }
    }

    public static void main(String[] args) {
// Создать и показать экземпляр главного окна приложения
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Класс-слушатель событий, связанных с отображением меню
    private class MenusListener implements MenuListener {
        // Обработчик, вызываемый перед показом меню
        public void menuSelected(MenuEvent e) {
// Доступность или недоступность элементов меню "График"
//            определяется загруженностью данных
            showAxisMenuItem.setEnabled(fileLoaded);
            showMarkersMenuItem.setEnabled(fileLoaded);
            showFillingMenuItem.setEnabled(fileLoaded);
            rotatedMenuItem.setEnabled(fileLoaded);
            saveToGraphicsItem.setEnabled(fileLoaded);
        }

        // Обработчик, вызываемый после того, как меню исчезло с экрана
        public void menuDeselected(MenuEvent e) {
        }
// Обработчик, вызываемый в случае отмены выбора пункта меню
//(очень редкая ситуация)

        public void menuCanceled(MenuEvent e) {
        }
    }
}