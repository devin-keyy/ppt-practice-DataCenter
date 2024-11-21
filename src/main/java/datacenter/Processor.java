package datacenter;

import java.util.List;
import java.util.ArrayList;

public class Processor {

    /*
        Abstraction Function:
            this represents a processor with time capacity this.timeLimit
            and holds the jobs in this.jobs
        Representation Invariant:
            jobs != null && jobs does not contain nulls
            && timeLimit > 0
            && (there should be something more that you should think about)

            memory usage = the highest value of job.getMemoryUsage()

     */

    private List<Job> jobs;
    private int timeLimit;
    private int memoryUsage = 0; // initialize to 0

    /**
     * Create a new empty processor
     * @param timeLimit the limit on compute time on this processor, > 0
     */
    public Processor(int timeLimit) {
        this.timeLimit = timeLimit;
        this.jobs = new ArrayList<Job>();
    }

    /**
     * Check if a given job can fit in this processor
     *
     * @return true if adding the job does not exceed the time limit on this processor, and false otherwise.
     */
    public boolean canFitJob(Job job) {
        int timeCount = 0;
        for (Job currentJob : jobs) {
            timeCount += currentJob.getExecutionTime();
        }

        if (timeCount + job.getExecutionTime() > this.timeLimit) {
            return false;
        }

        return true;
    }

    /** Inserts a job to the processor, at the end of its schedule
     *
     * @param job not null
     * @return true if the job can fit on this processor and was assigned, and false otherwise
     */
    public boolean addJob(Job job) {
        if (!canFitJob(job)) {
            return false;
        }

        jobs.add(job);

        // updates the memory usage
        if (job.getMemoryUsage() > this.memoryUsage) {
            this.memoryUsage = job.getMemoryUsage();
        }

        return true;
    }

    /** Get the peak memory usage of this processor
     *
     * @return the peak memory usage of the jobs ossigned to this processor
     * */
    public int getPeakMemoryUsage() {
        return this.memoryUsage;
    }

    /** Get the total computation (execution) time of this processor
     *
     * @return the total computation (execution) time of jobs assigned
     * to this processor
     */
    public int getTotalComputationTime() {
        int totalExecutionTime = 0;
        for (Job currentJob : jobs) {
            totalExecutionTime += currentJob.getExecutionTime();
        }
        return totalExecutionTime;
    }

    /** Check if this processor is equal to a given processor
     *
     * @return true if both processors have exactly the same jobs,
     * in the same order, and they have the same time limit
     */
    public boolean equals(Processor that) {

        // comparing to itself
        if (this == that) {
            return true;
        }

        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        Processor other = (Processor) that;

        if (this.jobs.size() != that.jobs.size()) {
            return false;
        }

        for (int i = 0; i < jobs.size(); i++) {
            Job thisJob = this.jobs.get(i);
            Job thatjob = that.jobs.get(i);

            if(!thisJob.equals(thatjob)) {
                return false;
            }
        }

        if (this.timeLimit != that.timeLimit) {
            return false;
        }

        if (this.memoryUsage != that.memoryUsage) {
            return false;
        }

        return true;
    }

    /** Get the time limit of this processor
     *
     * @return the time limit on this processor
     */
    public int getTimeLimit() {
        return this.timeLimit;
    }

    /**
     * Obtain the jobs scheduled on this processor, in scheduled order
     * @return the jobs scheduled on this processor, in scheduled order
     */
    public Job[] getJobs() {
        int JOBSIZE = jobs.size();
        Job [] jobsArray = new Job[JOBSIZE];
        for (int i = 0; i < jobs.size(); i++) {
            jobsArray[i] = jobs.get(i);
        }
        return jobsArray;
    }
}