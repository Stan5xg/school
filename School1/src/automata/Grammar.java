package automata;

import java.util.Set;

public class Grammar {
	static final char EMPTY = 'ε'; //Символ пустой цепочки
	Set<Character> vT; //Множество терминалов
	Set<Character> vN; //Множество нетерминалов
	Set<Rule> p; //Множество правил
	Character s; //Начальный символ
	
	public Grammar(Set<Character> vT, Set<Character> vN, Set<Rule> p, Character s) {
		this.vT = vT;
		this.vN = vN;
		this.p = p;
		this.s = s;
		
		//Считаем, что грамматика должна быть правой регулярной
		checkGrammar();
	}

	private void checkGrammar() {
		
		for (Rule rule : p) {
			//Левая часть должна принадлежать множеству нетерминальных символов
			if (!vN.contains(rule.left)) {
				throw new IllegalArgumentException("Rule " + rule + " contains alien character " + rule.left);
			}
			if (rule.right.length > 1) { //Если правая часть состоит из 2х символов
				//...то сначала должен быть терминальный, а затем нетерминальный символ
				if (!vT.contains(rule.right[0]) || !vN.contains(rule.right[1])) {
					throw new IllegalArgumentException("Rule " + rule + " is not in a valid form for right-aligned grammar");
				}
			} else { 
				//иначе, если символ один, то это должен быть терминал либо символ пустой цепочки
				if (!vT.contains(rule.right[0]) && rule.right[0] != EMPTY) {
					throw new IllegalArgumentException("Rule " + rule + " is not in a valid form for right-aligned grammar");
				}
			}
			
		}		
	}
	
	
}
