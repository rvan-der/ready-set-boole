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

	
	@Override
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
			return super.rewriteOnlyJunctions();
		}

		return newSubtree.rewriteOnlyJunctions();
	}


	@Override
	protected AstNode distributeJunctions(String operator) {
		if (!tToken.equals(operator)) {
			return super.distributeJunctions(operator);
		}

		String opposite = operator.equals("|") ? "&" : "|";
		AstNode left = tOperands[0];
		AstNode right = tOperands[1];
		AstNode newSubtree;

		// two binary values combined: 0 means != opposite ; 1 means == opposite
		// four combinations possible: 0->00 ; 1->01 ; 2->10 ; 3->11
		int combination = (left.tToken.equals(opposite) ? 2 : 0)
							+ (right.tToken.equals(opposite) ? 1 : 0);
		switch (combination) {
		case 0:
			tOperands[0] = tOperands[0].distributeJunctions(operator);
			tOperands[1] = tOperands[1].distributeJunctions(operator);
			if (tOperands[0].tToken.equals(opposite)
				|| tOperands[1].tToken.equals(opposite)) {
				newSubtree = this;
			} else {
				return this;
			}
			break;
		case 1:
			newSubtree = RewriteSubtrees
						.distribution(left,
						right.tOperands[0], right.tOperands[1],
						operator);
			break;
		case 2:
			newSubtree = RewriteSubtrees
						.distribution(right,
						left.tOperands[0], left.tOperands[1],
						operator);
			break;
		case 3:
			newSubtree = RewriteSubtrees
						.doubleDistribution(left.tOperands[0], left.tOperands[1],
										right.tOperands[0], right.tOperands[1],
										operator);
			break;
		default:
			return this;
		}

		return newSubtree.distributeJunctions(operator);
	}


	@Override
	protected AstNode simplify() {
		tOperands[0] = tOperands[0].simplify();
		tOperands[1] = tOperands[1].simplify();
		AstNode left = tOperands[0];
		AstNode right = tOperands[1];

		// must be a junction and
		// at least one of left or right must be a litteral (primitive or variable)
		if (!"&|".contains(tToken) || !left.isLitteral() && !right.isLitteral()) {
			return this;
		}

		// two binary values combined: 0 means primitive ; 1 means variable or expression
		// four combinations possible: 0->00 ; 1->01 ; 2->10 ; 3->11
		int combination = (left.isPrimitive() ? 0 : 2) + (right.isPrimitive() ? 0 : 1);
		
		switch (combination) {
		case 0:
			return new PrimitiveNode(evaluate(null) ? "1" : "0");
		case 1:
		case 2:
			boolean primitiveVal = (combination == 1 ? left : right).evaluate(null);
			AstNode expression = combination == 1 ? right : left;
			if (tToken.equals("&")) {
				return primitiveVal == true ? expression : new PrimitiveNode("0");
			}
			return primitiveVal == false ? expression : new PrimitiveNode("1");
		case 3:
			if (!left.isVariable() || !right.isVariable()
				|| !left.getVariableName().equals(right.getVariableName())) {
				return this;
			}
			int nbOfNegations = (left.tToken.equals("!") ? 1 : 0)
								+ (right.tToken.equals("!") ? 1 : 0);
			if (nbOfNegations != 1) {
				return left;
			}
			return tToken.equals("&") ? new PrimitiveNode("0") : new PrimitiveNode("1");
		default:
			break;
		}
		return this;
	}


	@Override
	protected AstNode alignJunctions() {
		AstNode newSubtree;
		int combination = (tOperands[0].tToken.equals(tToken) ? 2 : 0)
						+ (tOperands[1].tToken.equals(tToken) ? 1 : 0);
		switch (combination) {
		case 2:
			newSubtree = RewriteSubtrees.swapBranches(this);
			break;
		case 3:
			newSubtree = RewriteSubtrees.alignDoubleJunction(this);
			break;
		default:
			return super.alignJunctions();
		}
		return newSubtree.alignJunctions();
	}
}