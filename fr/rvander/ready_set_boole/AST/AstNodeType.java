package fr.rvander.ready_set_boole.AST;


public enum AstNodeType {
	BINARY_OP (2, "&|^>=") {
		protected AstNode createNode(char token) {
			return new BinaryOperatorNode(token);
		}
	},
	UNARY_OP (1, "!") {
		protected AstNode createNode(char token) {
			return new UnaryOperatorNode(token);
		}
	},
	PRIMITIVE (0, "01") {
		protected AstNode createNode(char token) {
			return new PrimitiveNode(token == '1' ? true : false);
		}
	},
	VARIABLE (0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
		protected AstNode createNode(char token) {
			return new VariableNode(Character.toString(token));
		}
	};

	private final int tNbOperands;
	private final String tTokens;


	private AstNodeType(int nbOperands, String tokens) {
		tNbOperands = nbOperands;
		tTokens = tokens;
	}


	protected int nbOperands() {
		return tNbOperands;
	}


	protected boolean containsToken(char token) {
		if (tTokens.indexOf((int)token) == -1) {
			return false;
		}
		return true;
	}


	protected abstract AstNode createNode(char token);
}