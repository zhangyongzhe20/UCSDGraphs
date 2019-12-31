package Practice;

import java.util.Collections;
//Test.java
import java.util.Comparator;
import java.util.PriorityQueue;

import Comparable_Comparator.MyComparator;

public class sortTest {
	
 public static void main(String[] args) {
	 //lambda expression
    // PriorityQueue<String> queue = new PriorityQueue<String>(10, (a,b)-> a.length() - b.length());
     //method reference
     //PriorityQueue<String> queue = new PriorityQueue<String>(10, Comparator.comparing(String::length));
	 //Override compare method
	 PriorityQueue<String> queue = new PriorityQueue<String>(10, new StringLengthComparator1());
     queue.add("short");
     queue.add("hi");
     queue.add("very long indeed");
     queue.add("medium");
     while (queue.size() != 0) {
         System.out.println(queue.remove());
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
