package bsu.rfe.java.group7.lab3.ravgeysh.varС1;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	
	// Ищем ячейки, строковое представление которых совпадает с needle(иголкой). 
	// Применяется аналогия поиска иголки в стоге сена, в роли стога сена - таблица
	private String needle = null;
	private Double needleStart = null;
	private Double needleEnd = null;
	
	private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();   
	
	public GornerTableCellRenderer() {
		formatter.setMaximumFractionDigits(5); // Показывать только 5 знаков после запятой
		// Не использовать группировку (т.е. не отделять тысячи ни запятыми, ни пробелами), т.е. показывать число как "1000", а не "1 000" или "1,000"
		formatter.setGroupingUsed(false);
		// Установить в качестве разделителя дробной части точку, а не запятую. 
		// По умолчанию, в региональных настройках Россия/Беларусь дробная часть отделяется запятой
		DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
		dottedDouble.setDecimalSeparator('.');
		formatter.setDecimalFormatSymbols(dottedDouble);
		panel.add(label); //Разместить надпись внутри панели
		panel.setLayout(new FlowLayout(FlowLayout.LEFT)); //Установить выравнивание надписи по левому краю панели
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		String formattedDouble = formatter.format(value); //Преобразовать double в строку с помощью форматировщика
		label.setText(formattedDouble); //Установить текст надписи равным строковому представлению числа
		
	    int sum = col + row;
	    if (sum % 2 == 0) {
	        panel.setBackground(Color.BLACK);
	        label.setForeground(Color.WHITE);
	    } else {
	        panel.setBackground(Color.WHITE);
	        label.setForeground(Color.BLACK);
	    }
		
		if (col == 1 && needle != null && needle.equals(formattedDouble)) {
			panel.setBackground(Color.RED);
		}
		
		if (needleStart != null && needleEnd != null) {
			if (Double.parseDouble(formattedDouble) >= needleStart && Double.parseDouble(formattedDouble) <= needleEnd) {
					panel.setBackground(Color.GREEN);
			}
		}
		return panel;
	}

	public void setNeedle(String needle) {
		this.needle = needle;
	}
	public void setNeedleStart(Double needleStart) {
		this.needleStart = needleStart;
	}
	public void setNeedleEnd(Double needleEnd) {
		this.needleEnd = needleEnd;
	}
	
	
}
