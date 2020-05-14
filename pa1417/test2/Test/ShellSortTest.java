import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by freak on 6/21/17.
 */
public class ShellSortTest {
    @Test
    public void shellSort() throws Exception {
    ShellSort shellSort = new ShellSort();
        Object[] testCase1 = {1};
        Object[] testCase2 = {1,0};
        shellSort.ShellSort(testCase2);
    }

}