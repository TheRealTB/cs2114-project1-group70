package dungeonCrawler;
 
import java.util.Scanner;
 
// -------------------------------------------------------------------------
/**
 * Runs the randomized dungeon crawler (the "GameEngine" class in the spec).
 *
 * @author Will Burnham
 * @version Sep 21, 2026
 */
public class GameRunner {
    // ~ Fields ................................................................
    private static int depth;
    private static Player player;
    private static Scanner in = new Scanner(System.in);
 
    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Starts the program.
     *
     * @param args dw bout it
     */
    public static void main(String[] args) {
        startGame();
    }
 
    // ----------------------------------------------------------
    /**
     * Runs the tutorial then the dungeon loop then the game-over screen.
     */
    public static void startGame() {
        player = new Player(100, 3, .9);
        depth = 0;
 
        System.out.println("=== Welcome to the Dungeon ===");
        String[] yesNo = { "yes", "no" };
        String answer = askChoice(
            "Would you like to play the tutorial? (yes / no)", yesNo);
        if (answer.equals("yes")) {
            runTutorial();
        }
        else {
            System.out.println("Skipping the tutorial.");
        }
 
        runDungeonLoop();
        endGame(depth);
    }
 
 
    // ----------------------------------------------------------
    /**
     * Tutorial
     */
    public static void runTutorial() {
        System.out.println();
        System.out.println("=== Tutorial ===");
        System.out.println("You fight your way down the dungeon one room "
            + "at a time. The deeper you get, the higher your score.");
        System.out.println("Every command is a word you type. Capital "
            + "letters and extra spaces are fine.");
 
        // a practice player and monster, so the real run is not affected
        Player trainee = new Player(100, 3, .9);
        Enemy dummy = new Enemy(0, false);
        dummy.changeMaxHp(100);
        dummy.changeHp(100);
        String dummyName = "practice " + dummy.getName();
        System.out.println("A " + dummyName + " appears. It won't hurt "
            + "much, so let's try every command.");
 
        System.out.println();
        System.out.println("ATTACK hits the enemy for your full damage.");
        waitForCommand("attack");
        int dmg = playerDamage(trainee, dummy, "attack");
        dummy.takeDamage(dmg);
        System.out.println("You deal " + dmg + ". The " + dummyName
            + " has " + dummy.getHp() + " HP left.");
 
        // heal
        int hit = (int)(dummy.getAtk() * trainee.getDef());
        dummy.attack(trainee, hit);
        System.out.println("After your move, the enemy hits back. The "
            + dummyName + " hits you for " + hit + ". Your HP: "
            + trainee.getHp() + "/" + trainee.getMaxHp());
 
        // parry
        System.out.println();
        System.out.println("PARRY is a careful counter: half damage plus 2.");
        waitForCommand("parry");
        dmg = playerDamage(trainee, dummy, "parry");
        dummy.takeDamage(dmg);
        System.out.println("You deal " + dmg + ".");
 
        // dodge
        System.out.println();
        System.out.println("DODGE is a quick jab while moving: half damage.");
        waitForCommand("dodge");
        dmg = playerDamage(trainee, dummy, "dodge");
        dummy.takeDamage(dmg);
        System.out.println("You deal " + dmg + ".");
 
        // heal
        System.out.println();
        System.out.println("HEAL drinks a potion for 10 HP. You get one "
            + "potion for each room you clear. Here is one to try.");
        trainee.addHealPotions(1);
        waitForCommand("heal");
        trainee.useHealPotion();
        System.out.println("Healed. Your HP: " + trainee.getHp() + "/"
            + trainee.getMaxHp());
 
        // print
        System.out.println();
        System.out.println("Between rooms you pick an exit left, forward, "
            + "or right. Each room takes you one level deeper.");
        System.out.println("Some rooms are fights, and every 10th level has "
            + "a boss. Upgrade rooms let you choose more health, attack, "
            + "or defense.");
        System.out.println("Your HP is restored after every room you clear. "
            + "Your score is the deepest level you reach.");
        System.out.println("=== Tutorial complete! ===");
    }
 
 
    // ----------------------------------------------------------
    /**
     * Enters a room, and moves deeper until the player dies or chooses to quit.
     */
    public static void runDungeonLoop() {
        depth = 1;
        Room current = RoomFactory.makeRoom(depth);
        boolean playing = true;
 
        while (playing && player.isAlive()) {
            System.out.println();
            System.out.println("--- Depth " + depth + " ---");
 
            boolean survived = current.enter(player, in);
 
            if (!survived || !player.isAlive()) {
                playing = false;
            }
            else {
                // health resets to max after each room
                player.changeHp(player.getMaxHp());
                System.out.println("You catch your breath. HP restored.");
                System.out.println(statsText());
 
                // a list of the exits can be open in said room 
                String exits = "";
                if (current.hasExit("left")) {
                    exits += "left / ";
                }
                if (current.hasExit("forward")) {
                    exits += "forward / ";
                }
                if (current.hasExit("right")) {
                    exits += "right / ";
                }
                exits += "quit";
 
                // keep asking until the player picks an open exit or quits
                String dir = "";
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.println("Which way? (" + exits + ")");
                    dir = in.nextLine().trim().toLowerCase();
                    if (dir.equals("quit") || current.hasExit(dir)) {
                        validChoice = true;
                    }
                    else {
                        System.out.println("\"" + dir
                            + "\" is not an open exit. Please try again.");
                    }
                }
 
                if (dir.equals("quit")) {
                    playing = false;
                }
                else {
                    Room next = current.go(dir, depth + 1);
                    if (next != null) {
                        depth++;
                        current = next;
                    }
                }
            }
        }
    }
 
 
    // ----------------------------------------------------------
    /**
     * Prints the final stats and the depth reached as the player's score.
     *
     * @param depthReached the deepest level the player reached
     */
    public static void endGame(int depthReached) {
        if (depthReached < 0) {
            System.out.println("Error: invalid depth (" + depthReached
                + "). Cannot show results.");
            return;
        }
 
        System.out.println();
        System.out.println("=== Game Over ===");
        if (player != null) {
            if (player.isAlive()) {
                System.out.println("You escaped the dungeon alive!");
            }
            else {
                System.out.println("You were defeated.");
            }
            System.out.println(statsText());
        }
        System.out.println("Deepest level reached: " + depthReached);
        System.out.println("Final score: " + depthReached);
    }
 
     /**
     * Helper method
     *
     * @param prompt the question to print
     * @param options the allowed answers 
     * @return the answer the player typed
     */
    private static String askChoice(String prompt, String[] options) {
        String answer = "";
        boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            answer = in.nextLine().trim().toLowerCase();
            for (int i = 0; i < options.length; i++) {
                if (answer.equals(options[i])) {
                    valid = true;
                }
            }
            if (!valid) {
                System.out.println("\"" + answer
                    + "\" is not an option. Please try again.");
            }
        }
        return answer;
    }
 
    /**
     * Helper method used by tutorial. Keeps asking until the player types the one
     * command this step is teaching, and explains the options if they
     * type something else.
     *
     * @param command the command to wait for
     */
    private static void waitForCommand(String command) {
        String typed = "";
        while (!typed.equals(command)) {
            System.out.println("Type \"" + command + "\":");
            typed = in.nextLine().trim().toLowerCase();
            if (!typed.equals(command)) {
                System.out.println("The fight commands are: attack, parry, "
                    + "dodge, heal.");
                System.out.println("For this step, please type \"" + command
                    + "\".");
            }
        }
    }
 
     /**
     * Works out how much damage the player deals. Uses the same math as
     * EncounterRoom so the tutorial matches the real game.
     *
     * @param p player attacking
     * @param e enemy being hit
     * @param cmd attack parry or dodge
     * @return the damage dealt
     */
    private static int playerDamage(Player p, Enemy e, String cmd) {
        int dmg = (int)(p.getAtk() * e.getDef());
        if (cmd.equals("parry")) {
            dmg = (int)(p.getAtk() * e.getDef() / 2) + 2;
        }
        if (cmd.equals("dodge")) {
            dmg = (int)(p.getAtk() * e.getDef() / 2);
        }
        if (dmg < 1) {
            dmg = 1;
        }
        return dmg;
    }
    
    /**
     * One line summary of players stats to add a little bit of sauciness
     *
     * @return concatanatanated stats text
     */
    private static String statsText() {
        int blocked = (int)Math.round((1 - player.getDef()) * 100);
        return "HP: " + player.getHp() + "/" + player.getMaxHp()
            + " | Attack: " + player.getAtk() + " | Defense: blocks "
            + blocked + "% of damage";
    }
}
 
