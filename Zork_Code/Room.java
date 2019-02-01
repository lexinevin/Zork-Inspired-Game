/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */
import java.util.* ;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

    
class Room
{
    private String title;
    private String description;
    private HashMap exits;        // stores exits of this room.
    private Game game = Main.getGameObject();
    private boolean locked;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */
    public Room(String title, String description, boolean locked)//Method was given
    {
        this.title = title;
        this.description = description;
        this.locked = locked;
        exits = new HashMap();
    }

    /**
     * Define the exits of this room.  Every direction either leads to
     * another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west)//Method was given
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getTitle()//Made by Justin
    {
        return title;
    }
    public String shortDescription()//Method was given
    {
        return description;
    }

    /**
     * Return a long description of this room, on the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String longDescription()//Method was given
    {
        return "You are " + description + ".\n" + exitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String exitString()//Method was given
    {
        String returnString = "Exits:";
		Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room nextRoom(String direction)//Method was given
    {
        return (Room)exits.get(direction);
    }
    public void setLocked(boolean lock)//Made by Justin
    {
        locked = lock;
    }
    public boolean getLocked()//Made by Justin
    {
        return locked;
    }
}
