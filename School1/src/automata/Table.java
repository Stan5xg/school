package automata;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.tools.ToolProvider;

public class Table extends AbstractTableModel {
	
	private static final int DEFAULT_ROW_SIZE = 10;
	private static final int DEFAULT_COL_SIZE = 10;
	private static final double ROW_TRESHOLD = 0.75;
	private static final double COL_TRESHOLD = 0.75;

	Map<Chars, Integer> headIndex = new LinkedHashMap<>();
	List<Chars> header = new ArrayList<>();
	Map<Chars, Integer> leftIndex = new LinkedHashMap<>();
	List<Chars> lefter = new ArrayList<>();
	
	Chars[][] data = new Chars[DEFAULT_ROW_SIZE][DEFAULT_COL_SIZE];
	int colSize = 0;
	int rowSize = 0;
	
	
	public Table(Automata m) {
		for (Function function : m.f) {
			add(function.currentState, function.symbol, function.resultState); 
		}
	
	}
	
	
	
	public Table() {
		// TODO Auto-generated constructor stub
	}



	private void add(char currState, char sym, char resState) {
		System.out.print(currState + "," + sym + " -> "+ resState);
		Chars symbol = new Chars(sym);
		Chars currentState = new Chars(currState);
		Chars resultState = new Chars(resState);
		
		Integer rowIndex = leftIndex.get(symbol);
		if (rowIndex == null) {
			leftIndex.put(symbol, rowSize++);
			lefter.add(symbol);
			checkSize();
			rowIndex = rowSize;
		} 
		
		Integer colIndex = headIndex.get(currentState);
		if (colIndex == null) {
			headIndex.put(currentState, colSize++);
			header.add(currentState);
			checkSize();
			colIndex = colSize;
		} 
		
		System.out.println("row: " + rowIndex + " col: " + colIndex);
		
		Chars chars = data[rowIndex][colIndex];
		if (chars == null) {
			data[rowIndex][colIndex] = resultState;
		} else {
			chars.add(resultState);
		}
		
		System.out.println("Head:" + headIndex);
		System.out.println("Left:" + leftIndex);
	}




	private void resize(int newRowSize, int newColSize) {		
		Chars[][] oldData = data;
		data = new Chars[newRowSize][newColSize];
		for (int i = 0; i < oldData.length-1; i++) {
			for (int j = 0; j < oldData[i].length-1; j++) {
				data[i][j] = oldData[i][j];
			}
		}
	}


	private void checkSize() {
		boolean changed = false;

		 
		int newRowSize = data.length;
		int newColSize = data.length == 0 ? 0 : data[0].length;

		if (rowSize >= newRowSize * ROW_TRESHOLD) {
			newRowSize = newRowSize * 2 + 1;
			changed = true;
		}
		
		if (colSize >= newColSize * COL_TRESHOLD) {
			newColSize = newColSize * 2 + 1;
			changed = true;
		}
		
		if (changed) {
			resize(newRowSize, newColSize);			
		}
	}



	@Override
	public int getRowCount() {
		return rowSize;
	}

	@Override
	public int getColumnCount() {
		return colSize+1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex > 0) 
			return data[rowIndex][columnIndex-1];
		else {
			return lefter.get(rowIndex);
		}
	}
	
	@Override
	public String getColumnName(int column) {
		if (column > 0) {
			return header.get(column-1).toString();			
		} else 
			return "";
	}
	
	public static void main(String[] args) {
		Chars chars = new Chars('B');
		Table table = new Table();
		table.headIndex.put(new Chars('A'), 1);
		table.headIndex.put(new Chars('B'), 2);
		table.headIndex.put(new Chars('C'), 3);
		System.out.println(table.headIndex);
	}
	
	
}
