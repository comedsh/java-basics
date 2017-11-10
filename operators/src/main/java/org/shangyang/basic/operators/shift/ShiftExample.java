package org.shangyang.basic.operators.shift;

public class ShiftExample {

	public static void main(String[] args) {

		int m = -7;
		
		System.out.println("m 的二进制码是：" + Integer.toBinaryString(m));
		
		System.out.println("m >> 2 的二进制码是：" + Integer.toBinaryString(m >> 2));
		
		System.out.println("m >> 2 = " + (m >> 2));
		
		System.out.println("m << 2 的二进制码是：" + Integer.toBinaryString(m << 2));
		
		System.out.println("m << 2 = " + (m << 2));
		
		System.out.println("m >>> 24 的二进制码是：" + Integer.toBinaryString(m >>> 24));
		
		System.out.println("m >>> 24 = " + (m >>> 24));
		
		System.out.println("m >> 2 << 2= " + (m >> 2 << 2));

		System.out.println("m >> 2 <<2 的二进制码是：" + Integer.toBinaryString(m >> 2 << 2));		

	}

}
