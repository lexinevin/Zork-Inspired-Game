/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    March 2000
 * 
 * Modified by: Kevin Good
 * Date:        October 2018
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can walk around some scenery.
 *  That's all. It should really be extended to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns.
 */
import java.util.* ;
class Game 
{
    Game game = Main.getGameObject();
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Room roomTemp;
    private String secondWord;
    private String targetItem;
    private Items inspectTargetItem;
    private Items itemFound;   
    private Player player;
    private boolean finished;
    private Room outside, kitchen, poolRoom, library, exitRoom;
    private Items egg, plant, flashlight, scissors, snorkel, towel, key, bookshelf, blueBook, redBook, chocolate, mat, mailbox, letter, blankItem;
    

    /**
     * Create the game and initialize its internal map.
     */
    public Game()//Method was given
    {
        parser = new Parser();
        player = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     * 
     */
    public void createRooms()//Method was given
    {      
        
        // create the rooms, format is name = new Room("description of the situation");    ex. desc = 'in a small house' would print 'you are in a small house.'
        outside = new Room("outside", "outside of a small building with a door straight ahead. There is a welcome mat, a mailbox, and a man standing in front of the door.", false);
        kitchen = new Room("kitchen", "in what appears to be a kitchen. There is a sink and some counters with an easter egg on top of the counter. There is a locked door straight ahead, and a man standing next to a potted plant. There is also a delicious looking chocolate bar sitting on the counter... how tempting", true);
        poolRoom = new Room("pool room", "in a new room that appears much larger than the first room.You see a table with a flashlight, towel, and scissors on it. There is also a large swimming pool with what looks like a snorkel deep in the bottom of the pool. Straight ahead of you stands the same man as before.", true);
        library = new Room("library", "facing east in a new, much smaller room than before. There are endless rows of bookshelves filling the room. All of the books have a black cover excpet for two: one red book and one blue book. The man stands in front of you, next to the first row of bookshelves", true);
        exitRoom = new Room("exit", "outside of the building again. You have successfully escaped, congratulations! Celebrate with a non-poisonous chocolate bar",true);
        
        // initialise room exits, goes (north, east, south, west)
        outside.setExits(kitchen, null, null, null);
        kitchen.setExits(poolRoom, null, outside, null);
        poolRoom.setExits(null, library, kitchen, null);
        library.setExits(null, null, exitRoom, poolRoom);
        exitRoom.setExits(null, null, null, null); 
 
        currentRoom = outside;  // start game outside
    }
    public void createItems()//Made by Justin
    {
        mat = new Items("welcome mat", "a brown welcome mat, with what seems to be a key sticking out from under it", 20, outside);
        mailbox = new Items("mailbox", "just a simple old mailbox, maybe you should inspect the mail...", 50, outside);
        letter = new Items("letter", "The letter reads: Do you want to play a game? All you have to do is enter the building... and P.S. don't fall for any temptations...", 1, outside);
        key = new Items("key", "a rusty old key covered in dirt from the mat", 1, outside);
        chocolate = new Items("chocolate", "a very tempting and delicious king sized chocolate bar", 1, kitchen);
        egg = new Items("egg", "a bright gold egg, almost shiny enough to solve any problem imaginable", 1, kitchen);
        plant = new Items("plant", "a three foot tall potted plant", 15, kitchen);
        flashlight = new Items("flashlight", "fresh batteries, a bright light,", 3, poolRoom);
        scissors = new Items("scissors", "sharp, great for cutting thin objects", 1, poolRoom);
        snorkel = new Items("snorkel", "use to see underwater without suffocation at small depths", 2, poolRoom);
        towel = new Items("towel", "a blue, soft towel to dry off after a nice swim", 5, poolRoom);
        bookshelf = new Items("bookshelf", "very large and heavy, with what seems like an endless amount of books on it", 50, library);
        blueBook = new Items("blue book", "a blue book with the title: Gone with the Wind", 1, library); //one of these book titles will be the answer to the library riddle
        redBook = new Items("red book", "a red book with the title: Alice in Wonderland", 1, library);
        blankItem = new Items("nonstatic", "nonstatic item for valid items  DON'T TOUCH ME!!!!I'M REALLY IMPORTANT!!!", 0, outside);
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()//Method was given
    {            
        createRooms();
        createItems();
        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        finished = false;
        while (! finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (currentRoom == exitRoom)
            {
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Goodbye.");
    }
    public Room getCurrentRoom()//Made by Justin
    {
        return currentRoom;
    }
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()//Method was given
    {
        System.out.println();
        System.out.println("Welcome to Zork!");
        System.out.println("This Zork is a new adventure game. Find your way through the building and get to the exit by solving riddles and escaping each room. Good luck!");
        System.out.println("Type 'help' to see a list of command words that you can use to play the game.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command)//Method was given
    {
        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("talk"))
            talkToCharacter(currentRoom);
        else if (commandWord.equals("grab"))
        {
            secondWord = command.getSecondWord();
            if (secondWord == null)
            {
                System.out.println("Grab what?");
            }
            else
            {
                grabItem(secondWord);
            }
        }
         else if (commandWord.equals("eat"))
        {
            secondWord = command.getSecondWord();
            if (secondWord == null)
            {
                System.out.println("Eat what?");
            }
            else
            {
                eatItem(secondWord);
            }
        }
        else if (commandWord.equals("inventory")) 
            player.getInventory();
        else if (commandWord.equals("drop"))
        {
            secondWord = command.getSecondWord();
            if (secondWord == null)
            {
                System.out.println("Drop what?");
            }
            else
            {
                dropItem(secondWord);
            }
        }
        else if (commandWord.equals("inspect"))
        {
            secondWord = command.getSecondWord();
            if (secondWord == null)
            {
                System.out.println("Inspect what?");
            }
            else
            {
                inspectItem(secondWord);
            }
        }
        else if (commandWord.equals("quit"))
        {
            if(command.hasSecondWord())
                System.out.println("Quit what?");
            else
                return true;  // signal that we want to quit
        }
        else if (commandWord.equals("look"))
        {
            System.out.println(currentRoom.longDescription());
        }
        return false;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
     
    private void printHelp()//Method was given
    {
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)//Method was given
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        if (direction.equals("back"))
        {
            roomTemp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = roomTemp;
            //roomTemp = null;
            System.out.println(currentRoom.longDescription());
        }
        else
        {
            // Try to leave current room.
            
            if (currentRoom.nextRoom(direction) == null)
                System.out.println("There is no door!");
            else 
            {
                Room nextRoom = currentRoom.nextRoom(direction);
                if (nextRoom.getLocked())
                {
                    System.out.println("The door is locked!");
                }
                else
                {   
                    previousRoom = currentRoom;
                    currentRoom = nextRoom;
                    System.out.println(currentRoom.longDescription());
                }
            }
        }
    }
    
    // grabItem will make sure the item is actually an item, and if so will add that item to the player's inventory
    private void grabItem(String item)//Made by Justin
    {
        itemFound = decodeItem(item);
        if (itemFound == null)
        {
            System.out.println("I don't know of any " + item + ".");
        }
        else
        {
            player.inventoryAdd(itemFound, currentRoom);
        }
    } // ends private void grabItem(Items item)
    
    // below will allow the player to eat an item ... aka the chocolate bar in the kitchen
     private void eatItem(String item)//Made by Lexi
    {
        itemFound = decodeItem(item);
        if (itemFound == null)
        {
            System.out.println("I don't know of any " + item + ".");
        }
        else
        {
            System.out.println("You gave in to the chocolate temptation and died from poison in the candy bar");
        }
    } // ends private void eatItem(String item)
    
    // below will let the player drop an item to make more room in their inventory
    private void dropItem(String item)//Made by Justin
    {
        itemFound = decodeItem(item);
        if (itemFound == null)
        {
            System.out.println("I don't know of any " + item + ".");
        }
        else
        {
            player.inventoryDel(itemFound);
        }
    }
    
    // below will let hte player inspect an item to see the item's wight, name, description, and room that it is found in
    private void inspectItem(String item)//Made by Lexi
    {
        itemFound = decodeItem(item);
        if (itemFound == null)
        {
            System.out.println("I don't know of any " + item + ".");
        }
        else
        {
            System.out.println("The item is: " + itemFound.getTitle());
            System.out.println("Description of the item: " + itemFound.getDescription());
            System.out.println("Item Weight: " + itemFound.getWeight());
            System.out.println("Room item was found in: " + itemFound.getLocation().getTitle());
        }
    } // ends private void inspectItem(Items item)
    
    // below will have the character tell the riddle when the talk command is used. Each room will have a different riddle given by the same character
    public void talkToCharacter(Room room)//Made by Lexi
    {
        if (currentRoom.getTitle().equals("outside")) {
            System.out.println("All you need to do is unlock the door...");
            System.out.println("Hint: use INSPECT to examine an item in detail.");
            if (player.inventoryContains(key))
            {
                kitchen.setLocked(false);
                System.out.println("The door makes a loud click.");
            }
        }
        
        
        else if (currentRoom.getTitle().equals("kitchen")) {
            System.out.println("In order to move on to the next room, you must first find an item which can be found by solving this riddle: ");
            System.out.println("A box without hinges, key, or lid, yet golden treasure inside is hid.");
            System.out.println("The answer is an item in this room that will help you escape and move on to the next room.");
            if (player.inventoryContains(egg))
            {
                poolRoom.setLocked(false);
                System.out.println("The door makes a loud click.");
            }
        }
        else if (currentRoom.getTitle().equals("pool room")) {
            System.out.println("In order to escape this room you must solve this next riddle: ");
            System.out.println("Wash me and I am not clean. Don't wash me and then I'm clean. What I Am?"); //water
            System.out.println("The answer to the riddle is where you'll find the object needed to escape this room... ");
            if (player.inventoryContains(snorkel))
            {
                library.setLocked(false);
                System.out.println("The door makes a loud click.");
            }
        }
        else if (currentRoom.getTitle().equals("library")) {
            System.out.println("In order to escape this room you must solve this next riddle: ");
            System.out.println("The objects, or people, have been removed from their previous localities through the power of a naturally moving phenomenon.");
            System.out.println("Once you have figured out what item belongs to the answer of this riddle you will have escaped the building!");
            if (player.inventoryContains(blueBook))
            {
                
                exitRoom.setLocked(false);
                System.out.println("The door makes a loud click.");
            }
        }
        else {
            System.out.println("There is nobody here, so you talk to yourself...");
        }
    } // ends private void talkToCharacter
    
    
    
// hard code all of the items individually so that the inventory will work
    public Items decodeItem(String targetItem)//Made by Justin and added to by all
    {
        if (targetItem.equals("egg"))
        {
            return egg;
        }
        else if (targetItem.equals("chocolate"))
        {
            return chocolate;
        }
        else if (targetItem.equals("plant"))
        {
            return plant;
        }
        else if (targetItem.equals("flashlight"))
        {
            return flashlight;
        }
        else if (targetItem.equals("scissors"))
        {
            return scissors;
        }
        else if (targetItem.equals("snorkel"))
        {
            return snorkel;
        }
        else if (targetItem.equals("towel"))
        {
            return towel;
        }
        else if (targetItem.equals("key"))
        {
            return key;
        }
        else if (targetItem.equals("bookshelf"))
        {
            return bookshelf;
        }
        else if (targetItem.equals("blue"))
        {
            return blueBook;
        }
        else if (targetItem.equals("red"))
        {
            return redBook;
        }
        else if (targetItem.equals("key"))
        {
            return key;
        }
        else if (targetItem.equals("mat"))
        {
            return mat;
        }
        else if (targetItem.equals("mailbox"))
        {
            return mailbox;
        }
        else if (targetItem.equals("letter"))
        {
            return letter;
        }
        return null;
    }
} // ends class Game