import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * Rooms can contain items and people which can be interacted with. Rooms  
 * can also be locked.
 * @author  UP695745 and UP663678
 * @version
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private ArrayList<Person> people;
    private String locked; 

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param locked A discription of the lock of the room. If they is 
     * no lock then null is used. 
     */
    public Room(String description, String locked) 
    {
        this.description = description;
        this.locked = locked;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>() ;
        people = new ArrayList<Person>() ;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /** 
     * Adds an item to the room.
     * @param itemName the item to be added.
    */ 
    public void placeItem(Item itemName)
    {
        items.add(itemName);
    }
    
    /** 
     * Adds a person to the room.
     * @param Name the person to be added.
     */ 
    public void placePerson(Person name)
    {
        people.add(name);
    }
    
     /** 
     * Deletes an item from the room.
     * @param itemName the item to be removed.
     * @return the item that has been removed.
     */ 
    public Item removeItem(String itemName)
    {
        Item itemToRemove;
        itemToRemove = null;
        for(Item item : items){
            if (itemName.equals(item.getName())){
                itemToRemove = item;
            }

        }
        items.remove(itemToRemove);
        return itemToRemove;
    }
    
    
    /** 
     * Deletes a person from the room.
     * @param Name the person to be removed.
     * @return the person that has been removed.
     */ 
        public Person removePerson(String name)
    {
        Person personToRemove;
        personToRemove = null;
        for(Person person : people){
            if (name.equals(person.getName())){
                personToRemove = person;
            }

        }
        people.remove(personToRemove);
        return personToRemove;
    }
    
     /** 
     * Inspects an item.
     * @param itemName the item to be inspected.
     * @return the long description of an item in the room.
     */ 
    public String inspectItem(String itemName)
    {
        Item itemToInspect;
        itemToInspect = null;
        for(Item item : items){
            if (itemName.equals(item.getName())){
                itemToInspect = item;
            }

        }
        return itemToInspect.getLongDiscription();
    }
    
    /**
     * Inspects an person.
     * @param personName the item to be inspected.
     * @return the long description of the person in the room.
     */
    public String inspectPerson(String personName)
    {
        Person personToInspect;
        personToInspect = null;
        for(Person person : people){
            if (personName.equals(person.getName())){
                personToInspect = person;
            }

        }
        return personToInspect.getDescription();
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * @return the list of items that are in the current room.
    */
    public ArrayList getItems()
    {
        return items;
    }
    
    /**
     * @return the list of items that are in the current room.
    */
    public ArrayList<Person> getPeople()
    {
        return people;
    }
    
     /**
     * @return the lock on the current room. Returns null if there is no lock.
     */
    public String getLock() {
        return locked;
    }
    
    /**
     * @return an arraylist containing the each item's name as a string.
     */
    public ArrayList getItemsString()
    {
        ArrayList<String> ItemStrings;
        ItemStrings = new ArrayList<String>(); 
        for (Item item : items) {
             ItemStrings.add(item.getName());
        }
        
        return ItemStrings;
    }
   
    /**
     * @return an arraylist containing the each person's name as a string.
     */    
    public ArrayList getPeopleString()
    {
        ArrayList<String> peopleStrings;
        peopleStrings = new ArrayList<String>(); 
        for (Person person : people) {
             peopleStrings.add(person.getName());
        }
        
        return peopleStrings;
    }
    /**
     * Unlocks the current room if it is not already unlocked.
     */  
    public void unlock() {
        locked = null;
    }
        
}

