package com.ruoyi;

import java.util.ArrayList;
import java.util.List;

public class a {
	public static void main(String[] args) {
		int 成功次数 = 0 ,失败次数 = 0,总次数 = 0 ;
		while (true) {
			//每次都新生成100个箱子
			int[][] xz = xz(100);
			int kxcs = 50;//打开次数
			boolean success = true;
			//100个人连续开箱，其中一个爆炸，集体爆炸
			for (int i = 1; i <= 100; i++) {
				if(dkxz(xz, i, i, kxcs) != 1){
					success = false; 
					break;//其中一个爆炸，集体爆炸
				}
			}
			if(success) 成功次数++ ; else 失败次数 ++ ;
			总次数 ++ ;
			if(总次数 % 10000 == 0 ) System.out.println("总次数="+总次数+"，成功次数="+成功次数+"，失败次数="+失败次数+"，成功率="+((成功次数+0.00)/(总次数+0.00)));
		}
	}
	/**
	 * 打开箱子
	 * @param xz 箱子组
	 * @param frhm  犯人号码
	 * @param dkhm  打开号码
	 * @param kxcs  开箱的次数
	 */ 
	static int boom = 0 ;//爆炸次数
	static int sjkxcs = 0 ;//实际开箱次数
	private static int dkxz(int[][] xz,int frhm,int dkhm , int kxcs) {
		//打开箱子
		int num = xz[dkhm-1][0]; 
		sjkxcs ++ ; //每打开一次箱子 计数器+1
		if(sjkxcs > kxcs) { //大于开箱次数次了直接死
			sjkxcs = 0 ;
			boom ++;
			return 0;
		}
		if(num == frhm) {
			sjkxcs = 0 ;
			//如果打开的号码和犯人号码一致，且不能大于开箱次数次，就成功 
			return 1;
		} else {
			int dkxz = dkxz(xz, frhm, num, kxcs);
			return dkxz;
		}
	}
	/**
	 * 创建箱子
	 * @param xz 箱子数量
	 * @return
	 */
	private static int[][] xz(int xz) {
		//先生成100个纸条
		List<Integer> zt = new ArrayList<Integer>();		
		for (int i = 1; i <= xz; i++) {
			zt.add(i);
		}
		//再生成100个箱子
		int a[][] = new int[xz][1];
		//把纸条放箱子里
		for (int i = 0; i < a.length; i++) {
			//随机取个纸条
			int index = (int)(Math.random()*(zt.size()));
			//放箱子里
			a[i][0] = zt.get(index);
			//将已经放箱子里的纸条删了
			zt.remove(index);
		}
		return a;
	}
}
