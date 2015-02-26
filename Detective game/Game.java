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
 * @author  Michael Kölling and David J. Barnes edited by UP695745, UP663678, UP688841.
 * @version 2011.08.08
 */

public class Game 
{
    final int maxWeight = 5000;
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Item> inventory; 
    private ArrayList<Room> previousRooms;
    private boolean questioning;
    private Person currentPerson;
    private Boolean stageOneComplete = false;
    private Boolean stageTwoComplete = false;
    private Boolean stageThreeComplete = false;
    private Boolean stageFourComplete = false;
    private Boolean stageFiveComplete = false;
    private Boolean stageSixComplete = false;
    private Boolean stageSevenComplete = false;
    private Boolean stageEightComplete = false;
    boolean wantToQuit = false;
    
    //define rooms
    Room outside, reception, staffRoom, office, securityRoom, iansOffice, jimmysOffice, borissOffice, albertsOffice, hall, kitchen, frontRoom, study, policeStation ;
    //define Items
    Item desk, computer, envelope, screwdriver, notebook, letter, drawer, safe, shirt, key, door, photograph, laptop;
    //define characters
    Person jimmy, albert, boris, linda, officer;
    
    
    /**
     * Create the game and initialise its internal map and the list of rooms and inventory.
     */
    public Game() 
    {
       
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<Item>();
        previousRooms = new ArrayList<Room>();
        
    }
   
    /**
     * Create all the rooms and link their exits together. This part is critical as it is the set up of the game.
     * Creates the people and items and sets the rooms they are in.
     * Creates the speach for the characters.
     */

    private void createRooms()
    {
        
        // create the rooms in the Offices
        outside = new Room("outside the main entrance of the building.", null);
        reception = new Room("in main reception of the building.", null);
        staffRoom = new Room("in the staff room.", null);
        office = new Room("in the general office.", null);   
        securityRoom = new Room("in the security room.", "A 4 digit pin number is required to enter the security room. Type ENTER followed by the pin.");
        iansOffice = new Room("in Ian's office. Ian's corpse has been removed by forensics, but the blood stains remain on the carpet.", null);
        jimmysOffice = new Room("in Jimmy's office. Jimmy's office contains many pictures of himself with Ian.", null);
        borissOffice = new Room("in Boris's office.", "The door won't open.");
        albertsOffice = new Room("in Albert's offcie.", "The door is locked, a key is required to enter. " );
        

        // initialise room exits
        outside.setExit("north", reception);
        reception.setExit("south", outside);
        reception.setExit("east", staffRoom);
        reception.setExit("west", office);
        reception.setExit("north", iansOffice);      
        iansOffice.setExit("south", reception);        
        staffRoom.setExit("west", reception);
        staffRoom.setExit("north", borissOffice);
        staffRoom.setExit("south", albertsOffice);        
        albertsOffice.setExit("north", staffRoom);        
        borissOffice.setExit("south", staffRoom);        
        office.setExit("east", reception);
        office.setExit("north", securityRoom);
        office.setExit("south", jimmysOffice);        
        jimmysOffice.setExit("north", office);        
        securityRoom.setExit("south", office);
        
        //create the rooms in Ians house
        
        hall = new Room("in Linda's hall.", null);
        kitchen = new Room("in Linda's kitchen.", null);
        frontRoom = new Room("in Linda's front room.", null);
        study = new Room("in Ian's study.", null);   
        
        //initialise room exits in Ian's house
        hall.setExit("east", frontRoom);
        hall.setExit("north", kitchen);
        kitchen.setExit("south", hall);
        frontRoom.setExit("west", hall);
        frontRoom.setExit("north", study);
        study.setExit("south", frontRoom);
        
        //create the police station
        policeStation = new Room("in the Police station.", null); 
        
        /**
     * This determines in detail what items are exactly and where they are lcoated in the rooms. These are critical itmes as they determine 
     * the game play and outcometo solving the clue.
     */
        
        // create Items
        
        desk = new Item("desk", "Ian's DESK sits by the window. ", "Inside the desk is an ENVELOPE. ", 50000, true);
        computer = new Item("computer", "Ian's COMPUTER is ontop the desk.", "To log on requires a password.", 6000, true);
        envelope = new Item("envelope", "An envelope  is inside the desk. ", "TAKE the envelope to read it. This could be a clue." , 150, false);
        screwdriver = new Item("screwdriver", "A SCREWDRIVER lies on the desk. ",  "A cross-head screwdriver about 30cms long found on Ian's Desk.", 1500, true);
        notebook = new Item("notebook", "A NOTEBOOK is on the desk. ", "A ball point pen and a blank notebook filled with sheets white A4 paper, found in Jimmy's Office.", 100, true);
        letter = new Item("letter", "A LETTER is on the desk. ", "TAKE the letter to read it.", 100, true);
        drawer = new Item("drawer", "A locked DRAWER is in Jimmy's desk.", "Although lock is of good qualty, the wooden drawer in held together by small screws.",10000, true); 
        safe = new Item("safe", "A SAFE is inside Boris's Office.", "The safe is of great quality and is impossible to crack without the knowledge of the technique.", 10000, true);
        shirt = new Item("shirt", "A SHIRT found inside Boris's safe.", "Take the SHIRT to have a closer look.", 1000, false);
        key = new Item("key", "A KEY found in Boris's safe.", "The key is thick and silver in colour.", 500, false);
        door = new Item("door", "A DOOR that requires a key to enter faces south.", "The door says Alberts Office.", 6000, true);
        photograph = new Item("photograph", "A framed PHOTOGRAPH is on Albert's desk.", "The photograph is of Albert and his wife at their wedding. It displays the date 20/09/2009. Could the date be significant?", 1500, true); 
        
        laptop = new Item("laptop", "A LAPTOP is in Ian's Office.", "The laptop weights 300 grams. TAKE the laptop to have a closer look.", 3000, true);
        
        // add items to room
        iansOffice.placeItem(desk);
        iansOffice.placeItem(envelope);
        iansOffice.placeItem(computer);
        staffRoom.placeItem(screwdriver);
        staffRoom.placeItem(door);
        reception.placeItem(notebook);
        securityRoom.placeItem(letter);
        jimmysOffice.placeItem(drawer);
        borissOffice.placeItem(safe);
        borissOffice.placeItem(shirt);
        borissOffice.placeItem(key);
        albertsOffice.placeItem(photograph);
        
        study.placeItem(laptop);

        
        //create characters
        
        
        jimmy = new Person("Jimmy", "JIMMY is in the room. He looks very upset and is shaking uncontrolably.");
        albert = new Person("Albert", "ALBERT is outside smoking a cigarette.");
        boris = new Person("Boris", "BORIS is in the room. He looks calm and in control dispite the circumstances.");
        linda = new Person("Linda", "LINDA is in the room. She has been crying but now looks deep in thought.");
        officer = new Person("Officer", "The OFFICER is in the room and wants to know who you think killed Ian."); 
        
        jimmy.SetConversation("Can you think of a reason why anyone would want to kill Ian?", "No, h-he was the-the nicest guy you could meet." );
        jimmy.SetConversation("Have you noticed anything suspicious recently?", "Well, Boris and Albert have had a r-really bad f-fall out recently." );
        
        boris.SetConversation("Can you think of a reason why anyone would want to kill Ian?", "Ian?, I never really new the guy to be honest.");
        boris.SetConversation("Did you know Jimmy has a drinking problem?", "Jimmy?... Well, yes now that you mention it he did like to drink a lot, and often too.");
        boris.SetConversation("Do you mind if I have a look in your office?", "By all means be my guest. I unlocked it just a moment ago. It's just north of here.");
        
        albert.SetConversation("Can you think of a reason why anyone would want to kill Ian?", "I did see Jimmy go into his office the other day and they had an arguement.");
        albert.SetConversation("Did you know Jimmy has a drinking problem?", "Yes, I did. I tried talking to him about it but he wouldn't let me help him.");
        albert.SetConversation("How would you discribe Boris's relationship with Ian", "They seemed ok, it's Jimmy that you'll want to be talking to.");
        albert.SetConversation("What's your role within the business?", "I am in charge of security.");
        
        linda.SetConversation("Can you think of a reason why anyone would want to kill Ian?", "No, he'd never hurt a fly." );
        linda.SetConversation("Do you mind if I have a look around?", "Not at all, just don't move things around.");
        linda.SetConversation("How well do you know Ian's Colleges?", "Ian and Jimmy go way back, but I hardly know the other two.");
        linda.SetConversation("Were you aware Jimmy has a drinking problem?", "Yes, he came around the house drunk the other day and started shouting at Ian saying he hated him.");
        linda.SetConversation("Who were you having an affair with? Tell me the truth, it's very important.", "Ok, it was Boris. It started about 6 mounths ago.");
        linda.SetConversation("Could anyone else in the office have found out about the affair?", "I think one of Ian's colleges may have suspected something.");
        
        officer.SetConversation("Albert Griffin", "You have made the wrong decision. Forencis tests find DNA on Ian's body that they think belongs to the killer, however it did not match Albert's.");
        officer.SetConversation("Boris Armstrong", "Correct! Boris was having an affair with Linda. Boris killed Ian to save his job and in the hope of being with Linda. DNA tests confirm Boris was the killer.");
        officer.SetConversation("Jimmy Soriano", "You have made the wrong decision. Forencis tests find DNA on Ian's body that they think belongs to the killer, however it did not match Jimmy's.");
        officer.SetConversation("Linda Johnson", "You have made the wrong decision. Forencis tests find DNA on Ian's body that they think belongs to the killer, however it did not match Linda's.");
        officer.SetConversation("Give me more time, I need to look over the clues again.", "Okay, take as long as you need.");
        
        
        //place characters in rooms.
        office.placePerson(jimmy);
        hall.placePerson(linda);
        policeStation.placePerson(officer);
        
        
        // set room to start game in
        currentRoom = outside;  

    }
     
    /**
     *  Main play routine.  Loops until end of play. This has been done to make sure that the user can end and restart the game accordingly. 
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
        System.out.println("Thank you for playing.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
       
        System.out.println("It's 4:00am when you, a famous detective receive a call from the station.");
        System.out.println("Your assistant breifs you and you note the following information:");
        System.out.println();
        System.out.println("The murder occured at approximately 2:00am in a company's headquarters based ");
        System.out.println("just on the coast of Southsea, Portsmouth.");
        System.out.println("The victim, Ian, was the manager in his early 40s, and there were 3 people with access to his office");
        System.out.println("over-night; Albert, Boris and Jimmy. His friend and college Jimmy, claims he went to his office at ");
        System.out.println("3:00am and found his body dead.");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomDetails();
        System.out.println();
    }

    /**
     * Occurs after the player finds the letter in Ians office. 
     * The method prints out the storyline.
     */
        private void stageOneComplete(){
        System.out.println();
        System.out.println("You have found a clue and ring up the detective agency to inform them about it.");
        System.out.println("Perhaps some-one here will know who Linda is.");
        jimmy.SetConversation("Do you know anyone by the name of Linda?", "L-Linda, yeah Linda is Ians Wife" );
    }
    
    /**
     * Occurs when the player unscrews Jimmy's desk with the screwdriver.
     * Boris is placed outside his office and his office is now unlocked.
     */
    private void stageTwoComplete() {
        System.out.println("You have unscrewed Jimmy's Desk. The drawer contains large amounts of alcohol");
        System.out.println("mainly vodka and whiskey");
        jimmy.SetConversation("Do you consider yourself to be an alocholic?", "Ok, I-I have a b-bit of a problem, P-Please don't tell the others" );
        System.out.println();
        System.out.println("Your phone rings and the detective agency inform you that Boris is coming into");
        System.out.println("the office now.");
        System.out.println("You ask them if they can allow you access to Ians computer, and they say they");
        System.out.println("will get back to you on that.");
        staffRoom.placePerson(boris);
        borissOffice.unlock();
        
    }
    
    /**
     * Occurs when player tries to access Boris's safe.
     * Allows the player to ask Bois to open his safe.
     * Moves Jimmy to his office.
     */
    private void stageThreeComplete() {
        boris.SetConversation("I need to have a look inside your safe.", "That's strictly Confidential! You insist? Ok fine, I've nothing to hide.");
        
        office.removePerson("Jimmy");
        jimmysOffice.placePerson(jimmy);
    }
    
    /**
     * Occurs when you ask Boris to open his safe
     * Allows the player to see the contents of Boris's safe
     */
    private void stageFourComplete(){
        safe.setLongDiscription("The safe contains a SHIRT and a KEY."); 
    }
    
    /**
     * Occurs when the player inspects the shirt in Boris's safe.
     * Places Albert outside, and allows you to view Ians computer.
     */
    private void stageFiveComplete(){
        boris.SetConversation("Why was there a shirt with blood on in your office?", "I got into a brawl with some local in the pub.");
        boris.SetConversation("Why did you hide the shirt in the safe?", "I thought it would look suspicious so I hid it.");
        System.out.println();
        System.out.println("You receive another call from the agency, and inform them of the blood-stained shirt you have found." );
        System.out.println("They say Albert is due in for work any minute now and that they have been able to hack Ians computer for you.");
        computer.setLongDiscription("Ians computer reveals he has sold his car online for £30,000.");
        
        outside.placePerson(albert);
    }
    
    /**
     * Occurs when the player inspects the second letter.
     * Allows the player to ask the three characters for a sample of their handwriting.
     * Moves Boris to his Office. 
     */
    private void stageSixComplete(){
        System.out.println("It would be interesting to know who the letter was written from.");
        System.out.println();
        boris.SetConversation("Can I take a sample of your handwriting?", "Yes, okay, can I ask as to why?");
        jimmy.SetConversation("Can I take a sample of your handwriting?", "Y-yeah, b-but I am shaking s-so it won't be any good.");
        albert.SetConversation("Can I take a sample of your handwriting?", "Yes, yes if you must.");
        
        staffRoom.removePerson("Boris");
        borissOffice.placePerson(boris);
    }
    
    /**
     * Occurs when the player inspects Alberts handwriting.
     * Moves the player to Linda's house.
     */
    private void stageSevenComplete() {
        albert.SetConversation("Who did you write the letter to?", "I didn't write that letter, some-one must have forged it.");
        System.out.println();
        System.out.println("Albert insists that he didn't write the letter dispite a clear resemblance in");
        System.out.println("handwriting. You decide to pay Ian's wife, Linda, a visit to see if you can get");
        System.out.println("any information out of her. You travel to her house in your car and knock on ");
        System.out.println("her door. She opens the door and lets you in.");
        currentRoom = hall;
        printRoomDetails();
        
    }
    
    /**
     * Occurs when you inspect the laptop in Ians study.
     * Moves the player to the Police station.
     * There they can decide who they think killed Ian. 
     */
    private void stageEightComplete() {
        System.out.println("You get a call from the office, they ask you how you have been");
        System.out.println("getting on and tell you to come to the office. You leave Linda's and");
        System.out.println("drive to the office.");
        System.out.println();
        currentRoom = policeStation;
        printRoomDetails();
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
                        
        if(command.isUnknown()) {
            System.out.println("That is not a valid command word.");
            System.out.println();
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            questioning = false;
            printHelp();
        }
        
        else if (commandWord.equals("go")) {
            questioning = false;
            goRoom(command);
        }
        
        else if (commandWord.equals("quit")) {
            questioning = false;
            wantToQuit = quit(command);
        }
        
        else if (commandWord.equals("take")) {
            questioning = false;
            takeItem(command);
        }
        
        else if ( commandWord.equals("inventory")) {
            questioning = false;
            displayInventory();
        }
        else if ( commandWord.equals("inspect")) {
            questioning = false;
            inspect(command);
        }
        
        else if ( commandWord.equals("drop")) {
            questioning = false;
            dropItem(command);
        }
            
        else if ( commandWord.equals("enter")) {
            questioning = false;
            enterPin(command);
        }
                    
        else if ( commandWord.equals("back")) {
            questioning = false;
            goBack(command);
        }
        
        else if ( commandWord.equals("question")) {
            question(command);
        }
        
        else if (commandWord.matches("[0-9]+") && questioning == true) {
            ChooseQuestion(command);
        }
        
        else if (commandWord.equals("use")) {
            questioning = false;
            Use(command);
        }
        
        else if (commandWord.matches("[0-9]+")) {
            System.out.println("I don't know what you mean.");
        }
        System.out.println("");
        
        // else command not recognised.
        return wantToQuit;
    }


    // implementations of user commands:

    
    /**
     * Print out some help information.
     * Informs the user of the commands that can be made and adds some hints for completing the game.
     */
    private void printHelp() 
    {
        System.out.println("You are the detective investing the murder of Ian.");
        System.out.println("Search the building for clues and interview the suspects.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println("Note that when interacting with People the first letter of the name");
        System.out.println("must be spelt with a capital");
        System.out.println("The USE command requires three words for example 'Use notebook door'");
        System.out.println("If you are stuck try questioning people, inspecting items and using items");
        
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * Also lists the Items and People in the room if there are any and the
     * room change is sucessful.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where sir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door in that direction.");
            System.out.println(currentRoom.getLongDescription());
        }
        else {
            if (nextRoom.getLock() == null) {
                previousRooms.add(currentRoom);
                currentRoom = nextRoom;
                
                printRoomDetails();
                
            }
            else {
                System.out.println(nextRoom.getLock());
                
            }
        }
    }
    
    /**
     * This prints our details, people and items in the room. 
     */
    private void printRoomDetails(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println("Items: " + getItemDiscriptions(currentRoom));
        System.out.println("People: " + getPeopleDiscriptions(currentRoom));
    }
    
    
     /**
     * This is asking the user what it is exactly what they would like to take. 
     * It will then remove the Item from the room and add it to the inventory
     * This will only happen if the item is within the weight contraints and is 
     * in the current room.
     * It is embedded with the blackadder theme.
     * @param the command containing the name of the item to be taken.
     */
    private void takeItem(Command command) 
    {
        Item item ;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to take?");
            return;
        }

        String object = command.getSecondWord();

        
        // if object is in the room.
        
        if (currentRoom.getItemsString().contains(object)) {
            //pick up object
            item = currentRoom.removeItem(object);
            if(getInventoryWeight() + item.getWeight() <= maxWeight){
            
                inventory.add(item);
                System.out.println("You pick up the " + object + " and add it to your INVENTORY.");
            }
            else {
                if(item.getWeight() > maxWeight){
                     System.out.println("The item weighs " + item.getWeight() + " grams. Your limit is " + maxWeight + " grams.");
                }
                else {
                    System.out.println("You can't pick up this item because it exceeds the weight limit.");
                    System.out.println("To pick this item up, DROP another item, in your INVENTORY.");
                }
                currentRoom.placeItem(item);
            }
        }
        
        else {
            System.out.println("That object is not in this room.");
        }
    }
    
    
    /**
     * Drops an item in the players inventory and places
     * @param the command that features the name of the item to be dropped.
     */
     private void dropItem(Command command) 
    {
        Item itemToBeRemoved = null;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to drop?");
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
        }
        
        else {
            System.out.println("That object is not in your INVENTORY.");
        }
    }
    
    
    /**
     * @return The list of items in the room.
     * @param The room the be checkedfor the items.
     */
    private String getItemDiscriptions(Room location)
    
    {
        ArrayList<Item> ItemsInRoom;
        ItemsInRoom = location.getItems();
        String itemsDiscription;
        itemsDiscription = "";
        
        for (Item item : ItemsInRoom) {
            if(item.getVisibility() == true) {
                itemsDiscription = itemsDiscription +item.getShortDiscription();
            }
        }
        return itemsDiscription;
    }
    
     /**
     * @return The list of items in the room.
     * @param The room the be checkedfor the items.
     */
        private String getPeopleDiscriptions(Room location)
    
    {
        ArrayList<Person> peopleInRoom;
        peopleInRoom = location.getPeople();
        String peoplesDiscription;
        peoplesDiscription = "";
        
        for (Person person : peopleInRoom) {
            peoplesDiscription = peoplesDiscription +person.getDescription();
            
        }
        return peoplesDiscription;
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
     
    
    /**
     * This is the invenory in the game play, it allows users to add items depending on the weight of them. 
     * this will workout wether they can take it or not.
     */
    private void displayInventory() {
        System.out.println("You have the following items in your possession:");
        for( Item item : inventory) {
            System.out.println(item.getName()+ ", Weight: " + item.getWeight() +" grams");
            
        }
        System.out.println();
        System.out.println("You can carry a maximum of " + maxWeight + " grams.");
        System.out.println("Total Weight: " + getInventoryWeight() + " grams.");
    }
    
    /**
     * @return The total weight of all the items in your inventory.
     */
    private int getInventoryWeight() {
        int totalWeight = 0;
        for(Item item : inventory) {
            totalWeight = totalWeight + item.getWeight();
        }
        return totalWeight;
    }
   
     /**
     * This returns the name of each item as a array list.
     */
    private ArrayList getItemsString()
    {
        ArrayList<String> ItemStrings;
        ItemStrings = new ArrayList<String>(); 
        for (Item item : inventory) {
             ItemStrings.add(item.getName());
        }
        
        return ItemStrings;
    }
    
    /**
     * For each item this reurns the long items decription, 
     */
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
    
     /**
     * This asks what it is that the user would like to inspect, this is critical to understand/progress in the gameplay. It prints the question
     * 'What would you like to inspect sir' We have added the blackadder theme to this game to create a more interesting experiance of gameplay.
     * @return If the item is in the current room or the players inventory it will return the discription.
     */
    private void inspect(Command command) {
                Item item ;
                Person person;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("What would you like to inspect?");
            return;
        }

        String object = command.getSecondWord();

             
        //If item is in room
        if (currentRoom.getItemsString().contains(object)) {
            System.out.println(currentRoom.inspectItem(object));  
            if (object.equals("safe")) {
                if ( stageThreeComplete == false) {
                    stageThreeComplete = true;
                    stageThreeComplete();
                }

            }
        }
        //If item is in inventory
        else if (getItemsString().contains(object)) {
            if(object.equals("envelope")) {

                
                System.out.println();
                System.out.println("To my beloved,");
                System.out.println("I want you to know even after what you did I still love you,");
                System.out.println("Ian very nearly caught us last week and I think he is");
                System.out.println("getting suspicious that something is up. ");
                System.out.println("How much longer can we keep this up?");
                System.out.println("");
                System.out.println("Love you, Linda ");

                if ( stageOneComplete == false) {
                    stageOneComplete = true;
                    stageOneComplete();
                }
                
            }
            
            else if(object.equals("letter")) {
                System.out.println("The hand-written letter reads:");
                System.out.println();
                System.out.println("I know your secret.");
                System.out.println("Unless you give me £10,000 in cash by the end of next week");
                System.out.println("I will tell Ian and your career will be over.");
                System.out.println("Clocks ticking...");
                System.out.println("");
                
                if (stageSixComplete == false) {
                    stageSixComplete = true;
                    stageSixComplete();
                }
            }
            
            else if(object.equals("shirt")) {
                System.out.println("The shirt was once plain white, but is soaked in blood.");
                if ( stageFiveComplete == false) {
                    stageFiveComplete = true;
                    stageFiveComplete();
                }
                
            }
            
            else if(object.equals("laptop")) {
                System.out.println("The laptop contains various e-mails:");
                System.out.println();
                System.out.println("To: Jimmy Soriano");
                System.out.println("Jimmy,");
                System.out.println("It was really out of order what you did the other night. You really need");
                System.out.println("to quit the drink or else I won't have you working for my company any ");
                System.out.println("more.");
                System.out.println("I was really scared for my life the other night.");
                System.out.println("Ian Johnson");
                System.out.println("p.s. Check this link: www.drinkaware.co.uk/ it might help you.");
                System.out.println();
                System.out.println("To: Boris Armstrong");
                System.out.println("Boris,");
                System.out.println("I know what you have been up to lately. I found a letter in your office.");
                System.out.println("I know that this job means a lot to you, but I can no longer work with");
                System.out.println("you. Resign peacefully or you're fired.");
                System.out.println("Ian Johnson");
                System.out.println();
                System.out.println("To: Albert Griffin RE: Expansion of the business");
                System.out.println("Al,");
                System.out.println("Your plans for the business are simply rediculous. No client would ever");
                System.out.println("comply with that and it will not be profitable. If I told the shareholders");
                System.out.println("your suggestions they would think I was joking.");
                System.out.println("I'll have to be six-feet under before these changes happen.");
                System.out.println();
                if ( stageEightComplete == false) {
                    stageEightComplete = true;
                    stageEightComplete();
                }
                
            }
            
            else{
                System.out.println(getItemsLongDiscription(object));
            }
        }
        else if(currentRoom.getPeopleString().contains(object)) {
            System.out.println(currentRoom.inspectPerson(object));
        }
        
        
        else {
            System.out.println("That object is not in this room or in your INVENTORY.");
        }
    }
    
    /**
     * @param The command containing the word enter. 
     * If the pin number is correct the room will become unlocked.
     */
        private void enterPin(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Enter what?");
            return;
        }

        String pin = command.getSecondWord();

        // Try to leave current room.
        

        if(currentRoom.getShortDescription().equals("in the general office.")) {
            if (pin.equals("2009")) {
                securityRoom.unlock();
                System.out.println("The pin number was successful and the door is now unlocked.");
            }
            else {
                System.out.println("The pin number was not correct.");
            }
        }
        else {
                System.out.println("There is no where to enter that.");
            }

    }

    /**
     * @param This requires a command where the first word is back.
     * This will change the value of the current room to a previous room 
     * that has been visited. If a number is supplied it will go back
     * that number of rooms.
     */
    private void goBack (Command command ) {
        int numberToGoBack;
        Room nextRoom;
        int numberOfPreviousRooms;
        numberOfPreviousRooms = previousRooms.size(); 
        
        if(command.hasSecondWord()) {
            String secondWord = command.getSecondWord();
            
            
            if (secondWord.matches("[0-9]+")){
                numberToGoBack = Integer.parseInt(secondWord);
            }
            
            else {
                numberToGoBack = 1;
            }
        }
        
        else{
            numberToGoBack = 1;
        }
        
        if(numberToGoBack > numberOfPreviousRooms) {
            System.out.println("You have not been in that number of rooms yet.");
        }
        
        else {
            previousRooms.add(currentRoom); 
            nextRoom = previousRooms.get(numberOfPreviousRooms -numberToGoBack);
            currentRoom = nextRoom;     
            printRoomDetails();
        }
    }
    
    /**
     * This allows you to ask a person in the room a question.
     * This will print a list of available questions if the 
     * person supplied is in the current room. 
     */
     private void question(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Question who?");
            return;
        }

        String secondWord = command.getSecondWord();
        currentPerson = null;
        ArrayList<String> questions;

        
        if(currentRoom.getPeopleString().contains(secondWord)) {
            for(Person person : currentRoom.getPeople()) {
                if (person.getName().equalsIgnoreCase(secondWord)) {
                    currentPerson = person;
                }

            }
            
            questions = new ArrayList<String>();   
            questions = currentPerson.getQuestions();
            int questionNumber = 0;
            questioning = true;
            if(currentPerson.equals(officer)) {
                System.out.println("Who do you think killed Ian Johnson?");
            }
            for(String question : questions) {
                questionNumber ++;
                System.out.println(questionNumber + " : " + question);
            }
        }
    
        
        
        else {
            System.out.println("That person is not currently in this room.");
        }
    }
    
    /** 
     * This will ask the person being question a question number 
     * and print the answer.
     * @param the question number to be asked.
     */
    private void ChooseQuestion(Command command) {
        int questionNumber;
        questionNumber = Integer.parseInt(command.getCommandWord());
        String question, answer;
        if (currentPerson.getNumberOfQuestions() >= questionNumber) {
            question = currentPerson.getQuestion(questionNumber);
            answer = currentPerson.askQuestion(questionNumber);
            System.out.println(question);
            System.out.println();
            System.out.println(answer);
            if(currentPerson.equals(boris) && question.equals("I need to have a look inside your safe.")) {
                stageFourComplete();
            }
            
            else if (question == "Can I take a sample of your handwriting?") {
                if(inventory.contains(notebook)){
                    System.out.println();
                    if(currentPerson.equals(boris)) {
                        System.out.println("The handwriting of the LETTER does not match Boris's handwriting.");
                    }
                    else if(currentPerson.equals(jimmy)) {
                        System.out.println("The handwriting of the LETTER does not match Jimmy's handwriting.");
                    }
                    else if(currentPerson.equals(albert)) {
                        System.out.println("The handwriting of the LETTER matches that of Albert's handwriting!");
                        if ( stageSevenComplete == false) {
                            stageSevenComplete = true;
                            stageSevenComplete();
                        }
                    
                    }
                }
                else {
                    System.out.println("You will need some paper and a pen on your person to sample handwriting");
                }
            }
            else if(question == "Albert Griffin"){
                wantToQuit = true;
            }
            else if (question == "Jimmy Soriano") {
                wantToQuit = true;
            }
            else if (question == "Linda Johnson") {
                wantToQuit = true;
            }
        }
        else {
            System.out.println("There are not that many questions to ask");
        }
    }
    
    /**
     * This command requires three words and will allow the properties
     * of items to change such as unlocking a door. 
    */
    private void Use(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Use what two objects?");
            return;
        }
        String secondWord = command.getSecondWord();
        
        if(!command.hasThirdWord()) {
            System.out.println("The use command requires 3 words. What what you like to use the " + secondWord + " with?");
            return;
        }
        
        //if there are three words
        else {
            String thirdWord = command.getThirdWord();
            
            //If first item is in inventory
            if (getItemsString().contains(secondWord)) {
                //If second item is in room
                if(currentRoom.getItemsString().contains(thirdWord)) {
                    
                    if(secondWord.equals("screwdriver") && thirdWord.equals("drawer") /*&& currentRoom.equals(jimmysOffice)*/) {
                        if(stageTwoComplete ==false) {
                            stageTwoComplete = true;
                            stageTwoComplete();
                        }
                        else {
                            System.out.print("You have already disassembled Jimmy's drawer.");
                        }
                    }
                    
                    else if (secondWord.equals("key") && thirdWord.equals("door")) {
                        System.out.println("You use the KEY on the DOOR and unlock Albert's Office.");
                        albertsOffice.unlock();
                    }
                    

                    
                    else {
                        System.out.println("The " + secondWord + " can't be used with the " + thirdWord);
                    }
                }
                
                else {
                    System.out.println("The second item must be in the current room.");
                }
            }
            
            else {
                System.out.println("The first item must be in your inventory");
            }
            
        }
        
    }
    
}








