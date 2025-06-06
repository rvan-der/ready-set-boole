package fr.rvander.ready_set_boole.AST;

import java.util.HashMap;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;


public class SatCalculator extends RecursiveAction {

	private static final long serialVersionUID = -8985174449365364102L;
	private AbstractSyntaxTree tTree;
	private int tStart;
	private int tLength;
	private byte[] tTable;
	private AtomicBoolean tResult;
	private final int tThreshold = 1 << 17;


	public SatCalculator(AbstractSyntaxTree tree, int start, int length, byte[] table, AtomicBoolean result) {
		tTree = tree;
		tStart = start;
		tLength = length;
		tTable = table;
		tResult = result;
	}


	protected void computeDirectly() {
		HashMap<String, Boolean> hypothesis = new HashMap<>();
		int nbVars = tTree.getNbVars();
		String[] variables = tTree.getVariables();
		
		if (tTable == null) {
			for (int interpretation = tStart; interpretation < tStart + tLength; interpretation++) {
				for (int i = 0; i < nbVars; i++) {
					int value = (interpretation >>> (nbVars - i - 1)) & 1;
					hypothesis.put(variables[i],
								value == 1 ? Boolean.valueOf(true) : Boolean.valueOf(false));
				}
				if (tTree.evaluate(hypothesis) == true) {
					tResult.set(true);
					return;
				}
			}
		}
		else {
			for (int interpretation = tStart; interpretation < tStart + tLength; interpretation++) {
				if (tTable[interpretation] == 1) {
					tResult.set(true);
					return;
				}
			}
		}
	}


	@Override
	protected void compute() {
		if (tResult.get() == true) {
			return ;
		}
		if (tLength < tThreshold) {
			computeDirectly();
			return;
		}
		int split = tLength / 2;
		invokeAll(new SatCalculator(tTree, tStart, split, tTable, tResult),
				new SatCalculator(tTree, tStart + split, tLength - split, tTable, tResult));
	}
}