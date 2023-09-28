package com.abing.config.job;

import com.abing.job.TestJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author 阿炳亿点点帅
 * 管理JOB任务的启动
 */
@Component
public class QuartzSchedulerManager {
    @Resource
    private Scheduler scheduler;

    /**
     * 开始执行定时器
     * @throws SchedulerException
     */
    public void startJob() throws SchedulerException {
//        notifyOnceWeekJob(scheduler);
//        notifyPerDayJob(scheduler);
//        remindEatToBreakfastJob(scheduler);
        test(scheduler);
        scheduler.start();
    }


    /**
     * test
     * @param scheduler
     * @throws SchedulerException
     */
    private void test(Scheduler scheduler) throws SchedulerException {
        JobDetail remindEatToBreakfastJob = JobBuilder.newJob(TestJob.class)
                .withIdentity("test","DEFAULT")
                .storeDurably()
                .build();
        SimpleTrigger testTrigger = TriggerBuilder.newTrigger()
                .withIdentity("testTrigger","DEFAULT")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(remindEatToBreakfastJob,testTrigger);
    }
//
//    /**
//     * 每天早上 9点  提醒记得吃早饭
//     * cron = "0 0 9 ? * * *"
//     * @param scheduler
//     */
//    private void remindEatToBreakfastJob(Scheduler scheduler) throws SchedulerException {
//        JobDetail remindEatToBreakfastJob = JobBuilder.newJob(RemindToEatBreakfastJob.class)
//                .withIdentity("remindEatToBreakfastJob")
//                .storeDurably()
//                .build();
//        CronTrigger remindEatToBreakfastTrigger = TriggerBuilder.newTrigger()
//                .forJob(remindEatToBreakfastJob)
//                .withSchedule(CronScheduleBuilder.cronSchedule(CronConstant.PER_DAY_9_REMIND_BREAKFAST))
//                .build();
//        scheduler.scheduleJob(remindEatToBreakfastJob,remindEatToBreakfastTrigger);
//    }
//
//
//    /**
//     * 每周六中午12点发送邮件
//     * cron = "0 0 12 ? * 7 *"
//     * @param scheduler
//     */
//    private void notifyOnceWeekJob(Scheduler scheduler) throws SchedulerException {
//        JobDetail notifyOnceWeekJob = JobBuilder.newJob(NotifyOnceWeekJob.class)
//                .withIdentity("notifyOnceWeekJob")
//                .storeDurably()
//                .build();
//        CronTrigger notifyOnceWeekTrigger = TriggerBuilder.newTrigger()
//                .forJob(notifyOnceWeekJob)
//                .withSchedule(CronScheduleBuilder.cronSchedule(CronConstant.ONCE_WEEK_12_JOB))
//                .build();
//        scheduler.scheduleJob(notifyOnceWeekJob,notifyOnceWeekTrigger);
//    }
//
//    /**
//     * 每天晚上七点总结当天任务情况 发送邮件
//     * 工作日的每周一到周五晚上七点 cron = "0 0 19 ? * 2,3,4,5,6 *"
//     * @param scheduler
//     */
//    private void notifyPerDayJob(Scheduler scheduler) throws SchedulerException {
//        JobDetail notifyPerDayJob = JobBuilder.newJob(NotifyPerDayJob.class)
//                .withIdentity("notifyPerDayJob")
//                .storeDurably()
//                .build();
//
//        CronTrigger notifyPerDayTrigger = TriggerBuilder.newTrigger()
//                .forJob(notifyPerDayJob)
//                .withSchedule(CronScheduleBuilder.cronSchedule(CronConstant.PER_DAY_19_JOB))
//                .build();
//        scheduler.scheduleJob(notifyPerDayJob,notifyPerDayTrigger);
//    }

    /**
     * 获取Job信息
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改某个任务的执行时间
     * @param name
     * @param group
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String time) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }
    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称(建议唯一)
     * @param jobGroupName
     *            任务组名
     * @param cron
     *            时间表达式 （如：0/5 * * * * ? ）
     * @param jobData
     *            参数
     */
    public void addJob(Class<? extends Job> jobClass, String jobName, String jobGroupName, String cron, Map jobData, Date endTime)
            throws SchedulerException {
        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类
        // 任务名称和组构成任务key
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        // 设置job参数
        if (jobData != null && jobData.size() > 0) {
            jobDetail.getJobDataMap().putAll(jobData);
        }
        // 定义调度触发规则
        // 使用cornTrigger规则
        // 触发器key
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();
        if(endTime!=null){
            trigger.getTriggerBuilder().endAt(endTime);
        }
        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停所有任务
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null){
            return;
        }
        scheduler.deleteJob(jobKey);
    }
 

 

}