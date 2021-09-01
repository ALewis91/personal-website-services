package com.aaronlewis.eightpuzzlesolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.aaronlewis.eightpuzzlesolver.astar.AstarSearchTree;

public class EightPuzzleSolver {
	
	private String eightPuzzleConfig;
	private List<String> solution;
	private Long duration;
	
	EightPuzzleSolver(String initialState) {
		eightPuzzleConfig = initialState;
		duration = 0L;
		solution = new LinkedList<>();
	}

	// Check to ensure user input initial puzzle state is valid
	public static boolean isValid(String state)
	{
		// Check to make sure string length is 9 and contains unique digits 0-8
		int[] digitSet = new int[9];
		
		if (state.length() != 9)
			return false;
		else
		{
			for (int x = 0; x < state.length(); x++)
			{
				if (digitSet[Integer.parseInt(state.charAt(x) + "")] == 1)
					return false;
				else 
					digitSet[Integer.parseInt(state.charAt(x) + "")] = 1;
			}
			return true;
		}
	}
	
	// Get number of inversions in the current puzzle state using ascii codes
	public static boolean isSolvable(String state)
	{
		int inversions = 0;
		int currentNumber;
		int nextNumber;
		// Get number of inversions for each number in puzzle
		for (int x = 0; x < state.length(); x++)
		{
			currentNumber = state.charAt(x);
			
			// If current number is not ascii code for 0
			if (currentNumber != 48)
			{
				// Iterate over all following numbers to check for inversion
				for (int y = x + 1; y < state.length(); y++)
				{
					nextNumber = state.charAt(y);
					
					// If the following number is less than the current and not 0, inversions++
					if (currentNumber < nextNumber && nextNumber > 48)
						inversions++;
				}
			}
		}
		
		// If number of inversions is even, return true
		return inversions%2 == 0;
	}
	
	// Generate a new 8-puzzle configuration until valid and solvable
	public static String newPuzzle()
	{
		LinkedList<Character> puzzle = new LinkedList<>();
		for (int x = 0; x < 9; x++)
			puzzle.add((char)(48 + x));
		Collections.shuffle(puzzle);
		String puzzleStr = "";
		for (int x = 0; x < puzzle.size(); x++)
			puzzleStr += puzzle.get(x);
		
		if (!isValid(puzzleStr) ||  !isSolvable(puzzleStr)) {
			puzzleStr = newPuzzle();
		}
		return puzzleStr;
	}
	
	// Solve puzzle using misplaced tiles heuristic
	public void solveMisplacedTiles() {
		
		// Create a search tree for the puzzle using Misplaced Tiles heuristic
		AstarSearchTree puzzleTreeMPT = new AstarSearchTree(eightPuzzleConfig, 0);
	
		// Measure time to solve puzzle using misplaced tiles heuristic
		long start = System.nanoTime();
		puzzleTreeMPT.solve();
		long stop = System.nanoTime();
		
		//Save duration
		duration = stop - start;
		
		// Obtain the optimal path to the solution from the search tree
		solution = puzzleTreeMPT.getRootPath();
	}
	
	// Solve puzzle using Manhattan distance heuristic
	public void solveManhattanDistance() {
		
		// Create a search tree for the puzzle using Manhattan distance heuristic
		AstarSearchTree puzzleTreeMD = new AstarSearchTree(eightPuzzleConfig, 1);
	
		// Measure time to solve puzzle using misplaced tiles heuristic
		long start = System.nanoTime();
		puzzleTreeMD.solve();
		long stop = System.nanoTime();
		
		//Save duration
		duration = stop - start;
		
		// Obtain the optimal path to the solution from the search tree
		solution = puzzleTreeMD.getRootPath();
		
	}

	public String getEightPuzzleConfig() {
		return eightPuzzleConfig;
	}

	public Integer getNumStepsToSolve() {
		return solution.size() - 1;
	}

	public List<String> getSolution() {
		return solution;
	}

	public Long getDuration() {
		return duration;
	}

}
