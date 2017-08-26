package automata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class GrammarTest {
	public static void main(String[] args) {
		
		
		Set<Rule> p = new LinkedHashSet<Rule>() {
			{
				add(new Rule('X','0','Y'));
				add(new Rule('X','1','Z'));
				add(new Rule('X',Grammar.EMPTY));
				
				add(new Rule('Y','0','Z'));
				add(new Rule('Y','~','W'));
				add(new Rule('Y','#'));

				add(new Rule('Z','1','Y'));
				add(new Rule('Z','1','W'));
				add(new Rule('Z','0','V'));

				add(new Rule('W','0','W'));
				add(new Rule('W','1','W'));
				add(new Rule('W','#'));

				add(new Rule('V','&','Z'));
			}
		};
		
		Character[] vTArr = {'0','1','~','#','&'};
		
		Set<Character> vT = new LinkedHashSet<>();
		vT.addAll(Arrays.asList(vTArr));
		
		Character[] vNArr = {'X','Y','Z','W','V'};
		Set<Character> vN = new LinkedHashSet<>();
		vN.addAll(Arrays.asList(vNArr));

		Character s = 'X';
	
		
		Grammar g = new Grammar(vT,vN,p,s);
		
		Automata m = GrammarToAutomataConverter.convert(g);
		System.out.println(m);
		Table t = new Table(m);
//		System.out.println(t.getValueAt(0, 0));
		
		Graphics.createAndShowGUI(t);
		


		
		
	}
}
