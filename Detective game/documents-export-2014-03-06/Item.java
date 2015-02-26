
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private String shortDiscription;
    private String longDiscription;
    private int weight;

    

    /**
     * Constructor for objects of class Item
     */

    public Item(String name, String shortDiscription, String longDiscription, int weight )
    {


        this.name = name;
        this.shortDiscription = shortDiscription;
        this.longDiscription = longDiscription;
        this.weight = weight;
        
    }
    
    public String getName() {
        return name;
    }
    
    public String getShortDiscription() {
        return shortDiscription;
    }
    
    public String getLongDiscription() {
        return longDiscription;
    }
    
    public int getWeight(){
        return weight;
    }
    
}

