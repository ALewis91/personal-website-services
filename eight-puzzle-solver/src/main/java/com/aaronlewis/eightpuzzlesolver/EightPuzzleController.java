package com.aaronlewis.eightpuzzlesolver;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
public class EightPuzzleController {
	
	private EightPuzzleSolver solver;
	
	@GetMapping("/eight-puzzle-solver/{heuristic}/{initialPuzzleState}")
	public EightPuzzleSolution getSolution(
			@PathVariable String heuristic,
			@PathVariable String initialPuzzleState) {
		
		EightPuzzleSolution solution = new EightPuzzleSolution();
		
		if (!EightPuzzleSolver.isSolvable(initialPuzzleState)) {
			solution.setInitialState("Not solvable");
			return solution;
		} else if (!EightPuzzleSolver.isValid(initialPuzzleState)) {
			solution.setInitialState("Not valid");
			return solution;
		}
		
		solver = new EightPuzzleSolver(initialPuzzleState);
		
		//Solve with misplaced tiles heuristic
		if (heuristic.compareTo("MPT") == 0) {
			solver.solveMisplacedTiles();
		} 
		//Solve using Manhattan distance heuristic
		else if (heuristic.compareTo("MHD") == 0) {
			solver.solveManhattanDistance();
		}
		
		solution.setDuration(solver.getDuration());
		solution.setInitialState(solver.getEightPuzzleConfig());
		solution.setNumStepsToSolve(solver.getNumStepsToSolve());
		solution.setSolution(solver.getSolution());
		
		return solution;
	}
	
	@GetMapping("/eight-puzzle-solver/random") 
	public String getRandomPuzzle() {
		return EightPuzzleSolver.newPuzzle();
	}

}
