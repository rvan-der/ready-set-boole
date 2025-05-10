package fr.rvander.ready_set_boole.AST;


public enum AstNodeType {
	BINARY_OP (2, "&|^>=") {
		protected AstNode createNode(String token) {
			return new BinaryOperatorNode(token);
		}
	},
	UNARY_OP (1, "!") {
		protected AstNode createNode(String token) {
			return new UnaryOperatorNode(token);
		}
	},
	PRIMITIVE (0, "01") {
		protected AstNode createNode(String token) {
			return new PrimitiveNode(token);
		}
	},
	VARIABLE (0, "ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
		protected AstNode createNode(String token) {
			return new VariableNode(token);
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


	protected boolean containsToken(String token) {
		return tTokens.contains(token);
	}


	protected abstract AstNode createNode(String token);
}