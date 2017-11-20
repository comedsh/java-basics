package org.shangyang.basic.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class SequencialStreamTest {

	@Test
	public void testStream(){
		
		List<String> strings = Arrays.asList("abc", "bdfafda", "ab", "abdace");
		
		OptionalInt longestStringLengthStartingWithA 
	        = strings.stream()
	              .filter(s -> s.startsWith("a"))
	              .mapToInt(String::length)
	              .max();
		
		System.out.println( longestStringLengthStartingWithA.isPresent() ? longestStringLengthStartingWithA.getAsInt() : "null" );
		
		Stream<String> stream = strings.stream();
		
		System.out.println(stream.spliterator().getExactSizeIfKnown());
		
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
		
		Stream<String> stream = Files.lines( path );
		
		List<String> sorted = stream.filter( s -> s.startsWith("a") )
						.sorted()
						.collect( Collectors.toList() );
		
		stream.close();
		
		System.out.println( sorted.toString() );
		
	}
	
	@Test
	public void testStream4(){
		
		List<String> strings = Arrays.asList("abc", "bdfafda", "ab", "abdace");
		
	    Optional<String> r = strings.stream()
	              .filter(s -> s.startsWith("a"))
	              .findFirst();
	    
	    System.out.println( r.get() );		
		
	}
	
}
