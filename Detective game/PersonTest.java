

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PersonTest.
 *
 * @author  (UP695745)
 * @version (a version number or a date)
 */
public class PersonTest

{
    Person person1;
    
    /**
     * Default constructor for test class PersonTest
     */
    
    public PersonTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     *Creates a new person and sets two questions and responces. 
     * Called before every test case method.
     */
    @Before //
    public void setUp()
    {
        person1 = new Person("jimmy", "JIMMY is in the Room");
        person1.SetConversation("Is your name Jimmy?", "Yes");
        person1.SetConversation("Did you kill Ian?", "No");
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
     * Checks the person contains the correct description and name.
     */
    @Test
    public void testPerson()
    {
        
        assertEquals("jimmy", person1.getName());
        assertEquals("JIMMY is in the Room", person1.getDescription());
    }

    /**
     * Checks that the are the correct number of questions.
     */
    @Test
    public void CheckQuestions()
    {
        assertEquals(2, person1.getNumberOfQuestions());
    }

    /**
     * Checks that askingthe questions returns the correct responces.
     */
    @Test
    public void askQuestions()
    {
        assertEquals("Yes", person1.askQuestion(1));
        assertEquals("No", person1.askQuestion(2));
    }

}




