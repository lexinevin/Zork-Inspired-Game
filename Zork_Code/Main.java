/**
 * Main Class - the runner class of the "Zork" game.
 *
 * Author:  Kevin Good
 * Version: 1
 * Date:    October 2018
 * 
 *  To play this game, the main method creates an instance 
 *  of the Game class and calls the "play" method.
 */
import java.util.* ;
public class Main {
	private static Game game;
	public static void main(String[] args)//Method was given
	{
		game = new Game();
		game.play();
	}
	public static Game getGameObject()//Made by Justin
	{
		return game;
	}
}
