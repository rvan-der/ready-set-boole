package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class BinaryOperatorNode extends AstNode {

	private static final long serialVersionUID = -1385074740534226194L;
	private char tOperator;


	protected BinaryOperatorNode (char operator) {
		super(AstNodeType.BINARY_OP);
		tOperator = operator;
		tProgramSymbol = String.valueOf(operator);
		switch (operator) {
			case '&': tMathSymbol = "∧"; break;
			case '|': tMathSymbol = "∨"; break;
			case '^': tMathSymbol = "⊕"; break;
			case '>': tMathSymbol = "⇒"; break;
			case '=': tMathSymbol = "⇔"; break;
			default: tMathSymbol = "[symbol not found]";
		}
	}


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		varsSet = tOperands[0].getVariables(varsSet);
		return tOperands[1].getVariables(varsSet);
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean left = tOperands[0].evaluate(hypothesis);
		boolean right = tOperands[1].evaluate(hypothesis);

		switch (tOperator) {
			case '&': result = left && right ? true : false; break;
			case '|': result = left || right ? true : false; break;
			case '^': result = !left && right || left && !right ? true : false; break;
			case '>': result = !left || right ? true : false; break;
			case '=': result = left == right ? true : false; break;
			default:
				System.err.println("Warning! Error at evaluation : unknown operator '"
					+ tOperator + "'. False was returned by default.");
				result = false;
		}

		return result;
	}

	
	protected AstNode rewriteOnlyJunctions() {
		AstNode newSubtree;

		switch (tOperator) {
			case '>':
				newSubtree = RewriteSubtrees.materialCondition(
					tOperands[0], tOperands[1]);
				break;
			case '=':
				newSubtree = RewriteSubtrees.equivalence(
					tOperands[0], tOperands[1]);
				break;
			case '^':
				newSubtree = RewriteSubtrees.exclusiveDisjunction(
					tOperands[0], tOperands[1]);
				break;
			default:
				newSubtree = null;
		}

		if (newSubtree == null) {
			tOperands[0] = tOperands[0].rewriteNnf();
			tOperands[1] = tOperands[1].rewriteNnf();
			return this;
		}

		return newSubtree.rewriteNnf();
	}


	


	protected AstNode rewriteNnf() {
		AstNode newSubtree;

		switch (tOperator) {
			case '>':
				newSubtree = RewriteSubtrees.materialCondition(
					tOperands[0], tOperands[1]);
				break;
			case '=':
				newSubtree = RewriteSubtrees.equivalence(
					tOperands[0], tOperands[1]);
				break;
			default:
				newSubtree = null;
		}

		if (newSubtree == null) {
			tOperands[0] = tOperands[0].rewriteNnf();
			tOperands[1] = tOperands[1].rewriteNnf();
			return this;
		}
		else {
			return newSubtree.rewriteNnf();
		}
	}


	protected AstNode rewriteCnf() {
		AstNode newSubtree;
		AstNode a, b, c;

		if (tOperator == '|') {
			if (tOperands[0].tProgramSymbol.equals("&")) {
				a = tOperands[1];
				b = tOperands[0].tOperands[0];
				c = tOperands[0].tOperands[1];
				newSubtree = RewriteSubtrees.distribution(a, b, c, '|');
				return newSubtree.rewriteCnf();
			}
			if (tOperands[1].tProgramSymbol.equals("&")) {
				a = tOperands[0];
				b = tOperands[1].tOperands[0];
				c = tOperands[1].tOperands[1];
				newSubtree = RewriteSubtrees.distribution(a, b, c, '|');
				return newSubtree.rewriteCnf();
			}
		}
		tOperands[0] = tOperands[0].rewriteNnf();
		tOperands[1] = tOperands[1].rewriteNnf();
		return this;
	}


	protected AstNode copySubtree() {
		AstNode copy = new BinaryOperatorNode(tOperator);
		AstNode[] operandsCopy = new AstNode[2];
		operandsCopy[0] = tOperands[0].copySubtree();
		operandsCopy[1] = tOperands[1].copySubtree();
		copy.setOperands(operandsCopy);
		return copy;
	}
}