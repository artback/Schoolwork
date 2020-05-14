import org.junit.Test;

import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by freak on 6/3/17.
 */
public class findValuesTest {
    @Test
    public void findLast() throws Exception {
        findValues findValues = new findValues();
        int[] testValues = {4,3,4,1,7,2,4,6,3};
        int result = findValues.findLast(testValues,4);
        assertEquals(6,result);

    }

    @Test
    public void lastZero() throws Exception {
        findValues findValues = new findValues();
        int[] testValues = {0,1,5,0,3,0,2};
        int[] testValues2 = {1,1,5,1,3,0,2};
        int result = findValues.lastZero(testValues2);
        assertEquals(5,result);
    }

    @Test
    public void countPositive() throws Exception {
        findValues findValues = new findValues();
        int[] testValues = {-0,-1,5,0,-3,-5,3};
        int result = findValues.countPositive(testValues);
        assertEquals(4,result);

    }

    @Test
    public void oddOrPos() throws Exception {
        findValues findValues = new findValues();
        int[] testValues = {0,-1,1,2,-3,-5};
        int[] testValues2 = {0,-1,1,2,3,5};
        int result = findValues.oddOrPos(testValues2);
        assertEquals(4,result);

    }

}