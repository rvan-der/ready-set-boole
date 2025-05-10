package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class BinaryOperatorNode extends AstNode {

	private static final long serialVersionUID = -1385074740534226194L;


	protected BinaryOperatorNode (String token) {
		super(AstNodeType.BINARY_OP, token);
		switch (token) {
			case "&": tMathSymbol = "∧"; break;
			case "|": tMathSymbol = "∨"; break;
			case "^": tMathSymbol = "⊕"; break;
			case ">": tMathSymbol = "⇒"; break;
			case "=": tMathSymbol = "⇔"; break;
			default: tMathSymbol = "[NA]";
		}
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean left = tOperands[0].evaluate(hypothesis);
		boolean right = tOperands[1].evaluate(hypothesis);

		switch (tToken) {
			case "&": result = left && right ? true : false; break;
			case "|": result = left || right ? true : false; break;
			case "^": result = !left && right || left && !right ? true : false; break;
			case ">": result = !left || right ? true : false; break;
			case "=": result = left == right ? true : false; break;
			default:
				System.err.println("Warning! Error at evaluation : unknown operator '"
					+ tToken + "'. False was returned by default.");
				result = false;
		}

		return result;
	}

	
	protected AstNode rewriteOnlyJunctions() {
		AstNode newSubtree;

		switch (tToken) {
			case ">":
				newSubtree = RewriteSubtrees.materialCondition(
					tOperands[0], tOperands[1]);
				break;
			case "=":
				newSubtree = RewriteSubtrees.equivalence(
					tOperands[0], tOperands[1]);
				break;
			case "^":
				newSubtree = RewriteSubtrees.exclusiveDisjunction(
					tOperands[0], tOperands[1]);
				break;
			default:
				tOperands[0] = tOperands[0].rewriteOnlyJunctions();
				tOperands[1] = tOperands[1].rewriteOnlyJunctions();
				return this;
		}

		return newSubtree.rewriteOnlyJunctions();
	}


	protected AstNode rewriteNegations() {
		tOperands[0] = tOperands[0].rewriteNegations();
		tOperands[1] = tOperands[1].rewriteNegations();
		return this;
	}
}