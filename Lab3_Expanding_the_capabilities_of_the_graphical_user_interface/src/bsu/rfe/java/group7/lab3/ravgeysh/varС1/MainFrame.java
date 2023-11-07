package bsu.rfe.java.group7.lab3.ravgeysh.varС1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;

	private Double[] coefficients; // Массив коэффициентов многочлена
	
	private JFileChooser fileChooser = null;
	private JMenuItem saveToTextMenuItem; // Элементы меню вынесены в поля данных класса, так как ими необходимо манипулировать из разных мест
	private JMenuItem saveToGraphicsMenuItem;
	private JMenuItem saveToCSVMenuItem;
	private JMenuItem searchValueMenuItem;
	private JMenuItem searchValueMenuItemForRange;

    private JMenuItem infoMenuItem;
	
	private JTextField textFieldFrom; // Поля ввода для считывания значений переменных
	private JTextField textFieldTo;
	private JTextField textFieldStep;
	private Box hBoxResult;

	private GornerTableCellRenderer renderer = new GornerTableCellRenderer(); // Визуализатор ячеек таблицы

	private GornerTableModel data; // Модель данных с результатами вычислений

	public MainFrame(Double[] coefficients) {
		super("Табулирование многочлена на отрезке по схеме Горнера");
		
		this.coefficients = coefficients;
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2); // Отцентрировать окно приложения на экране

		JMenuBar menuBar = new JMenuBar(); // Создать меню
		setJMenuBar(menuBar); // Установить меню в качестве главного меню приложения
		JMenu fileMenu = new JMenu("Файл"); // Добавить в меню пункт меню "Файл"
		menuBar.add(fileMenu); // Добавить его в главное меню
		JMenu tableMenu = new JMenu("Таблица"); // Создать пункт меню "Таблица"
		menuBar.add(tableMenu); // Добавить его в главное меню
        JMenu infoMenu = new JMenu("Справка");
        menuBar.add(infoMenu);

		
		Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") { // Создать новое "действие" по сохранению в текстовый файл
			public void actionPerformed(ActionEvent event) {
				if (fileChooser == null) {
					fileChooser = new JFileChooser(); // Если экземпляр диалогового окна "Открыть файл" ещѐ не создан, то создать его
					fileChooser.setCurrentDirectory(new File(".")); // и инициализировать текущей директорией
				}
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) // Показать диалоговое окно
					saveToTextFile(fileChooser.getSelectedFile()); // Если результат его показа успешный, сохранить данные в текстовый файл
			}
		};
		saveToTextMenuItem = fileMenu.add(saveToTextAction); // Добавить соответствующий пункт подменю в меню "Файл"
		saveToTextMenuItem.setEnabled(false); // По умолчанию пункт меню является недоступным (данных ещѐ нет)
		
		Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") { // Создать новое "действие" по сохранению в текстовый файл
			public void actionPerformed(ActionEvent event) {
				if (fileChooser == null) {
					fileChooser = new JFileChooser(); // Если экземпляр диалогового окна "Открыть файл" ещѐ не создан,то создать его
					fileChooser.setCurrentDirectory(new File(".")); // и инициализировать текущей директорией
				}
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) // Показать диалоговое окно
					saveToGraphicsFile(fileChooser.getSelectedFile()); // Если результат его показа успешный, сохранить данные в двоичный файл
			}
		};
		saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction); // Добавить соответствующий пункт подменю в меню "Файл"
		saveToGraphicsMenuItem.setEnabled(false); // По умолчанию пункт меню является недоступным(данных ещѐ нет)
		
		Action saveToCSVAction = new AbstractAction("Сохранить данные в CSV файл") {
			public void actionPerformed(ActionEvent event) {
				if (fileChooser == null) {
					fileChooser = new JFileChooser(); 
					fileChooser.setCurrentDirectory(new File(".")); 
				}
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)saveToCSVFile(fileChooser.getSelectedFile());
			}
		};
		saveToCSVMenuItem = fileMenu.add(saveToCSVAction);
		
		Action searchValueAction = new AbstractAction("Найти значение многочлена") { // Создать новое действие по поиску значений многочлена
			public void actionPerformed(ActionEvent event) {
				String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска","Поиск значения", JOptionPane.QUESTION_MESSAGE); // Запросить пользователя ввести искомую строку
				renderer.setNeedle(value); // Установить введенное значение в качестве иголки
				getContentPane().repaint(); // Обновить таблицу
			}
		};

		Action searchValueActionForRange = new AbstractAction("Найти из диапазона") {
			public void actionPerformed(ActionEvent event) {
				String valueStart = JOptionPane.showInputDialog(MainFrame.this, "Введите начало диапазона для поиска","Поиск значения", JOptionPane.QUESTION_MESSAGE);
				String valueEnd = JOptionPane.showInputDialog(MainFrame.this, "Введите конец диапазона для поиска","Поиск значения", JOptionPane.QUESTION_MESSAGE);
				renderer.setNeedleStart(Double.parseDouble(valueStart));
				renderer.setNeedleEnd(Double.parseDouble(valueEnd));
				getContentPane().repaint();
			}
		};
		
		AbstractAction aboutProgrammAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                int result = fileChooser.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    java.io.File selectedFile = fileChooser.getSelectedFile();
                    
                    String surname = "Равгейша";
                    String group = "7";
                    
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Фамилия: " + surname));
                    panel.add(new JLabel("Группа: " + group));
                    panel.add(new JLabel(new ImageIcon(selectedFile.getAbsolutePath())));
                    
                    JOptionPane.showMessageDialog(null, panel, "О программе", JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        infoMenuItem = infoMenu.add(aboutProgrammAction);
		
		searchValueMenuItem = tableMenu.add(searchValueAction); //Добавить действие в меню "Таблица"
		searchValueMenuItem.setEnabled(false); //По умолчанию пункт меню является недоступным (данных ещѐ нет)
		searchValueMenuItemForRange = tableMenu.add(searchValueActionForRange);
		searchValueMenuItemForRange.setEnabled(false);
		JLabel labelForFrom = new JLabel("X изменяется на интервале от:"); //Создать область с полями ввода для границ отрезка и шага Создать подпись для ввода левой границы отрезка
		textFieldFrom = new JTextField("0.0", 10); //Создать текстовое поле для ввода значения длиной в 10 символов со значением по умолчанию 0.0
		textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize()); //Установить максимальный размер равный предпочтительному, чтобы предотвратить увеличение размера поля ввода
		JLabel labelForTo = new JLabel("до:"); //Создать подпись для ввода левой границы отрезка
		textFieldTo = new JTextField("1.0", 10); //Создать текстовое поле для ввода значения длиной в 10 символов со значением по умолчанию 1.0
		textFieldTo.setMaximumSize(textFieldTo.getPreferredSize()); //Установить максимальный размер равный предпочтительному, чтобы предотвратить увеличение размера поля ввода
		JLabel labelForStep = new JLabel("с шагом:"); //Создать подпись для ввода шага табулирования
		textFieldStep = new JTextField("0.1", 10); //Создать текстовое поле для ввода значения длиной в 10 символов со значением по умолчанию 1.0
		textFieldStep.setMaximumSize(textFieldStep.getPreferredSize()); //Установить максимальный размер равный предпочтительному, чтобы предотвратить увеличение размера поля ввода
		Box hboxRange = Box.createHorizontalBox(); //Создать контейнер 1 типа "коробка с горизонтальной укладкой"
		hboxRange.setBorder(BorderFactory.createBevelBorder(1)); //Задать для контейнера тип рамки "объѐмная"
		hboxRange.add(Box.createHorizontalGlue()); //Добавить "клей" C1-H1
		hboxRange.add(labelForFrom); //Добавить подпись "От"
		hboxRange.add(Box.createHorizontalStrut(10)); //Добавить "распорку" C1-H2
		hboxRange.add(textFieldFrom); //Добавить поле ввода "От"
		hboxRange.add(Box.createHorizontalStrut(20)); //Добавить "распорку" C1-H3
		hboxRange.add(labelForTo); //Добавить подпись "До"
		hboxRange.add(Box.createHorizontalStrut(10)); //Добавить "распорку" C1-H4
		hboxRange.add(textFieldTo); //Добавить поле ввода "До"
		hboxRange.add(Box.createHorizontalStrut(20)); //Добавить "распорку" C1-H5
		hboxRange.add(labelForStep); //Добавить подпись "с шагом"
		hboxRange.add(Box.createHorizontalStrut(10)); //Добавить "распорку" C1-H6
		hboxRange.add(textFieldStep); //Добавить поле для ввода шага табулирования
		hboxRange.add(Box.createHorizontalGlue()); //Добавить "клей" C1-H7
		
		//Установить предпочтительный размер области равным удвоенному минимальному, чтобы при компоновке область совсем не сдавили
		hboxRange.setPreferredSize(new Dimension(new Double(hboxRange.getMaximumSize().getWidth()).intValue(),new Double(hboxRange.getMinimumSize().getHeight()).intValue() * 2));

		getContentPane().add(hboxRange, BorderLayout.NORTH); //Установить область в верхнюю (северную) часть компоновки

		JButton buttonCalc = new JButton("Вычислить"); //Создать кнопку "Вычислить"
		buttonCalc.addActionListener(new ActionListener() { //Задать действие на нажатие "Вычислить" и привязать к кнопке
			public void actionPerformed(ActionEvent ev) {
				try {
					Double from = Double.parseDouble(textFieldFrom.getText()); //Считать значения начала и конца отрезка, шага
					Double to = Double.parseDouble(textFieldTo.getText());
					Double step = Double.parseDouble(textFieldStep.getText());
					data = new GornerTableModel(from, to, step, MainFrame.this.coefficients); //На основе считанных данных создать новый экземпляр модели таблицы
					JTable table = new JTable(data); //Создать новый экземпляр таблицы
					table.setDefaultRenderer(Double.class, renderer); //Установить в качестве визуализатора ячеек для класса Double разработанный визуализатор
					table.setRowHeight(30); //Установить размер строки таблицы в 30 пикселов
					hBoxResult.removeAll(); //Удалить все вложенные элементы из контейнера hBoxResult
					hBoxResult.add(new JScrollPane(table)); //Добавить в hBoxResult таблицу, "обѐрнутую" в панель с полосами прокрутки
					getContentPane().validate(); //Обновить область содержания главного окна
					saveToTextMenuItem.setEnabled(true); //Пометить ряд элементов меню как доступных
					saveToGraphicsMenuItem.setEnabled(true);
					searchValueMenuItem.setEnabled(true);
					searchValueMenuItemForRange.setEnabled(true);
				} catch (NumberFormatException ex) { //В случае ошибки преобразования чисел показать сообщение об ошибке
					JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой","Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton buttonReset = new JButton("Очистить поля"); //Создать кнопку "Очистить поля"
		buttonReset.addActionListener(new ActionListener() { //Задать действие на нажатие "Очистить поля" и привязать к кнопке
			public void actionPerformed(ActionEvent ev) {
				textFieldFrom.setText("0.0"); //Установить в полях ввода значения по умолчанию
				textFieldTo.setText("1.0");
				textFieldStep.setText("0.1");
				hBoxResult.removeAll(); //Удалить все вложенные элементы контейнера hBoxResult
				hBoxResult.add(new JPanel()); //Добавить в контейнер пустую панель
				saveToTextMenuItem.setEnabled(false); //Пометить элементы меню как недоступные
				saveToGraphicsMenuItem.setEnabled(false);
				searchValueMenuItem.setEnabled(false);
				searchValueMenuItemForRange.setEnabled(false);
				getContentPane().validate(); //Обновить область содержания главного окна
			}
		});
		Box hboxButtons = Box.createHorizontalBox(); //Поместить созданные кнопки в контейнер
		hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.add(buttonCalc);
		hboxButtons.add(Box.createHorizontalStrut(30));
		hboxButtons.add(buttonReset);
		hboxButtons.add(Box.createHorizontalGlue());
		
		//Установить предпочтительный размер области равным удвоенному минимальному, чтобы при компоновке окна область совсем не сдавили
		hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new Double(hboxButtons.getMinimumSize().getHeight()).intValue() * 2));
		getContentPane().add(hboxButtons, BorderLayout.SOUTH); //Разместить контейнер с кнопками в нижней (южной) области граничной компоновки
		hBoxResult = Box.createHorizontalBox(); //Область для вывода результата пока что пустая
		hBoxResult.add(new JPanel());
		getContentPane().add(hBoxResult, BorderLayout.CENTER); //Установить контейнер hBoxResult в главной (центральной) области граничной компоновки
	}

	protected void saveToGraphicsFile(File selectedFile) {
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile)); //Создать новый байтовый поток вывода, направленный в указанный файл
			for (int i = 0; i < data.getRowCount(); i++) { //Записать в поток вывода попарно значение X в точке,значение многочлена в точке
				out.writeDouble((Double) data.getValueAt(i, 0));
				out.writeDouble((Double) data.getValueAt(i, 1));
				out.writeDouble((Double) data.getValueAt(i, 2));
				out.writeDouble((Double) data.getValueAt(i, 3));
			}
			out.close(); //Закрыть поток вывода
		} catch (Exception e) {}
	}

	protected void saveToTextFile(File selectedFile) {
		try {
			PrintStream out = new PrintStream(selectedFile); //Создать новый символьный поток вывода, направленный вуказанный файл
//			out.println("Результаты табулирования многочлена по схемеГорнера"); //Записать в поток вывода заголовочные сведения
//			out.print("Многочлен: ");
//			for (int i = 0; i < coefficients.length; i++) {
//				out.print(coefficients[i] + "*X^" + (coefficients.length - i - 1));
//				if (i != coefficients.length - 1)
//					out.print(" + ");
//			}
//			out.println("");
//			out.println("Интервал от " + data.getFrom() + " до " + data.getTo() + " с шагом " + data.getStep());
//			out.println("===================================================="); // Записать в поток вывода значения в точках
//			for (int i = 0; i < data.getRowCount(); i++) {
//				out.println("Значение в точке " + data.getValueAt(i, 0) + " равно " + data.getValueAt(i, 1));
//			}
			
			for (int i = 0; i < data.getRowCount(); i++) {
				out.println(data.getValueAt(i, 0));
				out.println(data.getValueAt(i, 1));
			}
			out.close();
		} catch (FileNotFoundException e) {}
	}
	
	protected void saveToCSVFile(File selectedFile) {
		try { 
			PrintStream out = new PrintStream(selectedFile);
//			for (int i = 0; i < data.getRowCount(); i++) {
//				out.println(data.getValueAt(i, 0) + "," + data.getValueAt(i, 1) + "," + data.getValueAt(i, 2) +  "," + data.getValueAt(i, 3));
//			}
			for (int i = 0; i < data.getRowCount(); i++) {
				out.println(data.getValueAt(i, 0));
				out.println(data.getValueAt(i, 1));
			}
				
			out.close();
		} catch (FileNotFoundException e) {}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
			System.exit(-1);
		}

		Double[] coefficients = new Double[args.length];
		int i = 0;
		try {
			for (String arg : args) {
				coefficients[i++] = Double.parseDouble(arg);
			}
		} catch (NumberFormatException ex) {
			System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
			System.exit(-2);
		}
		MainFrame frame = new MainFrame(coefficients);
			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Задать действие, выполняемое при закрытии окна
		frame.setVisible(true);
	}
}
