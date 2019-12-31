package roadgraph;

import java.util.Comparator;

public class aStarComparator implements Comparator<MapNode>{
	@Override
	 public int compare(MapNode x, MapNode y) {
	     // Assume neither string is null. Real code should
	     // probably be more robust
	     // You could also just return x.length() - y.length(),
	     // which would be more efficient.
		 if(x == null || y==null) {
			 throw new NullPointerException("null pointer found");
		 }				 
		 // descending orders
		 if (x.getEstCost() - y.getEstCost() > 0)
		 {
			 return 1;
		 }
		 else {
             return -1;
		 }
	 }

}
