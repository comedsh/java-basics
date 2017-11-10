package org.shangyang.basic.concurrence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<String>{

	private static final long serialVersionUID = 2772159741169463428L;

	private String workload;
	
	private static final int THRESHOLD = 4;

	public CustomRecursiveTask(String workload) {
		
		this.workload = workload;
	}

	@Override
	protected String compute() {
		
		if (workload.length() > THRESHOLD) {
			
			System.out.println("compute() invoked by "+Thread.currentThread().getName() +": "+ workload + " gets splitted by ");
			
			StringBuilder str = new StringBuilder();
			
			Iterator<CustomRecursiveTask> iter = ForkJoinTask.invokeAll(createSubtasks()).iterator();
			
			while(iter.hasNext()){
				
				CustomRecursiveTask task = iter.next();
				
				str.append( task.join() );
			}
			
			return str.toString();
			
		} else {
			
			System.out.println("compute() invoked by "+Thread.currentThread().getName() +": "+ workload + " gets toUpperCase()");
			
			return processing(workload);
		}
		
	}

	private List<CustomRecursiveTask> createSubtasks() {
		
		List<CustomRecursiveTask> subtasks = new ArrayList<>();

		String partOne = workload.substring(0, workload.length() / 2);
		String partTwo = workload.substring(workload.length() / 2, workload.length());

		subtasks.add(new CustomRecursiveTask(partOne));		
		subtasks.add(new CustomRecursiveTask(partTwo));

		return subtasks;
	}

	private String processing(String work) {
		
		String result = work.toUpperCase();
		
		System.out.println("This result - (" + result + ") - was processed by " + Thread.currentThread().getName());
		
		return result;
	}


}
