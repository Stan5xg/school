package automata;

import java.util.LinkedHashSet;
import java.util.Set;

public class UndeterminedToDetermined {
	public static Table convert(Table undetermined) {
		Table result = new Table();
		
		Column<Function> firstColumnFunctions = undetermined.column(0);
		
		
		
		//заполняем первый столбец
		for (Function function : firstColumnFunctions) {
			System.out.println(function);
			result.add(function);
		}
		
		boolean stop = false;
		
		int i = 0; //индекс рабочего столюбца
		while(!stop) {
			Column<Function> currCol = result.column(i); //получаем рабочий столбец
			Set<Chars> headsToAdd = new LinkedHashSet<>(); //множество исходных состояний для добавления в результат
			
			
			//перебираем все функции рабочего столбца
			for (Function function : currCol) {
				Chars newHead = function.resultState;
				//не берём исходные состояния без значений или уже имеющиеся в таблице
				if (newHead != null && !result.hasHead(newHead)) {
					headsToAdd.add(newHead);
				}
			}
			
			stop = headsToAdd.size() == 0 && i == result.colSize - 1;
			
			//перебираем все исходные состояния
			for (Chars head : headsToAdd) {
				//получаем столбцы для каждого исходного состояния и добавляем все функции из них в результат
				for (Function functionFromColumnWithHead : undetermined.column(head)) {
					result.replaceWithActualHeader(functionFromColumnWithHead);
					result.add(functionFromColumnWithHead);
				}				
			}

			i++;			
		}
		
		
		result.replaceDoubleSymbols();
		

	    return result;
	}



}
