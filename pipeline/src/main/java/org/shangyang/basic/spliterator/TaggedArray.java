package org.shangyang.basic.spliterator;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * Here is a class (not a very useful one, except for illustration) that
 * maintains an array in which the actual data are held in even locations, and
 * unrelated tag data are held in odd locations. Its Spliterator ignores the
 * tags.
 * 
 * 这么一个例子，就是说将真实的数据放置在偶数的位置，将标签数据放置到奇数的位置；
 * 
 * @author shangyang
 *
 * @param <T>
 */
class TaggedArray<T> {

	private final Object[] elements; // immutable after construction

	TaggedArray(T[] data, Object[] tags) {

		int size = data.length;

		if (tags.length != size)
			throw new IllegalArgumentException();

		this.elements = new Object[2 * size];

		for (int i = 0, j = 0; i < size; ++i) {

			elements[j++] = data[i];

			elements[j++] = tags[i];
		}
	}

	public Spliterator<T> spliterator() {

		return new TaggedArraySpliterator<>(elements, 0, elements.length);
	}

	static class TaggedArraySpliterator<T> implements Spliterator<T> {

		private final Object[] array;

		private int origin; // current index, advanced on split or traversal

		private final int fence; // one past the greatest index

		TaggedArraySpliterator(Object[] array, int origin, int fence) {

			this.array = array;

			this.origin = origin;

			this.fence = fence;
		}

		@SuppressWarnings("unchecked")
		public void forEachRemaining(Consumer<? super T> action) {

			for (; origin < fence; origin += 2)
				action.accept((T) array[origin]);
		}

		@SuppressWarnings("unchecked")
		public boolean tryAdvance(Consumer<? super T> action) {

			if (origin < fence) {

				action.accept((T) array[origin]);

				origin += 2; // 步长为 2 的目的是为了去获取真实的数据；

				return true;

			} else // cannot advance

				return false;
		}
		
		/**
		 * 需要注意的是，这里的切割并非将原有的 array 真正切割成两个部分然后分别赋值给不同的 array 对象；而是依然使用同一个 array 对象，
		 * 只是分别调整 split 以后的 array 的起始和结束坐标而已；
		 */
		public Spliterator<T> trySplit() {

			int lo = origin; // divide range in half

			int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even

			if (lo < mid) { // split out left half

				origin = mid; // reset this Spliterator's origin

				return new TaggedArraySpliterator<>(array, lo, mid);

			} else // too small to split
				return null;
		}

		public long estimateSize() {

			return (long) ((fence - origin) / 2);
		}

		public int characteristics() {

			return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
		}
	}

	static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
		
		Spliterator<T> s = a.spliterator();
		
		long targetBatchSize = s.estimateSize() / (ForkJoinPool.getCommonPoolParallelism() * 8);
		
		new ParEach<T>(null, s, action, targetBatchSize).invoke();
	}

	static class ParEach<T> extends CountedCompleter<Void> {

		private static final long serialVersionUID = 8754242475214197097L;
		
		final Spliterator<T> spliterator;
		
		final Consumer<T> action;
		
		final long targetBatchSize;

		ParEach(ParEach<T> parent, Spliterator<T> spliterator, Consumer<T> action, long targetBatchSize) {
			
			super(parent);
			
			this.spliterator = spliterator;
			
			this.action = action;
			
			this.targetBatchSize = targetBatchSize;
		}

		public void compute() {
			
			Spliterator<T> sub;
			
			// 只要当 spliterator 的 size 大于 targetBatchSize 的时候，就不停的对 spliterator 进行切割，生成更多的 sub spliterator
			while (spliterator.estimateSize() > targetBatchSize && (sub = spliterator.trySplit()) != null) {
				
				addToPendingCount(1);
				
				new ParEach<>(this, sub, action, targetBatchSize).fork();
			}
			
			spliterator.forEachRemaining(action);
			
			propagateCompletion();
		}
	}
}