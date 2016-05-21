package com.alforsconsulting.pizzastore.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by palfors on 5/18/16.
 */
public class ForkJoinPoolDemo {
    private static final Logger logger = LogManager.getLogger();


    // amount of time to sleep between creation of orders
    private static long sleepTime = 0;
    // the maximum number of orders to create
    private static int maxOrders = 10;
    // the number of threads to spawn
    private static int threadCount = 5;

    public static void main(String[] args) {
        if (args != null) {
            for (String arg : args) {
                processArgument(arg);
            }
        }

        ForkJoinPoolDemo loader = new ForkJoinPoolDemo();
        //loader.loadData(threadCount);
        loader.processScores(5);

        System.out.println("Done!");
    }

    private static void processArgument(String arg) {
        if (arg != null) {
            String[] split = arg.split("=");
            if (split.length == 2) {
                String key = split[0];
                String value = split[1];

                switch (key) {
                    case "sleep" :
                        sleepTime=Long.parseLong(value);
                        System.out.println("sleep: [" + sleepTime + "]");
                        break;
                    case "orders" :
                        maxOrders=Integer.parseInt(value);
                        System.out.println("orders: [" + maxOrders + "]");
                        break;
                    case "threads" :
                        threadCount=Integer.parseInt(value);
                        System.out.println("threads: [" + threadCount + "]");
                        break;
                    default :
                        System.out.println("Invalid argument [" + arg + "]");
                        break;
                }
            }
            else
            {
                System.out.println("Invalid argument [" + arg + "]");
            }
        }
    }

    private void processScores(int threadCount) {
        int[] scores = generateScores(10);


        ForkJoinPool pool = new ForkJoinPool(threadCount);
//        pool.invoke(new MeanScoreAction(scores));
        pool.invoke(new SumValueTask<Integer>(scores));

    }

    private int[] generateScores(int count) {
        int[] scores = new int[count];
        int i = 0;
        int value = 0;
        int total = 0;
        StringBuilder builder = new StringBuilder("generated scores: ");
        while (i < count) {
            value = new Double(100*Math.random()).intValue();
            scores[i] = value;
            builder.append("[").append(value).append("]");
            i++;
            total += value;
        }
        builder.append(" total [").append(total).append("]");
        System.out.println(builder.toString());
        return scores;
    }

    private class SumValueTask<T> extends RecursiveTask {
        private int[] scores;
        private int sum;
        private double instanceId = 100*Math.random();

        public SumValueTask(int[] scores) {
            this.scores = scores;
        }

        @Override
        protected Object compute() {
            System.out.println(this + ": compute: scores: " + listArrayContents(scores));
            int maxItems = 4;
            if (scores.length <= maxItems) {
                sum = calculateSum(this.toString(), scores);
                System.out.println(this + ": compute: calculated sum: " + sum);
            } else {
                // split the array
                int[] scores1 = Arrays.copyOf(scores, maxItems);
                System.out.println(this + ": compute: scores1: " + listArrayContents(scores1));
                int[] scores2 = Arrays.copyOfRange(scores, maxItems, scores.length);
                System.out.println(this + ": compute: scores2: " + listArrayContents(scores2));

                SumValueTask<Integer> task1 = new SumValueTask<Integer>(scores1);
                SumValueTask<Integer> task2 = new SumValueTask<Integer>(scores2);

                task1.fork();
                Integer sum1 = (Integer) task2.compute();
                Integer sum2 = (Integer) task1.join();

                sum = sum1 + sum2;

                System.out.println(this + ": merged sum: " + sum + " from [" + task1 + ": result: " + sum1 + "][" + task2 + ": result: " + sum2 + "]");
            }

            return sum;
        }

            public String toString() {
                return "MeanScoreTask id: " + new Double(this.instanceId).intValue();
            }


    }

    private class MeanScoreAction extends RecursiveAction {

        private int[] scores;
        private int mean = 0;
        private double instanceId = 100*Math.random();

        public MeanScoreAction(int[] scores) {
            this.scores = scores;
        }

        @Override
        protected void compute() {
            System.out.println(this + ": compute: scores: " + listArrayContents(scores));
            int maxItems = 4;
            if (scores.length <= maxItems) {
                mean = calculateMean(this.toString(), scores);
                System.out.println(this + ": compute: mean: " + mean);
            } else {
                // split the array
                int[] scores1 = Arrays.copyOf(scores, maxItems);
                System.out.println(this + ": compute: scores1: " + listArrayContents(scores1));
                int[] scores2 = Arrays.copyOfRange(scores, maxItems, scores.length);
                System.out.println(this + ": compute: scores2: " + listArrayContents(scores2));

                invokeAll(new MeanScoreAction(scores1),
                        new MeanScoreAction(scores2));
            }

            System.out.println(this + "- finish computed mean [" + mean + "] for [" + this + "]");

        }

        public String toString() {
            return "MeanScoreAction id: " + new Double(this.instanceId).intValue();
        }
    }

    private int calculateMean(String worker, int[] scores) {
        System.out.println(worker + ": calculateMean: " + listArrayContents(scores));
        int total = 0;
        int mean = 0;
        if (scores != null & scores.length > 0) {
            for (int i : scores) {
                total += i;
            }
            mean = new Double(total/scores.length).intValue();
            System.out.println(worker + ": mean [" + mean + "]");
        } else {
            System.out.println(worker + ": calculateMean - has Empty array!");
        }
        return mean;
    }

    private int calculateSum(String worker, int[] scores) {
        System.out.println(worker + ": calculateSum: " + listArrayContents(scores));
        int total = 0;
        if (scores != null & scores.length > 0) {
            for (int i : scores) {
                total += i;
            }
            System.out.println(worker + ": sum [" + total + "]");
        } else {
            System.out.println(worker + ": calculateSum - has Empty array!");
        }
        return total;
    }

    private String listArrayContents(int[] array) {
        StringBuilder builder = new StringBuilder();
        if (array != null) {
            if (array.length > 0) {
                for (int i : array) {
                    builder.append("[").append(i).append("]");
                }
            } else {
                builder.append("array is empty!");}
        } else {
            builder.append("array is null!");
        }
        return builder.toString();
    }

}
