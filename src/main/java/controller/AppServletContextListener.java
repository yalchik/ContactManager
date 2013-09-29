package controller;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.jobs.MailingJob;

/**
 * Creates Schedule for mailing job on context initialized
 * And shutdown it in context destroyed
 */
@WebListener
public class AppServletContextListener implements ServletContextListener {

	/** Schedule for mailing job */
	private Scheduler sched;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    /**
     * Default constructor. 
     */
    public AppServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {

    	int intervalInSeconds = Integer.parseInt(event.getServletContext().getInitParameter("mailing_interval_seconds"));
    	String adminEmail = event.getServletContext().getInitParameter("admin_email");
		String host = event.getServletContext().getInitParameter("mail.smtp.host");
		
    	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();    	
		try {
			sched = schedFact.getScheduler();
			
			JobDetail job = JobBuilder.newJob(MailingJob.class)
				      .withIdentity("mailingJob", "group1")
				      .build();
			
			job.getJobDataMap().put("adminEmail", adminEmail);
			job.getJobDataMap().put("host", host);			
			
			Trigger trigger = TriggerBuilder.newTrigger()
			    .withIdentity("trigger1", "group1")
			    .startNow()
			    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
		          .withIntervalInSeconds(intervalInSeconds)
		          .repeatForever())
			    .build();
			
			sched.scheduleJob(job, trigger);
			sched.start();
			
		} catch (SchedulerException e) {
			logger.error("Schedule hasn't been started", e);
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    	try {
			sched.shutdown(true);
		} catch (SchedulerException e) {
			logger.error("Schedule hasn't been stopped", e);
		}
    }
	
}
