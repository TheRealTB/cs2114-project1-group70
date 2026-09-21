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
    private static Player player;
    private static Scanner scanner;
    private static InputHandler input;

    //~Public  Methods ........................................................
    /**
     * Entry point so the project can actually be launched.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        startGame();
    }


    public static void startGame() {
        scanner = new Scanner(System.in);
        input = new InputHandler(scanner);
        player = new Player(100, 3, .9);
        depth = 0;

        System.out.println("=== Dungeon Crawler ===");
        System.out.println("In a room:      attack / parry / dodge / heal");
        System.out.println("Between rooms:  left / forward / right, or quit");
        System.out.println();

        GameRunner.runDungeonLoop();
        GameRunner.endGame(depth);
        scanner.close();
    }


    public static void runDungeonLoop() {
        Room current = DungeonGenerator.makeRoom(depth);

        while (player.isAlive()) {
            boolean survived = current.enter(player, scanner);
            if (!survived || !player.isAlive()) {
                return;
            }

            System.out.println();
            System.out.println(current.look());

            // only offer the exits this room actually has, so the player
            // cannot walk into a wall
            Set<String> exits = new HashSet<String>();
            if (current.hasExit("left")) {
                exits.add("left");
            }
            if (current.hasExit("forward")) {
                exits.add("forward");
            }
            if (current.hasExit("right")) {
                exits.add("right");
            }
            exits.add("quit");

            String dir = input.getValidatedCommand(exits, "Which way?",
                "quit");
            if (dir == null || dir.equals("quit")) {
                return;
            }

            Room next = current.go(dir, depth + 1);
            if (next == null) {
                // go() returns null when that direction has no open exit
                System.out.println("That way is blocked.");
                continue;
            }
            depth++;
            current = next;
        }
    }


    public static void endGame(int depthReached) {
        System.out.println();
        System.out.println("=== Game Over ===");
        if (player != null && player.isAlive()) {
            System.out.println("You left the dungeon alive.");
        }
        else {
            System.out.println("You died in the dark.");
        }
        System.out.println("Depth reached: " + depthReached);
    }
}
