package com.quartz.test;

import java.util.Date;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class Quartz_Spring {

	public static void main(String[] args) throws SchedulerException {
		ApplicationContext actx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		Scheduler schedulerFactory = (Scheduler) actx.getBean("schedulerFactoryBean");
//		SimpleTrigger mySimpleTrigger = (SimpleTrigger) actx.getBean("mySimpleTrigger");
//		schedulerFactory.start();
	}
}
