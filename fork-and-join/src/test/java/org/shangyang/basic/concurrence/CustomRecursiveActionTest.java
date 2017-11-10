package org.shangyang.basic.concurrence;

import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

public class CustomRecursiveActionTest {

	@Test
	public void test(){
		
		ForkJoinPool pool = new ForkJoinPool();
		
		CustomRecursiveAction action = new CustomRecursiveAction("abcdefghijklmnopqrstuvwxyz");
		
		pool.submit(action);
		
	}
	
}
