package automata;

public class Function {
	char currentState;
	char symbol;
	char resultState;

	public Function(char currentState, char symbol, char resultState) {
		this.currentState = currentState;
		this.symbol = symbol;
		this.resultState = resultState;
	}
	
	@Override
	public String toString() {
		return "F("  + currentState + "," + symbol  +  ")=" + resultState;
	}
}
