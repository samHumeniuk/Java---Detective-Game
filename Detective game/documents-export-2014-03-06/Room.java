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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private String locked; 

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String locked) 
    {
        this.description = description;
        this.locked = locked;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>() ;
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
    
    public void placeItem(Item itemName)
    {
        items.add(itemName);
    }
    
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
    
    public ArrayList getItems()
    {
        return items;
    }
    
    public String getLock() {
        return locked;
    }
    
    public ArrayList getItemsString()
    {
        ArrayList<String> ItemStrings;
        ItemStrings = new ArrayList<String>(); 
        for (Item item : items) {
             ItemStrings.add(item.getName());
        }
        
        return ItemStrings;
    }
    
    public void unlock() {
        locked = null;
    }
        
}

