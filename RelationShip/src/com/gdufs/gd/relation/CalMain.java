package com.gdufs.gd.relation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalMain {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		long initialDelay2 = 1;
		long delay2 = 5;
		// 从现在开始5秒钟之后，每隔2秒钟执行一次关系运算,取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度。
		service.scheduleWithFixedDelay(new CalTask(), initialDelay2, delay2,
				TimeUnit.SECONDS);

	}
}
