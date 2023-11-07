package bsu.rfe.java.group7.lab3.ravgeysh.varС1;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
	private Double[] coefficients;
	private Double from;
	private Double to;
	private Double step;

	public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
		this.from = from;
		this.to = to;
		this.step = step;
		this.setCoefficients(coefficients);
	}

	public Double getFrom() {
		return from;
	}

	public Double getTo() {
		return to;
	}

	public Double getStep() {
		return step;
	}

	public int getColumnCount() { // В данной модели два столбца
		return 4; 
	}

	public int getRowCount() { // Вычислить количество точек между началом и концом отрезка исходя из шага табулирования
		return new Double(Math.ceil((to - from) / step)).intValue() + 1;
	}

	public Object getValueAt(int row, int col) {
		double x = from + step * row; // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
		
		Double result = 0.0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
		Double result_inv = 0.0;
        for (int i = 0; i <= coefficients.length - 1; i++) {
        	result_inv = result_inv * x + coefficients[i];
        }
		
		switch(col) {
		case 0:
			return x;
		case 1:
	        return result;
		case 2:
	        return result_inv;
		case 3: 
			return result - result_inv;
		}
		return 0;
	}

	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "Значение X"; // Название 1-го столбца
		case 1:
			return "Значение многочлена"; // Название 2-го столбца
		case 2:
			return "Значение инверсированного многочлена";
		case 3: 
			return "Разница между 2 и 3";
			
		}
		return null;
	}

	public Class<?> getColumnClass(int col) { // И в 1-ом и во 2-ом столбце находятся значения типа Double
		return Double.class;
	}

	public Double[] getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(Double[] coefficients) {
		this.coefficients = coefficients;
	}
}
