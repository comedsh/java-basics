package org.shangyang.basic.concurrence;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.Test;

public class CustomRecursiveTaskTest {
	
	@Test
	public void test() throws InterruptedException, ExecutionException{
		
		ForkJoinPool pool = new ForkJoinPool();
		
		CustomRecursiveTask action = new CustomRecursiveTask("abcdefghijklmnopqrstuvwxyz");
		
		Future<String> result = pool.submit(action);
		
		System.out.println("the final result: " + result.get() );
		
	}
	
	@Test
	public void testRandom(){
		
		int a = 3, b = a;
		
		System.out.println(b);
		
	}
	
}
