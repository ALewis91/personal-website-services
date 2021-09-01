package com.aaronlewis.eightpuzzlesolver.astar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AstarSearchTree 
{
	// Nested Comparator for Misplaced Tile cost nodes
	class AstarNodeFMPTComparator implements Comparator<AstarNode>
	{
		public int compare(AstarNode n1, AstarNode n2) 
		{
			if (n1.getFMPT() > n2.getFMPT())
				return 1;
			else if (n1.getFMPT() < n2.getFMPT())
				return -1;
			else
				return 0;
		}
		
	}
	
	// Nested Comparator for Misplaced Tile cost nodes
	class AstarNodeFMDComparator implements Comparator<AstarNode>
	{
		public int compare(AstarNode n1, AstarNode n2) 
		{
			if (n1.getFMD() > n2.getFMD())
				return 1;
			else if (n1.getFMD() < n2.getFMD())
				return -1;
			else
				return 0;
		}
		
	}
	
	
	
	// Declare member data variables
	private AstarNode currentNode;
	private int size;
	private int heuristic;
	private String goalState = "123456780";
	private boolean solutionFound;
	private PriorityQueue<AstarNode> frontier;
	private HashMap<String, AstarNode> explored;
	// Declare member functions
	
	// Constructor - create a new tree from a given puzzle state and 
	//      a chosen heuristic (0 - misplaced tiles, 1 - manhattan distance)
	public AstarSearchTree(String state, int heuristic)
	{
		// Assign variables
		currentNode = new AstarNode();
		currentNode.setState(state);
		currentNode.setParent(null);
		currentNode.setPathCost(0);
		currentNode.setDepth(0);
		size = 1;
		solutionFound = false;
		this.heuristic = heuristic;
		
		// If Misplaced Tiles heuristic set root MPT cost and frontier to use FMPT comparator
		if (heuristic == 0)
		{
			currentNode.setMisplacedTileCost(misplacedTiles(state));
			frontier = new PriorityQueue<>(10, new AstarNodeFMPTComparator());
		}
		// Else set root MD cost and frontier to use FMD comparator
		else
		{
			currentNode.setManhattanCost(manhattanDist(state));
			frontier = new PriorityQueue<>(10, new AstarNodeFMDComparator());
		}
		
		// Add root node to frontier
		frontier.add(currentNode);
		
		// Assign new hashmap to explored map
		explored = new HashMap<String, AstarNode>();
	}
	
	// Return the state of the puzzle
	public String getState()
	{
		return currentNode.getState();
	}
	
	// Get the path back to the root from the current node
	public LinkedList<String> getRootPath()
	{
		// Create a new node to follow path
		AstarNode tempNode = currentNode;
		
		// Initialize the path to an empty list and add the current node state
		LinkedList<String> path = new LinkedList<String>();
		path.addFirst(tempNode.getState());
		
		// While tempNode is not the root
		while (tempNode.getParent() != null)
		{
			// Set tempNode to parent of tempNode
			tempNode = tempNode.getParent();
			
			// Add the state of tempNode to the path
			path.addFirst(tempNode.getState());
		}
		
		// Return the path from the current node to the root node
		return path;
	}
	
	// Remove next node from frontier, add it to explored set, and add it's children to frontier
	public void progress()
	{		
		// Grab the least-cost node from the frontier and add its state to explored list
		currentNode = frontier.poll();	

		explored.put(currentNode.getState(), currentNode);
		
		// Check to see if least-cost node is goal state
		if (currentNode.getState().compareTo(goalState) == 0)
		{
			solutionFound = true;
			return;
		}
		// Get the list of possible children for the state of the current node
		LinkedList<String> children = getPossibleMoves(currentNode.getState());
		
		// For each child in the list of children, create a new node and add to frontier
		// If using misplaced tiles heuristic
		if (heuristic == 0)
		{
			for (int x = 0; x < children.size(); x++)
			{
				// If we have not already visited this node, add it to the frontier
				if (explored.get(children.get(x))==null)
				{
					AstarNode child = new AstarNode();
					child.setDepth(currentNode.getDepth() + 1);
					child.setParent(currentNode);
					child.setPathCost(currentNode.getPathCost() + 1);
					child.setState(children.get(x));
					child.setMisplacedTileCost(misplacedTiles(child.getState()));
					frontier.add(child);
					size++;
				}
			}
		}
		// Else if using Manhattan distance heuristic
		else
		{
			for (int x = 0; x < children.size(); x++)
			{
				// If we have not already visited this node, add it to the frontier
				if (explored.get(children.get(x))==null)
				{
					AstarNode child = new AstarNode();
					child.setDepth(currentNode.getDepth() + 1);
					child.setParent(currentNode);
					child.setPathCost(currentNode.getPathCost() + 1);
					child.setState(children.get(x));
					child.setManhattanCost(manhattanDist(child.getState()));
					frontier.add(child);
					size++;
				}
			}
		}
	}
	
	// Return whether or not a solution has been found
	public boolean solutionFound()
	{
		return solutionFound;
	}
	
	// Progress through the nodes until a solution is found
	public void solve()
	{
		while (!solutionFound)
			this.progress();
	}
	
	// Calculate number of misplaced tiles in the current node
	private int misplacedTiles(String state)
	{
		int numMisplacedTiles = 0;
		int number;
		
		// Check each tile to see if it is misplaced
		for (int x = 0; x < state.length(); x++) 
		{
			// Get the number at tile x
			number = Integer.parseInt("" + state.charAt(x));
			
			// If not empty tile
			if (number != 0)
			{
				// If tile is not in correct position increment counter
				if (number != x)
					numMisplacedTiles++;
			}
		}
		
		// Return number of misplaced tiles
		return numMisplacedTiles;
	}
	
	// Calculate the Manhattan distance for the current node
	private int manhattanDist(String state)
	{
		// Initialize the distance to 0
		int distance = 0;
		int number;
		
		// Get the distance for each tile
		for (int x = 0; x < state.length(); x++) 
		{
			// Get the number at tile x
			number = Integer.parseInt("" + state.charAt(x));
			
			// If not empty tile
			if (number != 0)
			{
				// If not in correct row, then add distance from correct row
				distance += Math.abs(number/3 - x/3);
				
				// If not in correct column, then add distance from correct column
				distance += Math.abs(number%3 - x%3);
			}
		}
		// Return total distance
		return distance;
	}

	// Get list of possible moves (children nodes)
	private LinkedList<String> getPossibleMoves(String state)
	{
		// Get position of blank tile
		int blankPosition = state.indexOf("0");
		
		// Create a new linked list to hold the possible moves
		LinkedList<String> moves = new LinkedList<>();
		
		// Add potential moves to list based on blank tile position
		if (blankPosition == 0)
		{
			moves.add(swap(0, 1, state));
			moves.add(swap(0, 3, state));
		}
		else if (blankPosition == 1)
		{
			moves.add(swap(1, 0, state));
			moves.add(swap(1, 2, state));
			moves.add(swap(1, 4, state));
		}
		else if (blankPosition == 2)
		{
			moves.add(swap(2, 1, state));
			moves.add(swap(2, 5, state));
		}
		else if (blankPosition == 3)
		{
			moves.add(swap(3, 0, state));
			moves.add(swap(3, 4, state));
			moves.add(swap(3, 6, state));
		}
		else if (blankPosition == 4)
		{
			moves.add(swap(4, 1, state));
			moves.add(swap(4, 3, state));
			moves.add(swap(4, 5, state));
			moves.add(swap(4, 7, state));
		}
		else if (blankPosition == 5)
		{
			moves.add(swap(5, 2, state));
			moves.add(swap(5, 4, state));
			moves.add(swap(5, 8, state));
		}
		else if (blankPosition == 6)
		{
			moves.add(swap(6, 3, state));
			moves.add(swap(6, 7, state));
		}
		else if (blankPosition == 7)
		{
			moves.add(swap(7, 4, state));
			moves.add(swap(7, 6, state));
			moves.add(swap(7, 8, state));
		}
		else if (blankPosition == 8)
		{
			moves.add(swap(8, 5, state));
			moves.add(swap(8, 7, state));
		}
		
		return moves;
	}
	
	// Returns the number of nodes in the search tree
	public int size()
	{
		return size;
	}
	// Utility function to swap two characters in the state string
	private String swap(int index1, int index2, String str)
	{
		// Convert string to character array
		char[] c = str.toCharArray();

		// Replace with a "swap" function, if desired:
		char temp = c[index1];
		c[index1] = c[index2];
		c[index2] = temp;

		String swapped = new String(c);	
		return swapped;
	}
}
