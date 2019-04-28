package com.quartz.Job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import com.quartz.listener.HelloListener;

public class HelloQuartzJob implements Job{

	public static int count = 0;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = dateFormat.format(date);
		count++;
		//context
		//System.out.println(context.getJobDetail().getKey().getName());
		System.out.println(context.getJobDetail().getJobDataMap().get("jobMessage"));
		System.out.println("执行job"+dateString+"-"+count);
	}

	public static void main(String[] args) throws Exception {
		//调度器
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		 
		 //可以通过properties文件进行属性配置。/org/quartz/quartz.properties
		 Scheduler scheduler = schedFact.getScheduler();
		 
		 //任务实例
		 JobDetail job = JobBuilder.newJob(HelloQuartzJob.class)//添加任务，和HelloQuartzJob绑定
				 			.withIdentity("job1", "HelloJob")//设置job名，job组名
				 			.usingJobData("jobMessage", "jobMessage===")//存储参数
				 			.build();
		 //当前job的名字和组名
		 //System.out.println(job.getKey().getName()+"//"+job.getKey().getGroup());
		 
		 //触发器
		 //cornTrigger,表达式可以使用工具生成	
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("tigger1", "tigger")
				.startNow()//设置现在触发
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))//设置执行方式
				.build();
		//触发器和job进行关联,返回任务调度开始时间
		 Date date = scheduler.scheduleJob(job, trigger);	
		 //创建并注册一个全局Job Listener,全局监听器
		 //scheduler.getListenerManager().addJobListener(new HelloListener(),EverythingMatcher.allJobs());
		 scheduler.getListenerManager().addJobListener(new HelloListener(),KeyMatcher.keyEquals(JobKey.jobKey("job1", "HelloJob")));
		 scheduler.start();
	}
}
