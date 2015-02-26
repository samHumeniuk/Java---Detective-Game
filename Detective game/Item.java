
/**
 * This class represents items in the game are present in room and can picked up by the player.
 * Items can also be inspected, dropped and used. 
 * 
 * @author (UP695745) 
 * @version 
 */
public class Item
{
    private String name;
    private String shortDiscription;
    private String longDiscription;
    private int weight;
    private boolean visibility;

    

    /**
     * Constructor for objects of class Item
     * Assigns the item's parameters to the coresponding instance variables.
     */

    public Item(String name, String shortDiscription, String longDiscription, int weight, boolean visibility)
    {


        this.name = name;
        this.shortDiscription = shortDiscription;
        this.longDiscription = longDiscription;
        this.weight = weight;
        this.visibility = visibility;
        
    }
    
    /**
     * Returns the name of the item.
     *@return the name of the item. 
     */
    
    public String getName() {
        return name;
    }
    
     /**
     * Returns the item's discription.
     *@return the item's discription. 
     */
    public String getShortDiscription() {
        return shortDiscription;
    }
    
     /**
     * Returns the discription to be given when the item is inspected.
     *@return the item's long discription. 
     */
    public String getLongDiscription() {
        return longDiscription;
    }
    
     /**
     * Sets the items Long discription to the specified String.
     *@param Requires the String discription. 
     */
    public void setLongDiscription(String discription) {
        this.longDiscription = discription;
    }
    
     /**
     * Returns the weight of the current Item
     *@return the item's weight. 
     */
    public int getWeight(){
        return weight;
    }
    
     /**
     * Returns the true if the item will be displayed in the room it is in.
     *@return the item's visibility. 
     */
    public boolean getVisibility(){
        return visibility;
    }
    
}

