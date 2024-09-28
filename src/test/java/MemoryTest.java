//---------------------------------------
//	IMPORTS
//---------------------------------------
import java.util.ArrayList;
//--------------------------------------------------
//	CLASS MemoryTest
//--------------------------------------------------
/**
 * This class models a memory tester, to test memory usage and performance.<br>
 * This class tests stack memory and heap memory.
 */
public class MemoryTest {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private static int numRecursions = 0;
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the number of recursions that has occured.
     * @return The number of recursions that has occured.
     */
    public static int getNumRecursions() {
        return numRecursions;
    }
    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Sets the number of recursions.
     * @param amount The amount to set numRecursions to.
     */
    public static void setNumRecursions(int amount) {
        numRecursions = amount;
    }
    //---------------------------------------
    //  EXTRA METHODS
    //---------------------------------------
    /**
     * Calls itself infinitely, incrementing numRecursions by 1 each recursion.
     */
    private static void infiniteRecursion() {
        numRecursions++;
        infiniteRecursion();
    }

    /**
     * Tests the stack.<br>
     * Prints how long it takes and how many recursions it takes
     * for a StackOverflowError to occur.
     */
    public static void stackTest() {
        long startTime = System.nanoTime(), endTime;
        double timeTaken;
        try {
            infiniteRecursion();
        } catch (StackOverflowError e) {
            endTime = System.nanoTime();
            timeTaken = (endTime - startTime) / 1e9;
            System.out.println("Error! The Stack is full.\n");
            System.out.println("- Informational Table -");
            System.out.println("|   Time taken: ");
            System.out.println("| " + timeTaken + "s");
            System.out.println("|   Number of Recursions:");
            System.out.println("| " + numRecursions);
        }
    }

    /**
     * Tests the heap.<br>
     * Prints how long it takes and how many object creations it takes for a
     * OutOfMemoryError to occur. <br>
     * It also prints how long and how many objects it takes for object creation to slow down.
     */
    public static void heapTest() {
        // INIT VARS ------
        long startTime = System.nanoTime(), endTime, objectStart;
        int objectsCreated = 0, slowdownStartNumObjects = -1;
        ArrayList<Object> objects = new ArrayList<>();
        double timeTaken = Double.MAX_VALUE, slowdownStartTime = -1;
        boolean slowdown = false;
        // ----------------
        try {
            // Creates objects and adds them to an array list until
            // an OutOfMemory exception occurs
            while (true) {
                objectStart = System.nanoTime();
                objects.add(new Object());
                endTime = System.nanoTime();
                objectsCreated++;
                // slowdown is checked first, since it is cheaper to check
                // and the chance of it being true are high, meaning we do not need to check the equation
                if (!(slowdown) && endTime - objectStart > timeTaken) {
                    slowdownStartTime = (endTime - objectStart);
                    slowdownStartNumObjects = objectsCreated;
                    slowdown = true;
                }
                timeTaken = endTime - objectStart;
            }
        } catch (OutOfMemoryError e) {
            endTime = System.nanoTime();
            timeTaken = (endTime - startTime) / 1e9;
            System.out.println("Error! The Heap is full.\n");
            System.out.println("- Informational Table -");
            System.out.println("|   Time taken: ");
            System.out.println("| " + timeTaken + "s");
            System.out.println("|   Total objects created: ");
            System.out.println("| " + objectsCreated);
            System.out.println("|   Slowdown started after: ");
            System.out.println("| " + slowdownStartTime + "ns | Objects created: " + slowdownStartNumObjects);
        }
    }
    /**
     * The entrypoint for the Application.
     */
    public static void main(String[] args) {
        int choice = 2;
        switch (choice) {
            case 1:
                stackTest();
                break;
            case 2:
                heapTest();
                break;
        }
    }

}

