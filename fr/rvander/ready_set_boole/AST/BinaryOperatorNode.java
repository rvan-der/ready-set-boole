package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


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


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		varsSet = this.operands[0].getVariables(varsSet);
		return this.operands[1].getVariables(varsSet);
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean left = this.operands[0].evaluate(hypothesis);
		boolean right = this.operands[1].evaluate(hypothesis);

		switch (this.operator) {
			case '&': result = left && right ? true : false; break;
			case '|': result = left || right ? true : false; break;
			case '^': result = !left && right || left && !right ? true : false; break;
			case '>': result = !left || right ? true : false; break;
			case '=': result = left == right ? true : false; break;
			default:
				System.err.println("Warning! Error at evaluation : unknown operator '"
					+ this.operator + "'. False was returned by default.");
				result = false;
		}

		return result;
	}


	protected AstNode rewriteNnf() {
		AstNode newSubtree;

		switch (this.operator) {
			case '>':
				newSubtree = RewriteSubtrees.materialCondition(
					this.operands[0], this.operands[1]);
				break;
			case '=':
				newSubtree = RewriteSubtrees.equivalence(
					this.operands[0], this.operands[1]);
				break;
			default:
				newSubtree = null;
		}

		if (newSubtree == null) {
			this.operands[0] = this.operands[0].rewriteNnf();
			this.operands[1] = this.operands[1].rewriteNnf();
			return this;
		}
		else {
			return newSubtree.rewriteNnf();
		}
	}


	protected AstNode rewriteCnf() {
		AstNode newSubtree;
		AstNode a, b, c;

		if (this.operator == '|') {
			if (this.operands[0].programSymbol.equals("|")) {
				a = this.operands[1];
				b = this.operands[0].operands[0];
				c = this.operands[0].operands[1];
				newSubtree = RewriteSubtrees.distribution(a, b, c, '|');
				return newSubtree.rewriteCnf();
			}
			if (this.operands[1].programSymbol.equals("|")) {
				a = this.operands[0];
				b = this.operands[1].operands[0];
				c = this.operands[1].operands[1];
				newSubtree = RewriteSubtrees.distribution(a, b, c, '|');
				return newSubtree.rewriteCnf();
			}
		}
		this.operands[0] = this.operands[0].rewriteNnf();
		this.operands[1] = this.operands[1].rewriteNnf();
		return this;
	}


	protected AstNode copySubtree() {
		AstNode copy = new BinaryOperatorNode(this.operator);
		AstNode[] operandsCopy = new AstNode[2];
		operandsCopy[0] = this.operands[0].copySubtree();
		operandsCopy[1] = this.operands[1].copySubtree();
		copy.setOperands(operandsCopy);
		return copy;
	}
}