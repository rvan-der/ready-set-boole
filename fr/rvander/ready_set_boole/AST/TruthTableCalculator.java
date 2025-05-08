package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashMap;
import java.util.concurrent.RecursiveAction;


public class TruthTableCalculator extends RecursiveAction {

	private static final long serialVersionUID = -8985174449365364102L;
    private AbstractSyntaxTree tTree;
    private int tStart;
    private int tLength;
    private byte[] tTable;
    private final int tThreshold = 1 << 17;


    public TruthTableCalculator(AbstractSyntaxTree tree, int start, int length, byte[] table) {
        tTree = tree;
        tStart = start;
        tLength = length;
        tTable = table;
    }


    protected void computeDirectly() {
		HashMap<String, Boolean> hypothesis = new HashMap<>();
		int nbVars = tTree.getNbVars();
		String[] variables = tTree.getVariables();
		
		for (int values = tStart; values < tStart + tLength; values += 1) {
			for (int i = 0; i < nbVars; i += 1) {
				int val = (values >>> (nbVars - i - 1)) & 1;
				hypothesis.put(variables[i],
							val == 1 ? Boolean.valueOf(true) : Boolean.valueOf(false));
			}
			tTable[values] = (byte)(tTree.evaluate(hypothesis) == true ? 1 : 0);
		}
    }


    @Override
    protected void compute() {
        if (tLength < tThreshold) {
            computeDirectly();
            return;
        }
        int split = tLength / 2;
        invokeAll(new TruthTableCalculator(tTree, tStart, split, tTable),
                new TruthTableCalculator(tTree, tStart + split, tLength - split, tTable));
    }
}