package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class NnfSubtrees {

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


	protected static AstNode equivalence(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode('&');
		AstNode[] rootOperands = new AstNode[2];
		AstNode left = NnfSubtrees.materialCondition(a, b);
		AstNode right = NnfSubtrees.materialCondition(b.copySubtree(), a.copySubtree());
		rootOperands[0] = left;
		rootOperands[1] = right;
		root.setOperands(rootOperands);
		return root;
	}


	protected static AstNode deMorgansLaws(AstNode a, AstNode b, char operator) {
		AstNode root = operator == '|' ?
			new BinaryOperatorNode('&') : new BinaryOperatorNode('|');
		AstNode[] rootOperands = new AstNode[2];
		AstNode left = new UnaryOperatorNode('!');
		AstNode[] leftOperands = new AstNode[1];
		AstNode right = new UnaryOperatorNode('!');
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
}