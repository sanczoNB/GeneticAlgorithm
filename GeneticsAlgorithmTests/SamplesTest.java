import junit.framework.Assert;

import static org.junit.Assert.*;

/**
 * Created by sanczo on 2016-02-20.
 */
public class SamplesTest {

    @org.junit.Test
    public void testAdd()  {
        //Arrange
        int firstAddend  =  5;

        int secondAddend  = 3;

        int sum;

        //Action

        sum = Samples.add(firstAddend, secondAddend);

        //Assert

        Assert.assertEquals(8,sum);

    }
}