 /* @author (your name)
 * @version (Per 5 2018)
 */
import java.util.* ;
public class Items
{
    // instance variables - replace the example below with your own
    
    /* DO NOT TOUCH THIS*/ private Game game = Main.getGameObject(); /* DO NOT TOUCH THIS */
    private String title;
    private String description;
    private int weight;
    private Room location;
    
   
    /**
     * Constructor for objects of class Inventory
     */
    public Items(String t, String d, int w, Room l)//Whole class was made by all
    {
        title = t;
        description = d;
        weight = w;
        location = l;
    }
    //Below are the getters for the items
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public int getWeight()
    {
        return weight;
    }
    public Room getLocation()
    {
        return location;
    }
    //Below are the setters for the items
    public String setTitle()
    {
        return title;
    }
    public String setDescription()
    {
        return description;
    }
    public int setWeight()
    {
        return weight;
    }
    public Room setLocation()
    {
        return location;
    }
}
