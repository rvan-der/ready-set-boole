package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public abstract class AstNode {

	private AstNode parent = null;
	protected AstNode[] operands = null;
	protected String mathSymbol;
	protected String programSymbol;
	protected AstNodeType type;


	protected AstNode (AstNodeType type) {
		this.type = type;
	}


	protected abstract boolean evaluate() throws AstException;


	protected void setOperands(AstNode[] operands) throws AstException {

		if (this.type.nbOperands() == 0) {
			throw new AstException(
				"Trying to pass operands to a node of type " + this.type
				+ " which has no operands.");
		}

		if (operands.length != this.type.nbOperands()) {
			throw new AstException(
				"Wrong number of operands passed to AstNode of type "
				+ this.type +": " + operands.length);
		}

		this.operands = operands;
		for (AstNode n : this.operands) {
			n.setParent(this);
		}
	}


	protected void setParent(AstNode parent) {
		this.parent = parent;
	}


	protected AstNode getParent() {
		return this.parent;
	}


	protected void visualize(int depth, String branches) {
		if (depth > 0) {
			for (int i = 0; i < depth - 1; i += 1) {
				System.out.print(branches.substring(i, i + 1) + "  ");
			}
			System.out.print(branches.charAt(depth - 1) == '│' ? "├─ " : "└─ ");
		}
		System.out.println(this.mathSymbol);
		for (int i = this.type.nbOperands() - 1; i >= 0; i -= 1) {
			this.operands[i].visualize(depth + 1, branches + (i > 0 ? "│" : " "));
		}
	}
}