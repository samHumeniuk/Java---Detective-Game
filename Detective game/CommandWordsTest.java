

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CommandWordsTest.
 *
 * @author  (UP695745)
 * @version (a version number or a date)
 */

public class CommandWordsTest
{
    CommandWords commandW1;
    
    /**
     * Default constructor for test class CommandWordsTest
     */
    public CommandWordsTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        commandW1 = new CommandWords();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Tests that the valid commands are all recognised. 
     */
    @Test
    public void TestValidCommands()
    {
        
        assertEquals(true, commandW1.isCommand("go"));
        assertEquals(true, commandW1.isCommand("help"));
        assertEquals(true, commandW1.isCommand("quit"));
        assertEquals(true, commandW1.isCommand("take"));
        assertEquals(true, commandW1.isCommand("inspect"));
        assertEquals(true, commandW1.isCommand("drop"));
        assertEquals(true, commandW1.isCommand("enter"));
        assertEquals(true, commandW1.isCommand("inventory"));
        assertEquals(true, commandW1.isCommand("back"));
        assertEquals(true, commandW1.isCommand("use"));
        assertEquals(false, commandW1.isCommand("A non-command string"));
    }
}

