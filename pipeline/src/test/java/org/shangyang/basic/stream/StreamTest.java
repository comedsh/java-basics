package org.shangyang.basic.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	@Test
	public void testStream2(){
		
		List<String> strings = Arrays.asList("abc", "bdfafda", "ab", "abdace");
		
	    List<String> sorted  = strings.stream()
	              .filter(s -> s.startsWith("a"))	              
	              .sorted()
	              .collect(Collectors.toList());
	    
	    System.out.println( sorted.toString() );
	    
	}
	
	@Test
	public void testStream3() throws IOException{
		
		Path path = new File(this.getClass().getResource("stream-data.txt").getFile()).toPath();
		
		List<String> sorted = Files.lines( path )
				.filter( s -> s.startsWith("a") )
				.sorted()
				.collect( Collectors.toList() );
		
		System.out.println( sorted.toString() );
		
	}
	
}
