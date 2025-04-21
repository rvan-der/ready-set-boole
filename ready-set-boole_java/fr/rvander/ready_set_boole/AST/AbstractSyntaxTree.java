package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class AbstractSyntaxTree {
	private AstNode root;


	protected AbstractSyntaxTree(AstNode root) {
		this.root = root;
	}


	public boolean evaluate() throws AstException {
		return this.root.evaluate();
	}


	public void visualize() {
		this.root.visualize(0, "");
	}
}