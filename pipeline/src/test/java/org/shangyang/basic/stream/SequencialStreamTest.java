package org.shangyang.basic.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	@Test
	public void testStream5(){
		
		List<Shape> list = new ArrayList<Shape>();
		
		Collections.addAll(list, new Shape("RED"), new Shape("BLUE"), new Shape("BLUE"));
		
		List<Shape> storage = new ArrayList<Shape>();
		
		list.stream()
		        .filter(s -> s.getColor().equals("BLUE"))
		        .forEach( s -> { 
		        	s.setColor("GRAY");
		        	storage.add(s);
		        });
		
		list.forEach( s -> { System.out.println(s.getColor()); } );
		
		storage.forEach( s -> { System.out.println(s.getColor()); } );
	}
	
	class Shape{
		
		String color;
		
		public Shape(String color){
			this.color = color;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		
	}
	
}
