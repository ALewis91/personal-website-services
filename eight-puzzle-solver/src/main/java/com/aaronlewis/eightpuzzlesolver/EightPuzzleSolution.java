package com.aaronlewis.eightpuzzlesolver;

import java.util.List;

public class EightPuzzleSolution {
	
	private String initialState;
	private Integer numStepsToSolve;
	private List<String> solution;
	private Long duration;
	
	public EightPuzzleSolution() {}
	
	public EightPuzzleSolution(String initialState, Integer numStepsToSolve, List<String> solution, Long duration) {
		super();
		this.initialState = initialState;
		this.numStepsToSolve = numStepsToSolve;
		this.solution = solution;
		this.duration = duration;
	}
	public String getInitialState() {
		return initialState;
	}
	public Integer getNumStepsToSolve() {
		return numStepsToSolve;
	}
	public List<String> getSolution() {
		return solution;
	}
	public Long getDuration() {
		return duration;
	}
	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}
	public void setNumStepsToSolve(Integer numStepsToSolve) {
		this.numStepsToSolve = numStepsToSolve;
	}
	public void setSolution(List<String> solution) {
		this.solution = solution;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
}
