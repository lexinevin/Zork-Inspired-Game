
/**
 * This will be the player, includes their inventory/backpack, health, etc.
 */
import java.util.ArrayList;
public class Player
{
    // instance variables - replace the example below with your own
    private ArrayList<Items> inventory = new ArrayList<Items>();
    private int maxWeight = 10;
    private int currentWeight = 0;
    private Game game = Main.getGameObject();
    /**
     * Constructor for objects of class Inventory
     */
    public Player()
    {
        // initialise instance variables
    }
     // This will add an item to the inventory, as long as the weight is acceptable and the location is correct
    public void inventoryAdd(Items item, Room location)//Made by Justin
    {
        if (item.getLocation() == location)
        {
            if ((currentWeight + item.getWeight()) <= maxWeight)
            {
                inventory.add(item);
                currentWeight = currentWeight + item.getWeight();
                System.out.println("You pick up the " + item.getTitle() + ".");
            }
            else
            {
                System.out.println("This item is too heavy for me to carry!");
            }
        }
        else
        {
            System.out.println("I don't see any " + item.getTitle() + " in here.");
        }
    }
    //This will remove all the items of the type provided from the player's inventory
    public void inventoryDel(Items item)//Made by Justin
    {
        for (int x = 0; x < inventory.size(); x++)
        {
            if (inventory.get(x) == item)
            {
                inventory.remove(x);
                currentWeight = currentWeight - item.getWeight();
            }
        }
        System.out.println("You drop any " + item.getTitle() + "s you were carrying.");
    }
    //This will check if the player's inventory contains the item in the argument
    public boolean inventoryContains(Items item)//Made by Justin
    {
        boolean flag = false;
        for (int x = 0; x < inventory.size(); x++)
        {
            if (inventory.get(x) == item)
                flag = true;
        }
        return flag;
    }
    //This will print out the contents of the player's inventory
    public void getInventory()//Made by Justin
    {
        System.out.println("Your inventory contains:");
        for (int x = 0; x < inventory.size(); x++)
        {
            Items item = inventory.get(x);
            System.out.println(item.getTitle());
        }
    }
    public int getCurrentWeight()
    {
        return currentWeight;
    }
}