package org.shangyang.basic.stream;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import org.junit.Test;

public class StreamTest {

	@Test
	public void testStream(){
		
		List<String> strings = Arrays.asList("abc", "bdfafda", "ab", "abdace");
		
		OptionalInt longestStringLengthStartingWithA 
	        = strings.stream()
	              .filter(s -> s.startsWith("a"))
	              .mapToInt(String::length)
	              .max();
		
		System.out.println( longestStringLengthStartingWithA.isPresent() ? longestStringLengthStartingWithA.getAsInt() : "null" );
		
	}
	
	
}
