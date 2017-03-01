package br.com.intelie.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.intelie.model.Job;
import it.sauronsoftware.cron4j.Scheduler;

public class Jobs {

	private static Jobs instance;

	private Set<Job> jobs = new HashSet<Job>();
	private Map<String, String> jobIds = new HashMap<String, String>();

	private Scheduler scheduler;

	private Jobs() {
		scheduler = new Scheduler();
		scheduler.start();
	}

	public static Jobs getInstance() {
		if (instance == null) {
			instance = new Jobs();
		}
		return instance;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public Collection<Job> getJobs() {
		return jobs;
	}

	public void addJob(final Job job) {
		if (jobs.add(job)) {
			String jobId = scheduler.schedule(job.getCron(), new Runnable() {
				@Override
				public void run() {
					System.out.println(job.getMsg());
				}
			});
			jobIds.put(job.getName(), jobId);
		}
	}

	public void deleteJob(String jobName) {
		Job o = new Job();
		o.setName(jobName);
		if (jobs.remove(o)) {
			String jobId = jobIds.remove(jobName);
			scheduler.deschedule(jobId);
		}
	}

}
