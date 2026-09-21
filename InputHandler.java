package dungeonCrawler;

import java.util.Scanner;
import java.util.Set;

// -------------------------------------------------------------------------
/**
 *  Reads raw console input and guarantees the caller only ever receives a
 *  value from an allowed set. Does not know what the input means in-game.
 *
 *  Every read is trimmed and lowercased, and anything outside the allowed
 *  set causes a re-prompt rather than a crash. If the input stream runs dry
 *  the caller gets a fallback value instead of a NoSuchElementException.
 *
 *  @author group70
 *  @version Sep 21, 2026
 */
public class InputHandler {
    // ~ Fields ................................................................
    private Scanner scanner;

    // ~ Constructors ..........................................................
    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }


    // ~Public Methods ........................................................
    /**
     * Hands back the underlying Scanner, since Room.enter(Player, Scanner)
     * still reads from a Scanner directly.
     *
     * @return the wrapped scanner
     */
    public Scanner getScanner() {
        return scanner;
    }


    /**
     * Keeps prompting until the player types something in allowedCommands.
     * Returns null if the stream runs out and no fallback was given.
     *
     * @param allowedCommands the only values that will be accepted
     * @param prompt the text shown to the player each attempt
     * @return a value guaranteed to be inside allowedCommands
     */
    public String getValidatedCommand(Set<String> allowedCommands,
        String prompt) {
        return getValidatedCommand(allowedCommands, prompt, null);
    }


    /**
     * Same as getValidatedCommand(Set, String), but returns fallback if the
     * input stream ends, so a closed console or a scripted test cannot crash
     * the game.
     *
     * @param allowedCommands the only values that will be accepted
     * @param prompt the text shown to the player each attempt
     * @param fallback returned if there is no more input to read
     * @return an allowed value, or fallback if input ran out
     */
    public String getValidatedCommand(Set<String> allowedCommands,
        String prompt, String fallback) {
        if (allowedCommands == null || allowedCommands.isEmpty()) {
            return fallback;
        }
        while (true) {
            System.out.println(prompt);
            if (scanner == null || !scanner.hasNextLine()) {
                return fallback;
            }
            String cmd = clean(scanner.nextLine());
            if (allowedCommands.contains(cmd)) {
                return cmd;
            }
            System.out.println("'" + cmd + "' is not one of " + allowedCommands
                + " - try again.");
        }
    }


    /**
     * Reads a yes/no answer, accepting y, yes, n, and no in any casing.
     * Returns false if the input stream ends.
     *
     * @param prompt the question shown to the player
     * @return true for yes, false for no
     */
    public boolean getYesNo(String prompt) {
        while (true) {
            System.out.println(prompt + " (yes/no)");
            if (scanner == null || !scanner.hasNextLine()) {
                return false;
            }
            String cmd = clean(scanner.nextLine());
            if (cmd.equals("y") || cmd.equals("yes")) {
                return true;
            }
            if (cmd.equals("n") || cmd.equals("no")) {
                return false;
            }
            System.out.println("Please answer yes or no.");
        }
    }


    // ~Private Methods .......................................................
    /**
     * trim() clears out stray spaces and toLowerCase() means the comparison
     * never depends on how the player capitalized things.
     */
    private String clean(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.trim().toLowerCase();
    }
}
