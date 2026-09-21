package dungeonCrawler;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

// -------------------------------------------------------------------------
/**
 *  A handful of JUnit tests for Player's key methods. For each method there
 *  is one normal-case test and one bad-input / boundary-case test to make
 *  sure Player never crashes and never leaves its stats out of range.
 *
 *  @author Asher
 *  @version Sep 21, 2026
  *  Written with assistance from Anthropic Sonnet 5
 */
public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        // maxHp = 100, atk = 10, def = 0.5
        player = new Player(100, 10, 0.5);
    }


    // ~ Constructor / getters ................................................
    @Test
    public void testConstructorNormal() {
        assertEquals(100, player.getMaxHp());
        assertEquals(100, player.getHp());
        assertEquals(10, player.getAtk());
        assertEquals(0.5, player.getDef(), 0.0001);
    }


    @Test
    public void testConstructorBadInput() {
        // Negative/zero starting stats shouldn't crash the constructor.
        Player badPlayer = new Player(-50, -5, -1.0);
        assertEquals(-50, badPlayer.getMaxHp());
        assertEquals(-50, badPlayer.getHp());
        assertEquals(-5, badPlayer.getAtk());
        assertEquals(-1.0, badPlayer.getDef(), 0.0001);
    }


    // ~ isAlive ...............................................................
    @Test
    public void testIsAliveNormal() {
        assertTrue(player.isAlive());
    }


    @Test
    public void testIsAliveBadInput() {
        // Drive hp down with a huge negative change; isAlive should report
        // false once hp bottoms out at 0, not crash or go negative.
        player.changeHp(-999999);
        assertFalse(player.isAlive());
        assertEquals(0, player.getHp());
    }


    // ~ changeMaxHp ...........................................................
    @Test
    public void testChangeMaxHpNormal() {
        int result = player.changeMaxHp(20);
        assertEquals(120, result);
        assertEquals(120, player.getMaxHp());
    }


    @Test
    public void testChangeMaxHpBadInput() {
        // A huge negative change should clamp maxHp at 1, never below.
        int result = player.changeMaxHp(-999999);
        assertEquals(1, result);
        assertEquals(1, player.getMaxHp());
    }


    // ~ changeHp ..............................................................
    @Test
    public void testChangeHpNormal() {
        int result = player.changeHp(-30);
        assertEquals(70, result);
        assertEquals(70, player.getHp());
    }


    @Test
    public void testChangeHpBadInput() {
        // Healing far past maxHp should clamp at maxHp, not overflow past it.
        int result = player.changeHp(999999);
        assertEquals(player.getMaxHp(), result);

        // Damaging far past 0 should clamp at 0, never go negative.
        int result2 = player.changeHp(-999999);
        assertEquals(0, result2);
    }


    // ~ changeAtk .............................................................
    @Test
    public void testChangeAtkNormal() {
        int result = player.changeAtk(5);
        assertEquals(15, result);
        assertEquals(15, player.getAtk());
    }


    @Test
    public void testChangeAtkBadInput() {
        // Attack should never drop below 1, even with a huge negative change.
        int result = player.changeAtk(-999999);
        assertEquals(1, result);
        assertEquals(1, player.getAtk());
    }


    // ~ changeDef .............................................................
    @Test
    public void testChangeDefNormal() {
        double result = player.changeDef(0.1);
        assertEquals(0.6, result, 0.0001);
        assertEquals(0.6, player.getDef(), 0.0001);
    }


    @Test
    public void testChangeDefBadInput() {
        // Def should clamp to the [0.01, 0.99] range regardless of how
        // extreme the change is.
        double low = player.changeDef(-999999);
        assertEquals(0.01, low, 0.0001);

        double high = player.changeDef(999999);
        assertEquals(0.99, high, 0.0001);
    }


    // ~ addHealPotions ........................................................
    @Test
    public void testAddHealPotionsNormal() {
        int result = player.addHealPotions(3);
        assertEquals(3, result);
    }


    @Test
    public void testAddHealPotionsBadInput() {
        // Adding way more than the cap of 10 should clamp at 10, not
        // overflow past it.
        int result = player.addHealPotions(999999);
        assertEquals(10, result);
    }


    // ~ useHealPotion .........................................................
    @Test
    public void testUseHealPotionNormal() {
        player.changeHp(-50); // hp is now 50/100
        player.addHealPotions(1);
        player.useHealPotion();
        assertEquals(60, player.getHp());
    }


    @Test
    public void testUseHealPotionBadInput() {
        // Using a potion with none available should do nothing and must
        // not crash or drive potions negative.
        player.changeHp(-50); // hp is now 50/100
        player.useHealPotion();
        assertEquals(50, player.getHp());
    }
}
