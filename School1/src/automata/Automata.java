package automata;

import java.util.Set;

public class Automata {
	Set<Character> q; //Множество состояний
	Set<Character> t; //Множество символов
	Set<Function> f; //Множество функций перехода
	Set<Character> h; //Множество начальных состояний автомата
	Set<Character> z; //Множество заключительных состояний
	
	public Automata(Set<Character> q, Set<Character> t, Set<Function> f, Set<Character> h, Set<Character> z) {
		this.q = q;
		this.t = t;
		this.f = f;
		this.h = h;
		this.z = z;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Состояния q: " + q.toString() + '\n');
		result.append("Символы t: " + t.toString() + '\n');
		result.append("Функции f: " + f.toString() + '\n');
		result.append("Начальные состояния h: " + h.toString() + '\n');
		result.append("Заключительные состояния z: " + z.toString() + '\n');
		return result.toString();
	}
	
}
