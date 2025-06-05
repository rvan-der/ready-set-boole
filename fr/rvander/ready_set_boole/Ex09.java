package fr.rvander.ready_set_boole;

import java.util.ArrayDeque;
import java.util.Arrays;

import fr.rvander.ready_set_boole.sets.SetOperations;


public class Ex09 {

	public static int[] eval_set(String formula, int[][] sets) {
		int[] globalSet = SetOperations.combine(sets);
		Arrays.sort(globalSet);
	}
}