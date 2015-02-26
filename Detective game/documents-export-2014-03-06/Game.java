import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room lockedRoom;
    private ArrayList<Item> inventory;
    private int maxWeight;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
       
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<Item>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university", null);
        theater = new Room("in a lecture theater", null);
        pub = new Room("in the campus pub", null);
        lab = new Room("in a computing lab", null);
        office = new Room("in the computing admin office,", "A 4 digit pin number is required to enter. Type ENTER followed by the pin");
        lockedRoom = office;
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        // create Items
        Item screwdriver, notebook, letter, weight;
        screwdriver = new Item("screwdriver", "A SCREWDRIVER lies on the desk. ",  "A cross-head screwdriver about 30cms long found on Ian's Desk", 150);
        notebook = new Item("notebook", "A NOTEBOOK containing paper and a pen is on the desk.", "A ball point pen and a blank notebook filled with sheets white A4 paper, found in Jimmy's Office", 100);
        letter = new Item("letter", "A LETTER from the police is on the desk.", "The letter reads:", 10);
        weight = new Item("weight", "A 400 gram WEIGHT is in the room.", "The weight is 400 grams:", 400);
        
        
        
        // add item to room
        lab.placeItem(screwdriver);
        office.placeItem(notebook);
        pub.placeItem(letter);
        theater.placeItem(weight);

        // set room to start game in
        currentRoom = outside;  
        maxWeight = 500;
    }
    
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
                
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        
        else if ( commandWord.equals("inventory")) {
            displayInventory();
            
        }
        else if ( commandWord.equals("inspect")) {
            inspect(command);
        }
        
        else if ( commandWord.equals("drop")) {
            dropItem(command);
        }
            
        else if ( commandWord.equals("enter")) {
            enterPin(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            System.out.println();
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if (nextRoom.getLock() == null) {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                System.out.println(getItemDiscriptions(currentRoom));
                System.out.println();
            }
            else {
                System.out.println(nextRoom.getLock());
            }
        }
    }
    
    private void takeItem(Command command) 
    {
        Item item ;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to take?");
            System.out.println();
            return;
        }

        String object = command.getSecondWord();

        
        // if object is in the room.
        
        if (currentRoom.getItemsString().contains(object)) {
            //pick up object
            item = currentRoom.removeItem(object);
            if(getInventoryWeight() + item.getWeight() <= maxWeight){
            
                inventory.add(item);
                System.out.println("You pick up the " + object + " and add it to your inventory");
                System.out.println();
            }
            else {
                currentRoom.placeItem(item);
                System.out.println("You can't pick up this item because it exceeds the weight limit.");
                System.out.println("To pick this item up, DROP another item, in your INVENTORY");
                System.out.println();
            }
        }
        
        else {
            System.out.println("That object is not in this room");
            System.out.println();
        }
    }
    
    
    
     private void dropItem(Command command) 
    {
        Item itemToBeRemoved = null;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to drop?");
            System.out.println();
            return;
        }

        String object = command.getSecondWord();

        
        // if object is in the inventory.
        
        if (getItemsString().contains(object)) {
            for(Item item : inventory) {
                if(item.getName().equals(object)){
                    itemToBeRemoved = item; 
                }
            }
               
            inventory.remove(itemToBeRemoved);
            currentRoom.placeItem(itemToBeRemoved);
            System.out.println("You place the " + object + " " + currentRoom.getShortDescription());
            System.out.println();
            
        }
        
        else {
            System.out.println("That object is not in your inventory");
            System.out.println();
        }
    }
    
    
    
    private String getItemDiscriptions(Room location)
    
    {
        ArrayList<Item> ItemsInRoom;
        ItemsInRoom = location.getItems();
        String itemsDiscription;
        itemsDiscription = "";
        
        for (Item item : ItemsInRoom) {
            itemsDiscription = itemsDiscription +item.getShortDiscription();
        }
        return itemsDiscription;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private void displayInventory() {
        System.out.println("You have the following items in your possession:");
        for( Item item : inventory) {
            System.out.println(item.getName()+ ", Weight: " + item.getWeight() +" grams");
            
        }
        System.out.println();
        System.out.println("You can carry a maximum of " + maxWeight + " grams");
        System.out.println("Total Weight: " + getInventoryWeight());
        System.out.println();
    }
    
    private int getInventoryWeight() {
        int totalWeight = 0;
        for(Item item : inventory) {
            totalWeight = totalWeight + item.getWeight();
        }
        return totalWeight;
    }
   
    
    private ArrayList getItemsString()
    {
        ArrayList<String> ItemStrings;
        ItemStrings = new ArrayList<String>(); 
        for (Item item : inventory) {
             ItemStrings.add(item.getName());
        }
        
        return ItemStrings;
    }
    
    private String getItemsLongDiscription(String object)
    {
        String discription;
        discription = "";
        for (Item item : inventory) {
            if (item.getName().equals(object)){
                discription = item.getLongDiscription();
            }
        }
        
        return discription;
    }
    
    
    private void inspect(Command command) {
                Item item ;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to inspect?");
            return;
        }

        String object = command.getSecondWord();

             
        //If item is is room
        if (currentRoom.getItemsString().contains(object)) {
            System.out.println(currentRoom.inspectItem(object));
            System.out.println();
        
        }
        //If item is in inventory
        else if (getItemsString().contains(object)) {
            System.out.println(getItemsLongDiscription(object));
            System.out.println();
        }
        
        else {
            System.out.println("That object is not in this room or in your inventory");
            System.out.println();
        }
    }
    
        private void enterPin(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Enter what?");
            System.out.println();
            return;
        }

        String pin = command.getSecondWord();

        // Try to leave current room.
        

        if(currentRoom.getShortDescription().equals("in a computing lab")) {
            if (pin.equals("2009")) {
                lockedRoom.unlock();
                System.out.println("The pin number was successful and the door is now unlocked");
            }
            else {
                System.out.println("The pin number was not correct");
            }
        }
        else {
                System.out.println("There is no where to enter that");
            }

    }
}

