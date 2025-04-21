package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class BinaryOperatorNode extends AstNode {

	private char operator;


	protected BinaryOperatorNode (char operator) {
		super(AstNodeType.BINARY_OP);
		this.operator = operator;
		this.programSymbol = String.valueOf(operator);
		switch (operator) {
		case '&': this.mathSymbol = "∧"; break;
		case '|': this.mathSymbol = "∨"; break;
		case '^': this.mathSymbol = "⊕"; break;
		case '>': this.mathSymbol = "⇒"; break;
		case '=': this.mathSymbol = "⇔"; break;
		default: this.mathSymbol = "[symbol not found]";
		}
	}


	protected boolean evaluate() throws AstException {
		if (this.operands == null) {
			throw new AstException(
				"Error at evaluation : missing operands for '"
				+ this.operator + "'. The formula is invalid.");
		}

		boolean result;
		boolean left = this.operands[0].evaluate();
		boolean right = this.operands[1].evaluate();

		switch (this.operator) {
		case '&': result = left && right ? true : false; break;
		case '|': result = left || right ? true : false; break;
		case '^': result = !left && right || left && !right ? true : false; break;
		case '>': result = !left || right ? true : false; break;
		case '=': result = left == right ? true : false; break;
		default:
			throw new AstException(
				"Error at evaluation : unknown operator '"
				+ this.operator + "'.");
		}

		return result;
	}
}