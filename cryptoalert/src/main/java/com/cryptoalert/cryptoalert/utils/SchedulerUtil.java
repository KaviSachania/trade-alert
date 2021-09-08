package com.cryptoalert.cryptoalert.utils;

import com.cryptoalert.cryptoalert.jobs.AlertJob;
import com.cryptoalert.cryptoalert.jobs.CandidateJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulerUtil {

    public static void schedule() {
        try {
            Scheduler sched = new StdSchedulerFactory().getScheduler();

            scheduleCandidates(sched);
            scheduleAlerts(sched);

            sched.start();
        } catch (SchedulerException e) {
            System.out.println(e);
        }
    }

    private static void scheduleCandidates(Scheduler sched) {
        try {
            JobDetail job = newJob(CandidateJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(cronSchedule("0 0 0/1 1/1 * ? *")) // 2am
//                    .withSchedule(cronSchedule("0 0/30 * 1/1 * ? *")) // every 30 minutes
                    .build();

            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            System.out.println(e);
        }
    }

    private static void scheduleAlerts(Scheduler sched) {
        try {
            JobDetail job = newJob(AlertJob.class)
                    .withIdentity("job2", "group2")
                    .build();

            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger2", "group2")
//                    .withSchedule(cronSchedule("0 0/43 * 1/1 * ? *")) // every 10 minutes
                    .withSchedule(cronSchedule("0 0 6 ? * SAT *")) // Saturday morning at 6am
                    .build();

            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            System.out.println(e);
        }
    }

}
