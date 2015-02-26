

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RoomTest.
 *
 * @author  (UP695745)
 * @version (a version number or a date)
 */
public class RoomTest
{
    
    Room room1;
    Room room2;
    Person Jimmy;

    
    /**
     * Default constructor for test class RoomTest
     */
    public RoomTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     * Creates two new rooms and sets the exits.
     * Also places an item in the room and a person. 
     */
    @Before
    public void setUp()
    {
        room1 = new Room("outside", "A key is needed to unlock this door.");
        room2 = new Room("inside", null);
        room1.setExit("south", room2);
        room2.setExit("north", room1);
        Item weight = new Item("weight", "A weight is in the room", "The weight weighs 400 grams", 400, true);
        room1.placeItem(weight);
        Jimmy = new Person("Jimmy", "Jimmy is in the room");
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
     * Checks that the exits to each room have been set.
     */
    @Test
    public void SetExit()
    {

        assertEquals(room2, room1.getExit("south"));
        assertEquals(room1, room2.getExit("north"));
    }

    /**
     * Checks that items have been assigned to the rooms inventory
     */
    @Test
    public void Items()
    {
        
        assertEquals("The weight weighs 400 grams", room1.inspectItem("weight"));
    }

    /**
     * Checks that the person has ben assigned to the list of people.
     */
    @Test
    public void People()
    {
        room1.placePerson(Jimmy);
        assertEquals("Jimmy is in the room", room1.inspectPerson("Jimmy"));
    }

    @Test
    /**
     * Checks that the lock functions properly and the room can be unlocked.
     */
    public void Locks()
    {
        assertEquals("A key is needed to unlock this door.", room1.getLock());
        room1.unlock();
        assertNull(room1.getLock());
    }
}




