package automata;

public class Function {
	public Function(char currentState, char symbol, char resultState) {
		this.currentState = currentState;
		this.symbol = symbol;
		this.resultState = resultState;
	}
	char currentState;
	char symbol;
	char resultState;
}
