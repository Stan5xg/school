package automata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Chars implements Iterable<Chars> {
	
    private Set<Character> values = new HashSet<>();
    
    public Chars(Character... chars) {
    	values.addAll(Arrays.asList(chars));
	}
    
    public Chars(Chars chars) {
    	if (chars != null)
    		this.values.addAll(chars.values);
    }

	public int size() {
    	return values.size();
    }
    
    @Override
    public int hashCode() {
    	return values.hashCode();
    }
    
    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Chars)) {
        	return false;
        }
        Chars otherChars = (Chars)anObject; 
        return values.equals(otherChars.values);
    }
    
    @Override
    public String toString() {
    	StringBuffer result = new StringBuffer();
    	for (Character c : values) {
			result.append(c.toString());
			result.append(", ");
		}
    	result.replace(result.length()-2, result.length(), "");
    	return result.toString();
    }

	public void add(Chars c) {
		if (c != null) {
			values.addAll(c.values);
		}
	}
	
	public static void main(String[] args) {
		Chars chars = new Chars('b');
		Chars chars1 = new Chars('b');
		
		System.out.println(chars.hashCode() + " " + chars1.hashCode());
		System.out.println(chars1.equals(chars));
	}



	@Override
	public Iterator<Chars> iterator() {
		return new Iterator<Chars>() {
			
			Iterator<Character> valuesItereator = values.iterator();
			
			@Override
			public Chars next() {
				return new Chars(valuesItereator.next());
			}
			
			@Override
			public boolean hasNext() {
				return valuesItereator.hasNext();
			}
		};
	}

	public void replace(Chars newChars) {
		this.values = newChars.values;		
	}
	
}
