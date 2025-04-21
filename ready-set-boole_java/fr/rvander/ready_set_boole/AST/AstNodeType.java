package fr.rvander.ready_set_boole.AST;


public enum AstNodeType {
	BINARY_OP (2),
	UNARY_OP (1),
	PRIMITIVE (0);
	// VARIABLE (0);

	private final int nbOperands;


	private AstNodeType(int nbOperands) {
		this.nbOperands = nbOperands;
	}


	protected int nbOperands() {
		return this.nbOperands;
	}
}