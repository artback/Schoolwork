import org.testng.annotations.Test;

/**
 * Created by freak on 6/21/17.
 */
public class ShellSortTest {
    @Test
    public void shellsorttestzeroloops() throws Exception {
        ShellSort shellsort = new ShellSort();
        Object[] testcase = {0};
        shellsort.Shellsort(testcase);
    }
    @Test
    public void shellSortTestLoopOne() throws Exception {
        ShellSort shellsort = new ShellSort();
        Object[] testCase = {1};
        shellsort.Shellsort(testCase);
    }
    @Test
    public void shellSortTestSorted() throws Exception {
        ShellSort shellsort = new ShellSort();
        Object[] testCase = {0,1};
        shellsort.Shellsort(testCase);
    }
    @Test
    public void shellSortTestUnsorted() throws Exception {
        ShellSort shellsort = new ShellSort();
        Object[] testCase = {"Makaroner","Och","Falukorv"};
        shellsort.Shellsort(testCase);
    }


}
