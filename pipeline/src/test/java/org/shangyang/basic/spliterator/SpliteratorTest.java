package org.shangyang.basic.spliterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

import org.junit.Test;

public class SpliteratorTest {

	@Test
	public void testAny(){
		
		List<String> arrays = Arrays.asList("a","b","c","d");
		
		Iterator<String> iter = arrays.iterator();
		
		while(true){
			System.out.println(iter.next());
		}
		
	}
	
	@Test
	public void testArrayListSpliterator(){
		
		List<String> list = new ArrayList<String>();
		
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		
		Spliterator<String> s1 = list.spliterator();
		
		s1.forEachRemaining( e -> {System.out.printf("for each remaining: %s %n", e ); } );
		
		
		
		Spliterator<String> s2 = s1.trySplit();
		
		System.out.printf( "after splitted, the size of s2: %s, the size of s1: %s %n", s2.getExactSizeIfKnown(), s1.getExactSizeIfKnown() );
		
	}
	
	
	@Test
	public void testArraySpliterator(){
		
		List<String> arrays = Arrays.asList("a","b","c","d");
		
		Spliterator<String> spliterator = arrays.spliterator();
		
		spliterator.trySplit();
		
	}

}
