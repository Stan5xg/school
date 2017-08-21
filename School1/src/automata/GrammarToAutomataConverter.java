package automata;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GrammarToAutomataConverter {


	public static Automata convert(Grammar g) {
		
		Set<Function> f = new HashSet<>(); //Множество правил
		Set<Character> z = new HashSet<>(); //Множество заключительных состояний
	
		
		for (Rule rule : g.p) {
			if (rule.right.length == 2) {
				f.add(new Function(rule.left,rule.right[0],rule.right[1]));
			} else {
				convertRuleWithTerminalOnly(g, z, f, rule);
			}
			
		}
		
		return null;
	}

	

	//Производит конструирование функции перехода для правил вида А->a или S->ε	 
	private static void convertRuleWithTerminalOnly(Grammar g, Set<Character> z, Set<Function> f,  Rule rule) {

		if (isSToEmpty(g, rule)) {
			z.add(rule.left);
		} else {
			convertRuleWithTerminalOnlyChecked(g, z, f, rule);
		}
	}

	
	//Производит конструирование функции перехода для правил вида А->a (без нетерминала в правой части)   
	private static void convertRuleWithTerminalOnlyChecked(Grammar g, Set<Character> z, Set<Function> f, Rule rule) {
		char resultState = getNewNonTerminal(g); //Новый символ 

		for (Rule otherRule : g.p) {
			if (sameExceptNonTerminal(rule, otherRule)) {
				z.add(rule.right[1]);
			} else {
				f.add(new Function(rule.left,rule.right[0],resultState));
			}
		}
	}

	//Возвращает новый нетерминал, не содержавшийся в грамматике.  
	private static char getNewNonTerminal(Grammar g) {
		char result = 'N';
		Random r = new Random();
		while (g.vN.contains(result)) {
			int charInt = 'A' + r.nextInt('Z' - 'A' + 1);
			result = (char)charInt; 
		}
		return 0;
	}



	//Сравнивает два метода чтобы определить, является ли otherRule правилом вида A->aB если rule - правило вида A->a 
	private static boolean sameExceptNonTerminal(Rule rule, Rule otherRule) {
		if (otherRule.right.length != 2 || rule.right.length != 1) {
			return false;
		}
		return (otherRule.left == rule.left) && (otherRule.right[0] == rule.right[0]);
	}

	//Является ли правило rule правилом вида S->ε
	private static boolean isSToEmpty(Grammar g, Rule rule) {
		if (rule.right.length != 1) {
			return false;
		}
		return (rule.right[0] == Grammar.EMPTY) && (rule.left == g.s);
	}
	
	
	
	
}
