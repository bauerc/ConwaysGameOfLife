import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Cameron Bauer on 12/5/16.
 */
public class ConwaysUnitTests {

    @Test
    public void testUniverseReader() {

        //Test case, account for padding
        boolean[][] expectedUniverse = {{false,false,false,false,false},
                {false,false,true,false,false},
                {false,true,true,true,false},
                {false,false,true,false,false},
                {false,false,false,false,false}};

        ConwaysUniverse testUniverse = ConwaysUniverseReader.readUniverseStateFromFile("src/test.txt");

        Assert.assertArrayEquals(expectedUniverse, testUniverse.getCurrentUniverse());
    }


    @Test
    public void testGenerateNextUniverse() throws Exception {

        //Test case, account for padding
        // Basic spinner
        boolean[][] expectedUniverse = {{false,false,false,false,false},
                {false,false,true,false,false},
                {false,false,true,false,false},
                {false,false,true,false,false},
                {false,false,false,false,false}};

        //Test case, account for padding
        boolean[][] inputUniverse = {{false,false,false},
                {true,true,true},
                {false,false,false}};

        ConwaysUniverse testUniverse = new ConwaysUniverse(inputUniverse);
        testUniverse.generateNextUniverse();

        Assert.assertArrayEquals(expectedUniverse, testUniverse.getCurrentUniverse());
    }

    @Test
    public void testLookupTable() throws Exception {

        ConwaysUniverse testUniverse = new ConwaysUniverse(new boolean[1][1]);
        boolean[] lookupTable = testUniverse.getLookupTable();

        // Rule 1: live cell < 2 live neighbors = dead
        int gameOfLifeRule1 = Integer.parseInt("000010000",2);
        // Rule 2: live cell, 2 or 3 live neighbors = alive
        int gameOfLifeRule2 = Integer.parseInt("000111000",2);
        int gameOfLifeRule2b = Integer.parseInt("000111100",2);
        // Rule 3: live cell > 3 live neighbors = dead
        int gameOfLifeRule3 = Integer.parseInt("111010111",2);
        // Rule 1: dead cell, 3 live neighbors = alive
        int gameOfLifeRule4 = Integer.parseInt("100101000",2);

        // Rule 1 - Expect false
        assert !lookupTable[gameOfLifeRule1];
        // Rule 2 - Expect true
        assert lookupTable[gameOfLifeRule2];
        assert lookupTable[gameOfLifeRule2b];
        // Rule 3 - Expect false
        assert !lookupTable[gameOfLifeRule3];
        // Rule 4 - Expect true
        assert lookupTable[gameOfLifeRule4];




    }
}
