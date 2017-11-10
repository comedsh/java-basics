package org.shangyang.basic.concurrence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * 这个例子比较好玩，它可以将字符串长度超过 4 的字符串进行无限的拆分成多个 sub tasks；<br>
 * 
 * 这个例子的逻辑非常的简单，就是将字符串由小写转换成大写并且输出；<br>
 * 
 * 注意，RecursiveAction 是不返回结果的；
 * 
 * @author shangyang
 *
 */
public class CustomRecursiveAction extends RecursiveAction {

	private static final long serialVersionUID = 1625962939484825261L;
	
	private String workload;
	
	private static final int THRESHOLD = 4;

	public CustomRecursiveAction(String workload) {
		
		this.workload = workload;
	}

	@Override
	protected void compute() {
		
		if (workload.length() > THRESHOLD) {
			
			ForkJoinTask.invokeAll(createSubtasks());
			
		} else {
			
			processing(workload);
		}
	}

	private List<CustomRecursiveAction> createSubtasks() {
		
		List<CustomRecursiveAction> subtasks = new ArrayList<>();

		String partOne = workload.substring(0, workload.length() / 2);
		String partTwo = workload.substring(workload.length() / 2, workload.length());

		subtasks.add(new CustomRecursiveAction(partOne));		
		subtasks.add(new CustomRecursiveAction(partTwo));

		return subtasks;
	}

	private void processing(String work) {
		
		String result = work.toUpperCase();
		
		System.out.println("This result - (" + result + ") - was processed by " + Thread.currentThread().getName());
	}

}
