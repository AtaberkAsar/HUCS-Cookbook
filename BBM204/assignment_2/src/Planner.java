import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();

        calculateCompatibility();
        System.out.println("Calculating max array\n" + "---------------------");
        calculateMaxWeight(taskArray.length - 1);
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        // YOUR CODE HERE

        Task task = this.taskArray[index];

        int lo = 0;
        int ptr = 0;
        Task taskPtr = null;

        while (index >= lo) {
            int mid = lo + (index - lo) / 2;
            Task temp = this.taskArray[mid];
            if (temp.getFinishTime().compareTo(task.getStartTime()) <= 0) {
                lo = mid + 1;
                ptr = mid;
                taskPtr = temp;
            }
            else
                index = mid - 1;
        }

        return taskPtr != null ? ptr : -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE

        for(int i = 0; i < compatibility.length; ++i)
            compatibility[i] = binarySearch(i);
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE

        solveDynamic(taskArray.length - 1);

        System.out.print("\nDynamic Schedule\n" + "----------------");
        for (Task task : planDynamic)
            System.out.print("\n" + task);

        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE

        if (i == -1)
            return;

        System.out.println("Called solveDynamic(" + i + ")");

        Task currentTask = taskArray[i];
        int compatibleIndex = compatibility[i];

        double res = maxWeight[i];
        if (i != 0 && res == maxWeight[i-1]) {
            solveDynamic(i - 1);
        }
        else {
            solveDynamic(compatibleIndex);
            planDynamic.add(currentTask);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE

        System.out.println("Called calculateMaxWeight(" + i + ")");

        if (i == -1)
            return 0.0;

        if (maxWeight[i] != null && i != 0) // TODO CHECK WHY i != 0 NEEDED FOR ?
            return maxWeight[i];

        Task currentTask = taskArray[i];
        int compatibleIndex = compatibility[i];

        double case0 = calculateMaxWeight(compatibleIndex) + currentTask.getWeight();
        double case1 = calculateMaxWeight(i - 1);

        double w = Math.max(case0, case1);
        maxWeight[i] = w;
        return w;
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE

        planGreedy.add(taskArray[0]);
        int recentSelection = 0;
        for (int i = 1; i < taskArray.length; ++i)
            if(compatibility[i] >= recentSelection) {
                planGreedy.add(taskArray[i]);
                recentSelection = i;
            }

        System.out.print("\n\nGreedy Schedule\n" + "---------------");
        for(Task task : planGreedy)
            System.out.print("\n" + task);

        return planGreedy;
    }
}
