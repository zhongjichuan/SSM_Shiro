import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class QuartzTets {

	@Autowired
	private SchedulerFactoryBean schedulerFactory; 
	@Autowired
	private SimpleTriggerFactoryBean mySimpleTrigger; 
	
	
	@Test
	public void test() throws Exception {
		Scheduler scheduler = schedulerFactory.getScheduler();
		Date startTime = new Date();
		mySimpleTrigger.setStartTime(startTime);
		scheduler.start();
		schedulerFactory.start();
	}

}
