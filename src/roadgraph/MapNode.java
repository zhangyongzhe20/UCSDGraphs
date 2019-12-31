package roadgraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import geography.GeographicPoint;


/**
 * @author Zhang Yong Zhe
 *To create a vertex in a graph and store the location of the vertex with its edges;
 *
 */
public class MapNode implements Comparator<MapNode>{
	private GeographicPoint location;
	// use HashSet will be faster
	private List<Edge> edge;
	private double cost;
	// Estimated cost = cost + heuristic cost
	private double EstCost;
	
	// for calling comparator interface
	public MapNode(){
	}
	
	public MapNode(GeographicPoint location){
		this.location = location;
		edge = new ArrayList<Edge>();
		cost = 1000000;
		EstCost = 0;
	}
	
	public MapNode(GeographicPoint location, List<Edge> edge, double cost){
		this.location = location;
		this.edge = edge;
		this.cost = cost;
	}
	
	public List<Edge> getEdge(){
		return this.edge;
	}
	
	public double getCost(){
		return this.cost;
	}
	
	public double getEstCost(){
		return this.EstCost;
	}
	
	public  GeographicPoint getLocation() {
		return this.location;
	}
	
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setEstCost(double EstCost) {
		this.EstCost = EstCost;
	}
	
	public void addNeighbors(Edge edge) {
		try {
		 this.edge.add(edge);
		}
		catch(NullPointerException e) {
			//System.out.println("Catch it in MapNode class");
		}
	}
	// copy from sample code
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
//	@Override
//	public int hashCode()
//	{
//		return location.hashCode();
//	}
	
	
	@Override
	public String toString()
	{
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (Edge e: edge) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}
	
	@Override
	 public int compare(MapNode x, MapNode y) {
	     // Assume neither string is null. Real code should
	     // probably be more robust
	     // You could also just return x.length() - y.length(),
	     // which would be more efficient.
		 if(x == null || y==null) {
			 throw new NullPointerException("null pointer found");
		 }				 
		 
		 // DONT WRITE THIS:  return "(int)x.getCost() - (int)y.getCost();"
		 // descending orders
			 if (x.getCost() - y.getCost() > 0)
			 {
				 return 1;
			 }
			 else {
				 return -1;
			 }
	 }
}
