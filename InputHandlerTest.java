package dungeonCrawler;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// -------------------------------------------------------------------------
/**
 *  JUnit tests for InputHandler's key methods, with one normal case and one
 *  bad-input case each. A Scanner over a plain String stands in for the
 *  console so the tests can feed scripted input.
 *
 *  @author group70
 */
public class InputHandlerTest {

    /**
     * Helper: the set of commands used in combat.
     */
    private Set<String> combatCommands() {
        Set<String> allowed = new HashSet<String>();
        allowed.add("attack");
        allowed.add("parry");
        allowed.add("dodge");
        allowed.add("heal");
        return allowed;
    }


    // ~ getValidatedCommand ...................................................
    @Test
    public void testGetValidatedCommandNormal() {
        InputHandler input = new InputHandler(new Scanner("attack\n"));
        String cmd = input.getValidatedCommand(combatCommands(), "prompt");
        assertEquals("attack", cmd);
    }


    @Test
    public void testGetValidatedCommandTrimsAndLowercases() {
        // Messy but recoverable input should still be accepted.
        InputHandler input = new InputHandler(new Scanner("   ATTACK  \n"));
        String cmd = input.getValidatedCommand(combatCommands(), "prompt");
        assertEquals("attack", cmd);
    }


    @Test
    public void testGetValidatedCommandBadInputRePrompts() {
        // Two garbage lines, then a real one: the handler must skip past the
        // garbage and hand back only the allowed value.
        InputHandler input = new InputHandler(new Scanner(
            "banana\n!!!\nheal\n"));
        String cmd = input.getValidatedCommand(combatCommands(), "prompt");
        assertEquals("heal", cmd);
    }


    @Test
    public void testGetValidatedCommandBadInputRunsOut() {
        // Nothing valid ever arrives, so the fallback is returned instead of
        // throwing NoSuchElementException.
        InputHandler input = new InputHandler(new Scanner("nope\nstill nope\n"));
        String cmd = input.getValidatedCommand(combatCommands(), "prompt",
            "attack");
        assertEquals("attack", cmd);
    }


    @Test
    public void testGetValidatedCommandNullAllowedSet() {
        // A caller passing no allowed commands gets the fallback, not a crash.
        InputHandler input = new InputHandler(new Scanner("attack\n"));
        assertNull(input.getValidatedCommand(null, "prompt"));
    }


    // ~ getYesNo ..............................................................
    @Test
    public void testGetYesNoNormal() {
        InputHandler yes = new InputHandler(new Scanner("yes\n"));
        assertTrue(yes.getYesNo("prompt"));

        InputHandler no = new InputHandler(new Scanner("n\n"));
        assertFalse(no.getYesNo("prompt"));
    }


    @Test
    public void testGetYesNoBadInput() {
        // "maybe" is not an answer, so it re-prompts and reads the next line.
        InputHandler input = new InputHandler(new Scanner("maybe\nYES\n"));
        assertTrue(input.getYesNo("prompt"));
    }


    @Test
    public void testGetYesNoRunsOut() {
        // Empty stream defaults to false rather than crashing.
        InputHandler input = new InputHandler(new Scanner(""));
        assertFalse(input.getYesNo("prompt"));
    }
}
