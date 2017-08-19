package automata;

import java.util.Arrays;

public class Rule {
	char left;
	char[] right;

	public Rule(char left, char... right) {
		this.left = left;
		if (right.length > 2 || right.length < 1) {
			throw new IllegalArgumentException("Right part of rule must consist of 1 or 2 symbols. Found: " + right.length);
		}
		this.right = right;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(left);
		result.append("->");
		for (char c : right) {
			result.append(c);
		}
		return result.toString();
	}
	
	
}
