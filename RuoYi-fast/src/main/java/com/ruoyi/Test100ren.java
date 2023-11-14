package com.ruoyi;

import java.util.ArrayList;
import java.util.List;

public class Test100ren {
	private static int kxcs=0;//单轮样本开箱次数
	private static int zrs=50*2;//单轮样本总人数（必须偶数）
	private static int maxcs=zrs/2;//单轮样本单人最大开箱次数（默认总人数的一半）
	private static int box[];//箱子
	
	
	private static int zongyangben=0;//总样本数
	private static int success=0;//成功数
	private static int failure=0;//失败数
	private static boolean boo=true;//游戏开关
	private static int jishuqi=0;//计数器
	public static void main(String[] args) {
		
		while(zongyangben<10000000){
			//初始化
			boo=true;
			
			//样本次数+1
			zongyangben++;
			//单轮样本创建箱子
			createBox();
			//初始成功人数计数器
			jishuqi=0;
			//囚犯排队编号
			int i=0;
			do{
				i++;
				if(i>zrs){
					//所有囚犯依次都开过了，结束。
					break;
				}
				//初始第一个囚犯号码
				int frhm=i;
				//初始每个人开箱次数
				kxcs=0;
				//每个人第一次默认自己打开自己
				startGame(frhm,frhm);
				if(jishuqi==zrs){
					//所有人都成功了呢！！！
					success++;
					boo=false;//终止
				}
			}while(boo);
			System.out.println("第"+zongyangben+"局结束。"+"结果：成功"+success+"例，失败"+failure+"例。目前胜率"+((success*1.0)/zongyangben));
		}
	}
	public static void startGame(int selfNumber,int openNumber){
		if(maxcs<=kxcs){
			//达到上限了，不让开了。
			//有1个人达到上限没出去的，全体暴毙死了。
			failure++;
			boo=false;//终止
			return;
		}
		//开箱
		kxcs++;
		int num=box[openNumber-1];
		if(num==selfNumber){
			//这个人可以结束了，下一位。
			jishuqi++;
			return;
		}
		//没开到自己的，还得开。
		startGame(selfNumber,num);
	}
	public static void createBox(){
		//制造箱子
		List<Integer> paper=new ArrayList<Integer>();
		for(int i=1;i<=zrs;i++){
			paper.add(i);
		}
		box=new int[zrs];
		int index;
		for(int i=0;i<box.length;i++){
			index=(int)(Math.random()*(paper.size()));
			box[i]=paper.get(index);
			paper.remove(index);
		}
	}
}