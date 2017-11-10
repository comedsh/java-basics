package org.shangyang.basic.concurrence.util;

import java.math.BigInteger;

/**
 * 
 * @author shangyang
 *
 */
public class CalcUtil {
	
	/**
	 * 计算阶乘
	 * 
	 * @param i
	 * @return
	 */
	public static BigInteger calculateFactorial(BigInteger i){
		
		BigInteger bi = BigInteger.valueOf(1);
		
		for( int n = 2; n <= i.intValue(); n++){
			
			bi = BigInteger.valueOf(n).multiply( bi );
			
		}
		
		return bi;
	}
	
}	
