package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class RewriteSubtrees {

	// A > B --> !A | B
	protected static AstNode materialCondition(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode("|");
		AstNode[] rootOperands = new AstNode[2];
		AstNode negation = new UnaryOperatorNode("!");
		AstNode[] negationOperands = new AstNode[1];
		rootOperands[0] = negation;
		rootOperands[1] = b;
		negationOperands[0] = a;
		root.setOperands(rootOperands);
		negation.setOperands(negationOperands);
		return root;
	}


	// !(A ^ B)
	//   A = B --> (!A | B) & (!B | A)
	protected static AstNode equivalence(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode("&");
		AstNode[] rootOperands = new AstNode[2];
		AstNode left = RewriteSubtrees.materialCondition(a, b);
		AstNode right = RewriteSubtrees.materialCondition(b.copySubtree(), a.copySubtree());
		rootOperands[0] = left;
		rootOperands[1] = right;
		root.setOperands(rootOperands);
		return root;
	}


	// A ^ B --> (!A & B) | (A & !B)
	protected static AstNode exclusiveDisjunction(AstNode a, AstNode b) {
		AstNode root = new BinaryOperatorNode("|");
		AstNode left = new BinaryOperatorNode("&");
		AstNode right = new BinaryOperatorNode("&");
		AstNode aNot = new UnaryOperatorNode("!");
		AstNode bNot = new UnaryOperatorNode("!");
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


	// !(A | B) --> !A & !B
	// !(A & B) --> !A | !B
	protected static AstNode deMorgansLaws(AstNode a, AstNode b, String operator) {
		AstNode root = new BinaryOperatorNode(operator.equals("|") ? "&" : "|");
		AstNode left = new UnaryOperatorNode("!");
		AstNode right = new UnaryOperatorNode("!");
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
	// A & (B | C) --> (A & B) | (A & C)
	protected static AstNode distribution(AstNode a, AstNode b, AstNode c, String operator) {
		AstNode root = new BinaryOperatorNode(operator.equals("|") ? "&" : "|");
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


	// (A & B) | (C & D) --> (A | C) & (B | C) & (A | D) & (B | D)
	// (A | B) & (C | D) --> (A & C) | (B & C) | (A & D) | (B & D)
	//                          ↑    ↑    ↑    ↑    ↑    ↑    ↑
	//                          j1   r1   j2   r2   j3   r3   j4
	protected static AstNode doubleDistribution(AstNode a, AstNode b, AstNode c, AstNode d, String operator) {
		AstNode root1 = new BinaryOperatorNode(operator.equals("|") ? "&" : "|");
		AstNode root2 = new BinaryOperatorNode(operator.equals("|") ? "&" : "|");
		AstNode root3 = new BinaryOperatorNode(operator.equals("|") ? "&" : "|");
		AstNode junction1 = new BinaryOperatorNode(operator);
		AstNode junction2 = new BinaryOperatorNode(operator);
		AstNode junction3 = new BinaryOperatorNode(operator);
		AstNode junction4 = new BinaryOperatorNode(operator);
		AstNode[] root1operands = new AstNode[2];
		AstNode[] root2operands = new AstNode[2];
		AstNode[] root3operands = new AstNode[2];
		AstNode[] junction1operands = new AstNode[2];
		AstNode[] junction2operands = new AstNode[2];
		AstNode[] junction3operands = new AstNode[2];
		AstNode[] junction4operands = new AstNode[2];
		root1operands[0] = junction1;
		root1operands[1] = root2;
		root2operands[0] = junction2;
		root2operands[1] = root3;
		root3operands[0] = junction3;
		root3operands[1] = junction4;
		junction1operands[0] = a;
		junction1operands[1] = c;
		junction2operands[0] = b;
		junction2operands[1] = c.copySubtree();
		junction3operands[0] = a.copySubtree();
		junction3operands[1] = d;
		junction4operands[0] = b.copySubtree();
		junction4operands[1] = d.copySubtree();
		root1.setOperands(root1operands);
		root2.setOperands(root2operands);
		root3.setOperands(root3operands);
		junction1.setOperands(junction1operands);
		junction2.setOperands(junction2operands);
		junction3.setOperands(junction3operands);
		junction4.setOperands(junction4operands);
		return root1;
	}


	//         & <-node       & <-root
	//        / \            / \
	//root-> &   &          A   & <-node
	//      / \ / \   -->      / \
	//     A  B C  D          B   &
	//                           / \
	//                          C   D
	protected static AstNode alignDoubleJunction(AstNode node) {
		AstNode root = node.tOperands[0];
		node.tOperands[0] = root.tOperands[1];
		root.tOperands[1] = node;
		return root;
	}


	//    &           &
	//   / \   -->   / \
	//  &   A       A   &
	protected static AstNode swapBranches(AstNode node) {
		AstNode swap = node.tOperands[0];
		node.tOperands[0] = node.tOperands[1];
		node.tOperands[1] = swap;
		return node;
	}
}