package com.class7no.springbootquartzapi.config;

import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz配置类
 */
@Configuration
public class QuartzConfig {

    /**
     *  定时任务工厂
     * @return
     */
    @Bean(value = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        return schedulerFactoryBean;
    }

    /**
     * 初始化监听器
     * @return
     */
    @Bean
    public QuartzInitializerListener quartzInitializerListener(){

        return new QuartzInitializerListener();
    }

    /**
     * 通过SchedulerFactoryBean 获取Scheduler实例 也是触发调度的第一步
     * @return
     */
    @Bean
    public Scheduler scheduler(){
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        //第一个任务调度
        addmyTestJob(scheduler);
        //第二个任务调度
        add2(scheduler);
        return scheduler;
    }

    /**
     * 新增一个定时任务（测试）设置调度时间等
     * @param scheduler
     * @author sujin
     */
    private void addmyTestJob(Scheduler scheduler) {
        String startJob = "true";//是否开始
        String jobName = "DATAMART_SYNC";
        String jobGroup = "DATAMART_SYNC";
        String cron = "0 0/1 * * * ?";//定时的时间设置
        String className = MyTest.class.getName();
        if (startJob != null && startJob.equals("true")) {
            addCommonCronJob(jobName, jobGroup, cron, scheduler, className);
        } else {
            deleteCommonJob(jobName, jobGroup, scheduler);
        }
    }

    /**
     * 添加第二个调度测试。。。。
     */
    private void add2(Scheduler scheduler){
        String startJob = "true";//是否开始
        String jobName = "test";
        String jobGroup = "test";
        String cron = "0 0/2 * * * ?";//定时的时间设置
        String className = MyTest2.class.getName();
        if (startJob != null && startJob.equals("true")) {
            addCommonCronJob(jobName, jobGroup, cron, scheduler, className);
        } else {
            deleteCommonJob(jobName, jobGroup, scheduler);
        }
    }




        //end...

    /**
     * 删除定时任务方法
     * @param jobName
     * @param jobGroup
     * @param scheduler
     */
    private void deleteCommonJob(String jobName, String jobGroup, Scheduler scheduler) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);//先暂停任务
            scheduler.deleteJob(jobKey);//再删除任务
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一些定时任务，如日志清理任务
     */
    private void addCommonCronJob(String jobName, String jobGroup, String cron, Scheduler scheduler, String
        className) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            //任务触发
            Trigger checkExist = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (checkExist == null) {
                JobDetail jobDetail = null;
                jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(className))
                    .requestRecovery(true)//当Quartz服务被中止后，再次启动或集群中其他机器接手任务时会尝试恢复执行之前未完成的所有任务
                    .withIdentity(jobName, jobGroup)
                    .build();
                jobDetail.getJobDataMap().put("jobName", jobName);
                jobDetail.getJobDataMap().put("jobGroup", jobGroup);
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
                /*withMisfireHandlingInstructionDoNothing
                ——不触发立即执行
                ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
                withMisfireHandlingInstructionIgnoreMisfires
                ——以错过的第一个频率时间立刻开始执行
                ——重做错过的所有频率周期后
                ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
                withMisfireHandlingInstructionFireAndProceed
                ——以当前时间为触发频率立刻触发一次执行
                ——然后按照Cron频率依次执行*/
                Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroup)
                    .withSchedule(cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires())
                    .build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
                addCommonCronJob(jobName, jobGroup, cron, scheduler, className);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
