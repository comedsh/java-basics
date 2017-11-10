package org.shangyang.basic.concurrence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

import org.shangyang.basic.concurrence.util.CalcUtil;

/**
 * CountedCompleter must be complied under Java SE 8
 * 
 * @author shangyang
 *
 */
public class CountedCompleterExample {

	public static void main(String[] args) {
		
		List<BigInteger> list = new ArrayList<>();
		
		for (int i = 3; i < 20; i++) {
			
			list.add(new BigInteger(Integer.toString(i)));
		}
		
		ForkJoinPool.commonPool().invoke( new FactorialTask(null, list) );
		
	}

	private static class FactorialTask extends CountedCompleter<Void> {

		private static final long serialVersionUID = -2998768710556055174L;
		
		private static int SEQUENTIAL_THRESHOLD = 5;
		
		private List<BigInteger> integerList;

		private FactorialTask(CountedCompleter<Void> parent, List<BigInteger> integerList) {
			
			super(parent);
			
			this.integerList = integerList;
		}

		@Override
		public void compute() {
			
			boolean split = false;
			
			if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
				
				showFactorial();
				
			} else {
				
				split = true;
				
				int middle = integerList.size() / 2;
				
				List<BigInteger> rightList = integerList.subList(middle, integerList.size());
				
				List<BigInteger> leftList = integerList.subList(0, middle);
				
				// 表示还剩下两个任务需要被执行，注意，只有在需要被 split 的情况下，才会加入 pending，所以前几次调用，当 integerList.size > SEQUENTIAL_THRESHOLD 的情况下，便会累加 pending。
				addToPendingCount(2);
				
				// 注意，如果是被 Splitted 过的 FactorialTask，它的 CountedCompleter.countedCompleter = super，所以这里会形成这样一种链条，既是 root -> super -> super -> super -> leaf
				FactorialTask taskRight = new FactorialTask(this, rightList); 
				
				FactorialTask taskLeft = new FactorialTask(this, leftList);
				
				taskLeft.fork();
				
				taskRight.fork();
				
				System.out.printf("=====> Splits %s, integerList.size: %s, leftList.size: %s, rightList.size: %s of thread : %s%n", 
						integerList.toString(), integerList.size(), leftList.size(), rightList.size(), Thread.currentThread().getName() );
			}
			
			System.out.printf("-----> The current pending count: %s before try complete with %s splitted of thread : %s%n", this.getPendingCount(), split == true ? integerList.toString():"no", Thread.currentThread().getName());		
			
			tryComplete();
			
			System.out.printf("-----> The current pending count: %s after try complete with %s splitted of thread : %s%n", this.getPendingCount(), split == true ? integerList.toString():"no", Thread.currentThread().getName());
			
		}
		
		/**
		 * 好奇的是，该方法的判断条件 caller == this，通过这个方式判断所得到的既是 leaf 的执行回调；但是我们知道整个结构是这样的啊，root -> super -> super -> super -> leaf，而 caller 正好是 super 呀？
		 * 所以这里不就不能判断得出是否是 leaf? 结果认真分析了一下 tryComplete() 方法，答案就在下面这两段代码，
		 * 
		 * CountedCompleter<?> a = this, s = a;
		 * 
		 * a.onCompletion(s);
		 * 
		 * 看到没有，上面进行了移花接木，也就是说，onCompletion 回调的就是 this 本身；
		 * 
		 * @param caller
		 */
		@Override
		public void onCompletion(CountedCompleter<?> caller) {
			
			if (caller == this) {
				
				System.out.printf("****** on completed of this object, those numbers %s get calculated of thread : %s%n", integerList.toString(), Thread.currentThread().getName());
				
			}else{
				
				System.out.printf(">>>>>> on completed of this parents, those numbers %s weird thread : %s%n", integerList.toString(), Thread.currentThread().getName());
			}
		}

		private void showFactorial() {

			for (BigInteger i : integerList) {
				
				BigInteger factorial = CalcUtil.calculateFactorial(i);
				
				System.out.printf("%s! = %s, thread = %s%n", i, factorial, Thread.currentThread().getName());

			}
		}
	}
}