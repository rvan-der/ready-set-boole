package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class RewriteSubtrees {

	// A > B --> !A | B
	protected static AstNode materialCondition(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode('|');
		AstNode[] rootOperands = new AstNode[2];
		AstNode negation = new UnaryOperatorNode('!');
		AstNode[] negationOperands = new AstNode[1];
		rootOperands[0] = negation;
		rootOperands[1] = b;
		negationOperands[0] = a;
		root.setOperands(rootOperands);
		negation.setOperands(negationOperands);
		return root;
	}


	// A = B --> (!A | B) & (!B | A)
	protected static AstNode equivalence(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode('&');
		AstNode[] rootOperands = new AstNode[2];
		AstNode left = RewriteSubtrees.materialCondition(a, b);
		AstNode right = RewriteSubtrees.materialCondition(b.copySubtree(), a.copySubtree());
		rootOperands[0] = left;
		rootOperands[1] = right;
		root.setOperands(rootOperands);
		return root;
	}

	// !(A | B) --> !A & !B
	// !(A & B) --> !A | !B
	protected static AstNode deMorgansLaws(AstNode a, AstNode b, char operator) {
		AstNode root = new BinaryOperatorNode(operator == '|' ? '&' : '|');
		AstNode left = new UnaryOperatorNode('!');
		AstNode right = new UnaryOperatorNode('!');
		AstNode[] rootOperands = new AstNode[2];
		AstNode[] leftOperands = new AstNode[1];
		AstNode[] rightOperands = new AstNode[1];
		rootOperands[0] = left;
		rootOperands[1] = right;
		leftOperands[0] = a;
		rightOperands[0] = b;
		root.setOperands(rootOperands);
		left.setOperands(leftOperands);
		right.setOperands(rightOperands);
		return root;
	}

	// A | (B & C) --> (A | B) & (A | C)
	protected static AstNode distribution(AstNode a, AstNode b, AstNode c, char operator) {
		AstNode root = new BinaryOperatorNode(operator == '|' ? '&' : '|');
		AstNode left = new BinaryOperatorNode(operator);
		AstNode right = new BinaryOperatorNode(operator);
		AstNode[] rootOperands = new AstNode[2];
		AstNode[] leftOperands = new AstNode[2];
		AstNode[] rightOperands = new AstNode[2];
		rootOperands[0] = left;
		rootOperands[1] = right;
		leftOperands[0] = a;
		leftOperands[1] = b;
		rightOperands[0] = a.copySubtree();
		rightOperands[1] = c;
		root.setOperands(rootOperands);
		left.setOperands(leftOperands);
		right.setOperands(rightOperands);
		return root;
	}


	// A ^ B --> (!A & B) | (A & !B)
	protected static AstNode exclusiveDisjunction(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode('|');
		AstNode left = new BinaryOperatorNode('&');
		AstNode right = new BinaryOperatorNode('&');
		AstNode aNot = new UnaryOperatorNode('!');
		AstNode bNot = new UnaryOperatorNode('!');
		AstNode[] rootOperands = new AstNode[2];
		AstNode[] leftOperands = new AstNode[2];
		AstNode[] rightOperands = new AstNode[2];
		AstNode[] aNotOperands = new AstNode[1];
		AstNode[] bNotOperands = new AstNode[1];
		rootOperands[0] = left;
		rootOperands[1] = right;
		leftOperands[0] = aNot;
		leftOperands[1] = b;
		rightOperands[0] = a.copySubtree();
		rightOperands[1] = bNot;
		aNotOperands[0] = a;
		bNotOperands[0] = b.copySubtree();
		root.setOperands(rootOperands);
		left.setOperands(leftOperands);
		right.setOperands(rightOperands);
		aNot.setOperands(aNotOperands);
		bNot.setOperands(bNotOperands);
		return root;
	}


	// !(A ^ B) --> (!A | B) & (A | !B)
	protected static AstNode notExclusiveDisjunction(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode('&');
		AstNode left = new BinaryOperatorNode('|');
		AstNode right = new BinaryOperatorNode('|');
		AstNode aNot = new UnaryOperatorNode('!');
		AstNode bNot = new UnaryOperatorNode('!');
		AstNode[] rootOperands = new AstNode[2];
		AstNode[] leftOperands = new AstNode[2];
		AstNode[] rightOperands = new AstNode[2];
		AstNode[] aNotOperands = new AstNode[1];
		AstNode[] bNotOperands = new AstNode[1];
		rootOperands[0] = left;
		rootOperands[1] = right;
		leftOperands[0] = aNot;
		leftOperands[1] = b;
		rightOperands[0] = a.copySubtree();
		rightOperands[1] = bNot;
		aNotOperands[0] = a;
		bNotOperands[0] = b.copySubtree();
		root.setOperands(rootOperands);
		left.setOperands(leftOperands);
		right.setOperands(rightOperands);
		aNot.setOperands(aNotOperands);
		bNot.setOperands(bNotOperands);
		return root;
	}
}