package fr.rvander.ready_set_boole.AST;

import java.util.HashSet;
import java.util.HashMap;
import java.io.Serializable;


public abstract class AstNode implements Serializable {

	private static final long serialVersionUID = -7509745474094255803L;
	protected AstNode[] tOperands;
	protected String tToken;
	protected String tMathSymbol;
	protected AstNodeType tType;


	protected AstNode (AstNodeType type, String token) {
		tType = type;
		tToken = token;
		tOperands = null;
	}


	protected abstract boolean evaluate(HashMap<String, Boolean> hypothesis);


	protected AstNode rewriteOnlyJunctions() {
		for (int i = 0; i < tType.nbOperands(); i++) {
			tOperands[i] = tOperands[i].rewriteOnlyJunctions();
		}
		return this;
	}


	protected AstNode rewriteNegations() {
		for (int i = 0; i < tType.nbOperands(); i++) {
			tOperands[i] = tOperands[i].rewriteNegations();
		}
		return this;
	}


	protected AstNode distributeJunctions(String operator) {
		for (int i = 0; i < tType.nbOperands(); i++) {
			tOperands[i] = tOperands[i].distributeJunctions(operator);
		}
		return this;
	}


	protected AstNode simplify() {
		for (int i = 0; i < tType.nbOperands(); i++) {
			tOperands[i] = tOperands[i].simplify();
		}
		return this;
	}


	protected AstNode alignJunctions() {
		for (int i = 0; i < tType.nbOperands(); i++) {
			tOperands[i] = tOperands[i].alignJunctions();
		}
		return this;
	}


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		if (tType.nbOperands() > 0) {
			for (AstNode operand : tOperands) {
				varsSet = operand.getVariables(varsSet);
			}
		}
		if (tType == AstNodeType.VARIABLE) {
			varsSet.add(tToken);
		}
		return varsSet;
	}


	protected void setOperands(AstNode[] operands) {
		tOperands = operands;
	}
	

	protected String getFormula() {
		String formula = "";
		for (int i = 0; i < tType.nbOperands(); i += 1) {
			formula += tOperands[i].getFormula();
		}
		return formula + tToken;
	}


	protected boolean isLitteral() {
		if (tType == AstNodeType.PRIMITIVE || tType == AstNodeType.VARIABLE) {
			return true;
		}
		if (tType == AstNodeType.UNARY_OP && tToken.equals("!")) {
			return tOperands[0].isLitteral();
		}
		return false;
	}


	protected boolean isVariable() {
		if (tType == AstNodeType.VARIABLE) {
			return true;
		}
		if (tType == AstNodeType.UNARY_OP && tToken.equals("!")) {
			return tOperands[0].isVariable();
		}
		return false;
	}


	protected String getVariableName() {
		if (tType == AstNodeType.VARIABLE) {
			return tToken;
		}
		if (tType == AstNodeType.UNARY_OP && tToken.equals("!")) {
			return tOperands[0].getVariableName();
		}
		return null;
	}


	protected boolean isPrimitive() {
		if (tType == AstNodeType.PRIMITIVE) {
			return true;
		}
		if (tType == AstNodeType.UNARY_OP && tToken.equals("!")) {
			return tOperands[0].isPrimitive();
		}
		return false;
	}


	protected void visualize(int depth, String branches, boolean unary) {
		if (depth > 0 && !unary) {
			for (int i = 0; i < depth - 1; i += 1) {
				System.out.print(branches.substring(i, i + 1) + "  ");
			}
			System.out.print(branches.charAt(depth - 1) == '│' ? "├─╴" : "└─╴");
		}
		if (depth > 0 && unary) {
			System.out.print("╶╴");
		}
		System.out.print(tMathSymbol);
		if (tType.nbOperands() != 1) {
			System.out.print("\n");
		}
		for (int i = tType.nbOperands() - 1; i >= 0; i -= 1) {
			tOperands[i].visualize(depth + 1,
				branches + (i > 0 ? "│" : " "),
				tType.nbOperands() == 1 ? true : false);
		}
	}
	

	protected AstNode copySubtree() {
		AstNode copy = tType.createNode(tToken);
		if (tType.nbOperands() > 0) {
			AstNode[] operandsCopy = new AstNode[tType.nbOperands()];
			for (int i = 0; i < tType.nbOperands(); i += 1) {
				operandsCopy[i] = tOperands[i].copySubtree();
				copy.setOperands(operandsCopy);
			}
		}
		return copy;
	}
}