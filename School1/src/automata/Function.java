package automata;

public class Function {
	Chars currState;
	Chars symbol;
	Chars resultState;

	public Function(Chars currState, Chars symbol, Chars resultState) {
		this.currState = currState;
		this.symbol = symbol;
		this.resultState = resultState;
	}
	
	public Function(char currState, char symbol, char resultState) {
		this.currState = new Chars(currState);
		this.symbol = new Chars(symbol);
		this.resultState = new Chars(resultState);
	}

	public Function() {
	}

	@Override
	public String toString() {
		return "F("  + currState + "," + symbol  +  ")=" + resultState;
	}

	public void add(Chars currState, Chars symbol, Chars resultState) {
		if (symbol == null || currState == null) {
			throw new IllegalArgumentException("Can't create function with null current state or symbol. Got: symbol == " + symbol + ", currState == " + currState);
		}
		if (this.currState == null) {
			this.currState = currState;
		} else {
			this.currState.add(currState);
		}
		
		if (this.symbol == null) {
			this.symbol = symbol;
		} else if(!this.symbol.equals(symbol)) {
			throw new IllegalArgumentException("Can't add different symbol to function. Required " + this.symbol + " got " + symbol);
		}
		
		if (this.resultState == null) {
			this.resultState = resultState;
		} else if (resultState != null){
			this.resultState.add(resultState);
		}
	}
}
