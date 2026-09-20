package dungeonCrawler;

import java.util.*;

// -------------------------------------------------------------------------
/**
 *  Runs the randomized dungeon crawler using a series of classes and methods
 * 
 *  @author Tyler
 *  @version Sep 3, 2026
 */
public class GameRunner {
    //~ Fields ................................................................
    private static int depth;
    //~Public  Methods ........................................................
    public static void startGame() {
        Player player = new Player(100, 3, .9);
        GameRunner.runTutorial();
        GameRunner.runDungeonLoop();
    }
    public static void runTutorial() {
        //tutorial
    }
    public static void runDungeonLoop() {
        //make rooms and stuff
    }
    public static void endGame(int depthReached) {
        
    }
}
