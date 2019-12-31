/**
 * @author UCSD MOOC development team
 * @author Zhang Yongzhe
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;
import util.GraphLoader2;

/**
 * @author UCSD MOOC development team
 * @author Zhang Yong Zhe A class which represents a graph of geographic
 *         locations Nodes in the graph are intersections between Using the
 *         Adjacent List Graph structure
 *
 */
public class MapGraph {
	// TODO: Add your member variables here in WEEK 3
	private HashMap<GeographicPoint, MapNode> adjmap;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		// TODO: Implement in this constructor in WEEK 3
		adjmap = new HashMap<>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		// TODO: Implement this method in WEEK 3
		return adjmap.size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		// TODO: Implement this method in WEEK 3
		return adjmap.keySet();

		// creating new structure not only to match the required
		// return type, but also to encapsulate member variable
		// return new HashSet<GeographicPoint>(adjmap.keySet());
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		// TODO: Implement this method in WEEK 3
		int sum = 0;
		for (MapNode mapnode : adjmap.values()) {
			if (mapnode.getEdge() != null) {
				sum += mapnode.getEdge().size();
			}
		}
		return sum;
	}

	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location The location of the intersection
	 * @return true if a node was added, false if it was not (the node was already
	 *         in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		// TODO: Implement this method in WEEK 3
		if (location == null || (adjmap.containsKey(location))) {
			return false;
		} 
	    adjmap.put(location, new MapNode(location));
		return true;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from     The starting point of the edge
	 * @param to       The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length   The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been added as
	 *                                  nodes to the graph, if any of the arguments
	 *                                  is null, or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		// TODO: Implement this method in WEEK 3
		if (!adjmap.containsKey(from) && !adjmap.containsKey(to)) {
			throw new IllegalArgumentException("GeographicPoints are not added");
		} else if (from == null || to == null) {
			throw new IllegalArgumentException("GeographicPoints are null");
		}
		Edge edge = new Edge(from, to, roadName, roadType, length);
		MapNode startNode = adjmap.get(from);
		startNode.addNeighbors(edge);
	}

	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length,
			double speedlimit) throws IllegalArgumentException {

		// TODO: Implement this method in WEEK 3
		if (!adjmap.containsKey(from) && !adjmap.containsKey(to)) {
			throw new IllegalArgumentException("GeographicPoints are not added");
		} else if (from == null || to == null) {
			throw new IllegalArgumentException("GeographicPoints are null");
		}

		else {
			Edge edge = new Edge(from, to, roadName, roadType, length, speedlimit);
			MapNode startNode = adjmap.get(from);
			startNode.addNeighbors(edge);
		}
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */

	// Referring to the refactored dfs code
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3
		// Using queue
		// Initial check
		if (start == null || goal == null) {
			System.out.println("GeographicPoint is null");
			return new ArrayList<GeographicPoint>();
		}
		// Use MapNode better?
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = bfs_findGoal(start, goal, parentMap, nodeSearched);
		if (found) {
			// System.out.println(parentMap);
			return constructPath(start, goal, parentMap);
		} else {
			System.out.println("No path found");
			return new ArrayList<GeographicPoint>();
		}
	}

	public boolean bfs_findGoal(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {

		MapNode startNode = adjmap.get(start);
		MapNode endNode = adjmap.get(goal);
		// Create queue
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();

		toExplore.add(startNode);
		visited.add(startNode);
		boolean found = false;

		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			if (curr == endNode) {
				return true;
			}
			for (Edge edge : curr.getEdge()) {
				MapNode next = adjmap.get(edge.getEnd());

				if (!visited.contains(next)) {
					// Hook for visualization. See writeup.
					nodeSearched.accept(next.getLocation());
					// Update toExplore
					toExplore.add(next);
					// Update visited nodes
					visited.add(next);
					// Update parent map
					parentMap.put(next.getLocation(), curr.getLocation());
				}
			}
		}
		return found;
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 4
		if (start == null || goal == null) {
			System.out.println("GeographicPoint is null");
			return new ArrayList<GeographicPoint>();
		}

		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = dijkstra_findGoal(start, goal, parentMap, nodeSearched);
		if (found) {
			// System.out.println(parentMap);
			return constructPath(start, goal, parentMap);
			// return constructPath(start, goal, parentMap);

//			for (MapNode node : adjmap.values()) {
//				System.out.println(node.getCost());
//			}	
		} else {
			System.out.println("No path found");
			return new ArrayList<GeographicPoint>();
		}
		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());
	}

	public boolean dijkstra_findGoal(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {

		MapNode startNode = adjmap.get(start);
		MapNode endNode = adjmap.get(goal);
		// Create queue
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(100, new MapNode());
		LinkedList<GeographicPoint> visited_path = new LinkedList<GeographicPoint>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		for (MapNode node : adjmap.values()) {
			node.setCost(10000);
		}

		startNode.setCost(0);
		toExplore.add(startNode);
		visited.add(startNode);
		visited_path.add(startNode.getLocation());
		double cost = 0;

		// print the speed limit in each road
//		for (MapNode node : adjmap.values()) {
//			for(Edge edge : node.getEdge()) {
//			System.out.println(edge.getspeedLimit());
//			}
//		}

		// print the length of each road
//		for (MapNode node : adjmap.values()) {
//			for(Edge edge : node.getEdge()) {
//			System.out.println(edge.getStart() + ";" +  edge.getEnd() + ": " + edge.getLengh());
//			}
//		}

		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			// System.out.println(curr.getLocation() + ": " + curr.getCost());

			// loop neighbors
			if (!visited_path.contains(curr.getLocation())) {
				visited_path.add(curr.getLocation());
				nodeSearched.accept(curr.getLocation());
			}

			if (curr == endNode) {
				System.out.println("Dijkstra: " + visited_path.size());
				// System.out.println("Dijkstra visited path: " + visited_path);
				return true;
			}
			for (Edge edge : curr.getEdge()) {
				MapNode next = adjmap.get(edge.getEnd());
				if (!visited_path.contains(next.getLocation())) {
					// compare length in the same node and update, and the Comparator used in
					// PriorityQueue
					// cost = curr.getCost() + edge.getLengh();
					cost = curr.getCost() + edge.getLengh() / edge.getspeedLimit();
					// System.out.println(edge.getStart() + ";" + edge.getEnd() + ":" + cost);
					if (cost < next.getCost()) {
						next.setCost(cost);
						parentMap.put(next.getLocation(), curr.getLocation());
						toExplore.add(next);
					}
				}
			}
		}
		return false;
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 4
		if (start == null || goal == null) {
			System.out.println("GeographicPoint is null");
			return new ArrayList<GeographicPoint>();
		}

		// init all the costs of nodes
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		boolean found = aStar_findGoal(start, goal, path, parentMap, nodeSearched);
		if (found) {
			return constructPath(start, goal, parentMap);
		} else {
			System.out.println("No path found");
			return new ArrayList<GeographicPoint>();
		}
	}

	public boolean aStar_findGoal(GeographicPoint start, GeographicPoint goal, List<GeographicPoint> path,
			HashMap<GeographicPoint, GeographicPoint> parentMap, Consumer<GeographicPoint> nodeSearched) {

		MapNode startNode = adjmap.get(start);
		MapNode endNode = adjmap.get(goal);
		// Create queue
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(100, new aStarComparator());
		LinkedList<GeographicPoint> visited_path = new LinkedList<GeographicPoint>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		for (MapNode node : adjmap.values()) {
			node.setCost(10000);
		}
		for (MapNode node : adjmap.values()) {
			node.setEstCost(0);
		}
		startNode.setCost(0);
		toExplore.add(startNode);
		visited.add(startNode);
		visited_path.add(startNode.getLocation());
		double cost = 0;
		double estimated_cost = 0;

		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			System.out.println(curr.getLocation() + ": " + curr.getCost() + ", " + curr.getEstCost());
			// loop neighbors
			if (!visited_path.contains(curr.getLocation())) {
				visited_path.add(curr.getLocation());
				nodeSearched.accept(curr.getLocation());
			}
			if (curr == endNode) {
				System.out.println("aStar: " + visited_path.size());
				// System.out.println("aStar visited path: " + visited_path);
				return true;
			}
			for (Edge edge : curr.getEdge()) {
				MapNode next = adjmap.get(edge.getEnd());
				if (!visited_path.contains(next.getLocation())) {
					// This part is different from Dijstra search
					System.out.println(curr.getLocation() + " " + next.getLocation() + ": " + curr.getCost());
					cost = curr.getCost() + edge.getLengh() / edge.getspeedLimit();
					// Assume the average speed is 40, this number can be further optimized
					estimated_cost = cost + edge.getEnd().distance(goal) / 40;
					if (estimated_cost < next.getCost()) {
						next.setCost(cost);
						next.setEstCost(estimated_cost);
						parentMap.put(next.getLocation(), curr.getLocation());
						toExplore.add(next);
					}
				}
			}
		}
		return false;
	}

	public List<GeographicPoint> constructPath(GeographicPoint start, GeographicPoint goal,
			HashMap<GeographicPoint, GeographicPoint> parentMap) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		// Can not use curr!=start as condition check, but have no idea why not
		// while(curr!=start) {
		while (!curr.equals(start)) {
			if (curr != null) {
				path.addFirst(curr);
				curr = parentMap.get(curr);
			}
		}
		path.addFirst(start);
		return path;
	}

	public static void main(String[] args) {
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader2.loadRoadMap("data/testdata/speedLimit_simpletest.map", simpleTestMap);
//        System.out.println(simpleTestMap.getNumVertices());
//        System.out.println(simpleTestMap.getNumEdges());
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);
		System.out.println("Dijkstra shortest path" + testroute);
		System.out.println("aStar shortest path" + testroute2);

//		MapGraph testMap = new MapGraph();
//		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//		
//		// A very simple test using real data
//		 testStart = new GeographicPoint(32.869423, -117.220917);
//		 testEnd = new GeographicPoint(32.869255, -117.216927);
//		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//		 testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//
//		System.out.println(testroute);
//		System.out.println(testroute2);
////		
//////		// A slightly more complex test using real data
//		testStart = new GeographicPoint(32.8674388, -117.2190213);
//		testEnd = new GeographicPoint(32.8697828, -117.2244506);
//		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		System.out.println(testroute);
//		System.out.println(testroute2);
//		
//		
//		MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");
//
//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
//
//		List<GeographicPoint> route = theMap.dijkstra(start,end);
//		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

	}
}
