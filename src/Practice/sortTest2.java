package Practice;

import java.util.Collections;
//Test.java
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import Comparable_Comparator.MyComparator;
import geography.GeographicPoint;
import roadgraph.Edge;
import roadgraph.MapNode;

public class sortTest2 {
	
 public static void main(String[] args) {
	 //lambda expression
    // PriorityQueue<String> queue = new PriorityQueue<String>(10, (a,b)-> a.length() - b.length());
     //method reference
     //PriorityQueue<String> queue = new PriorityQueue<String>(10, Comparator.comparing(String::length));
	 //Override compare method
//		MapNode(GeographicPoint location, List<Edge> edge, double cost){
//			this.location = location;
//			this.edge = edge;
//			this.cost = cost;
//		}
	 GeographicPoint x = new GeographicPoint(1,1);
	 GeographicPoint y = new GeographicPoint(2,2);
	 System.out.println(x.distance(y));
		MapNode node1 = new MapNode(null, null, 10);
		MapNode node2 = new MapNode(null, null, 9);
		MapNode node3 = new MapNode(null, null, 12);
		MapNode node4 = new MapNode(null, null, 0);
	PriorityQueue<MapNode> queue = new PriorityQueue<MapNode>(100, new MapNode());
     queue.add(node1);
     queue.add(node2);
     queue.add(node3);
     queue.add(node4);
     node3.setCost(5);
     while (queue.size() != 0) {
         System.out.println(queue.remove().getCost());
     }
 }
}


class StringLengthComparator implements Comparator<String> {
	 @Override
	 public int compare(String x, String y) {
	     // Assume neither string is null. Real code should
	     // probably be more robust
	     // You could also just return x.length() - y.length(),
	     // which would be more efficient.
	     if (x.length() < y.length()) {
	         return -1;
	     }
	     if (x.length() > y.length()) {
	         return 1;
	     }
	     return 0;
	 }
	}
