package com.base.test;

public class ParamTest {
	public static void main(String[] args) {
		int page = 2;
		int size = 10;
		int count = 11;
		
		int lr = 3;
		
		int pageCount = count / size;
		if(count % size > 0){
			pageCount++;
		}
		
		int start = page - lr;
		System.out.println(start);
		if(page - lr < 1){
			start = 1;
		}

		System.out.println("start = " + start);
		
		int end = start + lr * 2;
		if(end > pageCount){
			end = pageCount;
			start = end - lr * 2;
			if(start < 1){
				start = 1;
			}
		}
		
		
		

		System.out.println("start = " + start);
		System.out.println("end = " + end);
		
		
		
		
		for (int i = start; i <= end; i++) {
			System.out.print(i);
			if(i == page){
				System.out.println(" - active");
			}else{
				System.out.println();
			}
		}
	}
}