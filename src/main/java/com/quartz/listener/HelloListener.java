package com.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class HelloListener implements JobListener {

	@Override
	public String getName() {
		String simpleName = this.getClass().getSimpleName();
		return simpleName;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("Job执行之前============");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("Job执行之前，但又被tigger拒绝============");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		System.out.println("Job执行之后============");
	}

}
