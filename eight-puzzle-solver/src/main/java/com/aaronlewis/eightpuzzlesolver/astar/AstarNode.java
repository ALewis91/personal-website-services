package com.aaronlewis.eightpuzzlesolver.astar;

public class AstarNode 
{
	// Declare member data variables
	private int pathCost;
	private int misplacedTileCost;
	private int manhattanCost;
	private int depth;
	private String state;
	private AstarNode parent;
	
	// Declare member functions
	
	// Constructor - Initialize variables
	public AstarNode()
	{
		pathCost = 0;
		misplacedTileCost = 0;
		manhattanCost = 0;
		depth = 0;
		state = "";
		parent = null;
	}
	
	// Setters
	
	// Set pathCost
	public void setPathCost(int x)
	{
		pathCost = x;
	}
	
	// Set misplacedTileCost
	public void setMisplacedTileCost(int x)
	{
		misplacedTileCost = x;
		
	}
	
	// Set manhattanCost
	public void setManhattanCost(int x)
	{
		manhattanCost = x;
	}
	
	// Set depth
	public void setDepth(int x)
	{
		depth = x;
	}
	
	// Set state
	public void setState(String s)
	{
		state = s;
	}
	
	// Set parent node
	public void setParent(AstarNode p)
	{
		parent = p;
	}
	
	
	// Getters
	
	// Get pathCost
	public int getPathCost()
	{
		return pathCost;
	}
	
	// Get misplacedTileCost
	public int getMisplacedTileCost()
	{
		return misplacedTileCost;
	}
	
	// Get manhattanCost
	public int getManhattanCost()
	{
		return manhattanCost;
	}
	
	// Get depth
	public int getDepth()
	{
		return depth;
	}
	
	// Get state
	public String getState()
	{
		return state;
	}
	
	// Get parent node
	public AstarNode getParent()
	{
		return parent;
	}
	
	// Get evaluation function result using misplaced tiles heuristic
	public int getFMPT()
	{
		return pathCost + misplacedTileCost;
	}
	
	// Get evaluation function result using Manhattan distance heuristic
	public int getFMD()
	{
		return pathCost + manhattanCost;
	}
	
}
