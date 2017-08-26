package automata;

import java.util.Arrays;

public class Chars {
    private int hash; 
	
    private char value[];
    
    public Chars(char... chars) {
		value = chars;
	}
    
    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
    
    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Chars) {
            Chars anotherString = (Chars)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
    	StringBuffer result = new StringBuffer();
    	for (char c : value) {
			result.append(c);
			result.append(", ");
		}
    	result.replace(result.length()-2, result.length(), "");
    	return result.toString();
    }

	public void add(Chars c) {
		char[] oldVal = value;
		value = Arrays.copyOf(oldVal, oldVal.length+c.value.length);		
		System.arraycopy(c.value, 0, value, oldVal.length, c.value.length);
	}
	
	public static void main(String[] args) {
		Chars chars = new Chars('a');
		Chars chars1 = new Chars('a');
		
		System.out.println(chars.hashCode() + " " + chars1.hashCode());
		System.out.println(chars1.equals(chars));
	}
	
}
