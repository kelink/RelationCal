package com.gdufs.gd.relation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class CalMain {
	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		long initialDelay2 = 1;
		long delay2 = 5;		
		service.scheduleWithFixedDelay(new CalTask(), initialDelay2, delay2,
				TimeUnit.SECONDS);	
	}
}
