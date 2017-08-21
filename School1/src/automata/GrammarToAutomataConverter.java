package automata;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GrammarToAutomataConverter {


	public static Automata convert(Grammar g) {
		addNonterminal(g); //Шаг 1
		
		return null;
	}

	private static void addNonterminal(Grammar g) {
		Set<Function> f = new HashSet<>();
		Set<Character> z = new HashSet<>();
		for (Rule rule : g.p) {
			if (rule.right.length == 2) {
				f.add(new Function(rule.left,rule.right[0],rule.right[1]));
			} else {
				convertRuleWithTerminalOnly(g, z, f, rule);
			}
			
		}
	}

	//Производит конструирование функции перехода для правил вида А->a (без нетерминала в правой части)   
	private static void convertRuleWithTerminalOnly(Grammar g, Set<Character> z, Set<Character> f,  Rule rule) {
		if (isSToEmpty(g, rule)) {
			z.add(rule.left);
		} else {
			for (Rule otherRule : g.p) {
				if (sameExceptNonTerminal(rule, otherRule)) {
					z.add(rule.right[1]);
				}
			}
		}
	}

	//Сравнивает два метода чтобы определить, является ли otherRule правилом вида A->aB если rule - правило вида A->a 
	private static boolean sameExceptNonTerminal(Rule rule, Rule otherRule) {
		if (otherRule.right.length != 2 || rule.right.length != 1) {
			return false;
		}
		return (otherRule.left == rule.left) && (otherRule.right[0] == rule.right[0]);
	}

	//Является ли правило rule правило вида S->ε
	private static boolean isSToEmpty(Grammar g, Rule rule) {
		if (rule.right.length != 1) {
			return false;
		}
		return (rule.right[0] == Grammar.EMPTY) && (rule.left == g.s);
	}
	
	
	
	
}
