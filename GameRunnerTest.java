package dungeonCrawler;
 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
 
// -------------------------------------------------------------------------
/**
 * Tests for GameRunner.
 *
 * @author Will
 * @version Sep 21, 2026
 */
public class GameRunnerTest {
  
    // ----------------------------------------------------------
    /**
     * Normal case
     */
    @Test
    public void testAskChoiceNormal() {
        Scanner input = new Scanner("yes\n");
        GameRunner.setScanner(input);
        String[] options = { "yes", "no" };
 
        String answer = GameRunner.askChoice("Play? (yes / no)", options);
 
        assertEquals("yes", answer);
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input
     */
    @Test
    public void testAskChoiceCapitalsAndSpaces() {
        Scanner input = new Scanner("   YES   \n");
        GameRunner.setScanner(input);
        String[] options = { "yes", "no" };
 
        String answer = GameRunner.askChoice("Play? (yes / no)", options);
 
        assertEquals("yes", answer);
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input
     */
    @Test
    public void testAskChoiceWrongThenRight() {
        Scanner input = new Scanner("maybe\nno\n");
        GameRunner.setScanner(input);
        String[] options = { "yes", "no" };
 
        String answer = GameRunner.askChoice("Play? (yes / no)", options);
 
        assertEquals("no", answer);
    }
 
 
    
 
    // ----------------------------------------------------------
    /**
     * Normal case 
     */
    @Test
    public void testWaitForCommandNormal() {
        Scanner input = new Scanner("attack\nleftover\n");
        GameRunner.setScanner(input);
 
        GameRunner.waitForCommand("attack");
 
        // it should have read only "attack"
        assertEquals("leftover", input.nextLine());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input 
     */
    @Test
    public void testWaitForCommandWrongWords() {
        Scanner input = new Scanner("hello\nheal\nattack\nleftover\n");
        GameRunner.setScanner(input);
 
        GameRunner.waitForCommand("attack");
 
        assertEquals("leftover", input.nextLine());
    }
 
 
    // A depth 0 enemy always has def 0.99, so these numbers never change.
 
    // ----------------------------------------------------------
    /**
     * Normal case 
     */
    @Test
    public void testPlayerDamageAttack() {
        Player p = new Player(100, 3, .9);
        Enemy e = new Enemy(0, false);
 
        assertEquals(2, GameRunner.playerDamage(p, e, "attack"));
    }
 
 
    // ----------------------------------------------------------
    /**
    * tests
     */
    @Test
    public void testPlayerDamageParry() {
        Player p = new Player(100, 3, .9);
        Enemy e = new Enemy(0, false);
 
        assertEquals(3, GameRunner.playerDamage(p, e, "parry"));
    }
 
 
    // ----------------------------------------------------------
    /**
     * Normal case
     */
    @Test
    public void testPlayerDamageDodge() {
        Player p = new Player(100, 3, .9);
        Enemy e = new Enemy(0, false);
 
        assertEquals(1, GameRunner.playerDamage(p, e, "dodge"));
    }
 
 
    // ----------------------------------------------------------
    /**
     * tests case.
     */
    @Test
    public void testPlayerDamageAtLeastOne() {
        Player p = new Player(100, 1, .9);
        Enemy e = new Enemy(0, false);
 
        assertEquals(1, GameRunner.playerDamage(p, e, "dodge"));
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input
     */
    @Test
    public void testPlayerDamageUnknownCommand() {
        Player p = new Player(100, 3, .9);
        Enemy e = new Enemy(0, false);
 
        assertEquals(2, GameRunner.playerDamage(p, e, "banana"));
    }
 
 
    // ~ statsText .............................................................
 
    // ----------------------------------------------------------
    /**
     * Normal case
     */
    @Test
    public void testStatsTextNormal() {
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        assertEquals("HP: 100/100 | Attack: 3 | Defense: blocks 10% of damage",
            GameRunner.statsText());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Normal case
     */
    @Test
    public void testStatsTextAfterDamage() {
        Player p = new Player(100, 3, .9);
        p.changeHp(-30);
        GameRunner.setPlayer(p);
 
        assertEquals("HP: 70/100 | Attack: 3 | Defense: blocks 10% of damage",
            GameRunner.statsText());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad case
     */
    @Test
    public void testStatsTextNoPlayer() {
        GameRunner.setPlayer(null);
 
        assertEquals("No player.", GameRunner.statsText());
    }
 
 
    // ~ runTutorial ...........................................................
 
    // ----------------------------------------------------------
    /**
     * Normal case
     */
    @Test
    public void testRunTutorialNormal() {
        Scanner input = new Scanner(
            "attack\nparry\ndodge\nheal\nleftover\n");
        GameRunner.setScanner(input);
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        GameRunner.runTutorial();
 
        // it should have read exactly the 4 commands
        assertEquals("leftover", input.nextLine());
        // the tutorial uses a practice player, so the real one is untouched
        assertEquals(100, GameRunner.getPlayer().getHp());
        assertEquals(3, GameRunner.getPlayer().getAtk());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input
     */
    @Test
    public void testRunTutorialBadInput() {
        Scanner input = new Scanner(
            "heal\nhello\nattack\nparry\ndodge\nheal\nleftover\n");
        GameRunner.setScanner(input);
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        GameRunner.runTutorial();
 
        assertEquals("leftover", input.nextLine());
        assertEquals(100, GameRunner.getPlayer().getHp());
    }
 
.
 
    // ----------------------------------------------------------
    /**
     * Bad case
     */
    @Test
    public void testRunDungeonLoopDeadPlayer() {
        Scanner input = new Scanner("leftover\n");
        GameRunner.setScanner(input);
        Player p = new Player(100, 3, .9);
        p.changeHp(-100);
        GameRunner.setPlayer(p);
 
        GameRunner.runDungeonLoop();
 
        assertEquals(1, GameRunner.getDepth());
        assertFalse(GameRunner.getPlayer().isAlive());
        // no input should have been read
        assertEquals("leftover", input.nextLine());
    }
 
 
    // ~ endGame ...............................................................
 
    // ----------------------------------------------------------
    /**
     * Normal case: depth 10 should show the results.
     */
    @Test
    public void testEndGameNormal() {
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        assertTrue(GameRunner.endGame(10));
    }
 
 
    // ----------------------------------------------------------
    /**
     * testing testing 123
     */
    @Test
    public void testEndGameZero() {
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        assertTrue(GameRunner.endGame(0));
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad case
     */
    @Test
    public void testEndGameNegative() {
        GameRunner.setPlayer(new Player(100, 3, .9));
 
        assertFalse(GameRunner.endGame(-1));
    }
}
