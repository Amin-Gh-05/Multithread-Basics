package sbu.cs;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        public int getProcessingTime() {
            return processingTime;
        }

        public String getTaskName() {
            return taskName;
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        tasks.sort(Comparator.comparing(Task::getProcessingTime));
        ArrayList<String> finishedTasks = new ArrayList<>();

        for (Task task : tasks) {
            finishedTasks.add(task.getTaskName());
        }

        return finishedTasks;
    }

    public static void main(String[] args) {

    }
}
