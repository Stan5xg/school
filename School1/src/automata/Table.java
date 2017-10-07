package automata;

import java.awt.IllegalComponentStateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4451769843472682967L;
	private static final int DEFAULT_ROW_SIZE = 10;
	private static final int DEFAULT_COL_SIZE = 10;
	private static final double ROW_TRESHOLD = 0.75;
	private static final double COL_TRESHOLD = 0.75;

	Map<Chars, Integer> headIndex = new LinkedHashMap<>();
	List<Chars> header = new ArrayList<>();
	Map<Chars, Integer> leftIndex = new LinkedHashMap<>();
	List<Chars> lefter = new ArrayList<>();
	
	Chars[][] data = new Chars[DEFAULT_ROW_SIZE][DEFAULT_COL_SIZE];
	/*
	 *  	col1 col2 col3
	 *  row1
	 *  row2
	 *  row3
	 *  
	 *  data[rowN][colN]
	 */
	int colSize = 0;
	int rowSize = 0;
		
	public Table() {
	}
	
	public Table(Automata m) {
		for (Function f : m.f) {
			add(f);
		}
		for (char c: m.q) {
			addToHead(new Chars(c));
		}
		for (char c: m.t) {
			addToRow(new Chars(c));
		}
	}



	public void add(Function f) {
			add(f.currState, f.symbol, f.resultState);					
	}

	public void add(Chars currentState, Chars symbol, Chars resultState) {
		
		Integer rowIndex = leftIndex.get(symbol);
		if (rowIndex == null) {
			rowIndex = rowSize;
			addToRow(symbol);
		} 
		
		Integer colIndex = headIndex.get(currentState);
		
		if (colIndex == null) {
			colIndex = colSize;
			addToHead(currentState);
		} 
		
		
		Chars chars = data[rowIndex][colIndex];
		if (chars == null) {
			data[rowIndex][colIndex] = resultState;
		} else {
			chars.add(resultState);
		}
		
	}



	private void addToHead(Chars currentState) {
		if (headIndex.containsKey(currentState)) {
			return;
		}
		headIndex.put(currentState, colSize++);
		header.add(currentState);
		checkSize();
	}



	private void addToRow(Chars symbol) {
		if (leftIndex.containsKey(symbol)) {
			return;
		}
		leftIndex.put(symbol, rowSize++);
		lefter.add(symbol);
		checkSize();
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
		return lefter.size();
	}

	@Override
	public int getColumnCount() {
		return header.size()+1;
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
	

	public boolean hasHead(Chars currentState) {
		return header.contains(currentState);
	}

	public Column<Function> column(int columnIndex) {
		return new Column<Function>() {

			@Override
			public Iterator<Function> iterator() {
				return new Iterator<Function>(){					
					int i = 0;

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return i < lefter.size();
					}

					@Override
					public Function next() {
						Function result = new Function(header.get(columnIndex), lefter.get(i), data[i++][columnIndex]);
						return result;
					}
					
				};
			}
		};
	}

	public Column<Function> column(Chars head) {
		
		if (head == null || head.size() < 1) {
			return null;
		}
		
		
		if (hasHead(head)) {
			return column(headIndex.get(head));
		}			
		
		
		return new Column<Function>() {

			@Override
			public Iterator<Function> iterator() {
				return new Iterator<Function>(){					
					int i = 0;

					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return i < lefter.size();
					}

					@Override
					public Function next() {
						Function resultFunction = new Function();
						Chars symbol = lefter.get(i);
						for (Chars headChar : head) {
							int headCharIndex = headIndex.get(headChar);
							
							//Chars currState = new Chars(header.get(headCharIndex));
							
							Chars resultState = data[i][headCharIndex];
							
							if (resultState != null) {
								resultState = new Chars(resultState);
							}
							
							resultFunction.add(head, symbol, resultState);
						}
						i++;
						return resultFunction;
					}
					
				};
			}
		};
	}
	
	
	
	
	
	static Chars getNewChars(List<Chars> existing) {
		Chars result = new Chars('N');
		Random r = new Random();
		while (existing.contains(result)) {
			int charInt = 'A' + r.nextInt('Z' - 'A' + 1);
			result = new Chars((char)charInt); 
		}
		return result;
	}

	public void replaceDoubleSymbols() {
		for (Chars headChars : header) {
			if (headChars.size() > 1) {
				headChars.replace(getNewChars(header));
			}
		}
	}

	public void replaceWithActualHeader(Function function) {
		Chars resState = function.resultState;
		Integer iHead = headIndex.get(resState);
		if (iHead != null) {
			function.resultState = header.get(iHead);
		}
		
	}


	
	
	
}
