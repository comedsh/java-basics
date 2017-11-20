package org.shangyang.basic.stream;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.junit.Test;

public class ParallelStreamTest {

	@Test
	public void testStream1(){
		
		List<String> strings = Arrays.asList("abc", "bdfafda", "ab", "abdace");
		
		OptionalInt longestStringLengthStartingWithA 
	        = strings.parallelStream()
	              .filter(s -> s.startsWith("a"))
	              .mapToInt(String::length)
	              .max();
		
		System.out.println( longestStringLengthStartingWithA.isPresent() ? longestStringLengthStartingWithA.getAsInt() : "null" );
		
	}
	
	@Test
	public void testStream2(){
		
		List<String> strings = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
		
		List<String> upperCaseStrings = strings.parallelStream()
			   .map( e -> e.toUpperCase() )
			   .collect( Collectors.toList() );
			   
		System.out.println(upperCaseStrings);

	}
	
	
	@Test
	public void testAny(){
		System.out.println( 5 >>> 1 );
	}
	
}
