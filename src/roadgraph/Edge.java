package roadgraph;
import geography.GeographicPoint;

/**
 * @author Zhang Yong Zhe
 *To create an edge by a given vertex(starting vertex) in a graph.
  *A Edge contains five variables which are the "GeographicPoint start", 
  *"GeographicPoint end", "String roadName", "String roadType" and "double length".
 *
 */

public class Edge {
	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double length;
	private double speedLimit;
	
	Edge(){
		start = new GeographicPoint(0, 0);
		end = new GeographicPoint(0, 0);
		roadName = "Nanyang Crescent";
		roadType = "residential";
		length = 0;
		speedLimit = 50;
	}
	
	Edge(GeographicPoint start, GeographicPoint end, 
			String roadName, String roadType, double length){
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.roadType = roadType;
		// use start and end point to calculate the distance
		this.length = start.distance(end);
		//this.length = Math.sqrt((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y));
	}
	
	
	Edge(GeographicPoint start, GeographicPoint end, 
			String roadName, String roadType, double length, double speedlimit){
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = start.distance(end);
		//store speed limit
		this.speedLimit = speedlimit;
		
	}
	

	public GeographicPoint getStart(){
		return this.start;
	}
	
	public GeographicPoint getEnd(){
		return this.end;
	}
	
	public String getRoadName(){
		return this.roadName;
	}
	
	public String getRoadType() {
		return this.roadType;
	}
	
	public double getLengh() {
		return this.length;
	}
	
	public double getspeedLimit() {
		return this.speedLimit;
	}
	
	public void setStart(GeographicPoint start) {
		this.start = start;
	}
	
	public void setEnd(GeographicPoint end) {
		this.end = end;
	}
	
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	
	public void setLength(GeographicPoint start, GeographicPoint end) {
		 this.length = start.distance(end);
		//this.length = end.y - start.y;
		//this.length = Math.sqrt((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y));
	}
}
