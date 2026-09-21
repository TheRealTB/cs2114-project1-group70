package dungeonCrawler;

import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 *  A handful of JUnit tests for Enemy's key methods. Enemy relies on
 *  Math.random() for its stats, so these tests check invariants (valid
 *  ranges, no crashes, correct clamping) rather than exact random values.
 *
 *  @author Asher
 *  @version Sep 21, 2026
 *  Written with assistance from Anthropic Sonnet 5
 */
public class EnemyTest {

    // ~ Constructor / getName .................................................
    @Test
    public void testConstructorAndGetNameNormal() {
        Enemy enemy = new Enemy(1, false);
        assertNotNull(enemy.getName());
        assertFalse(enemy.getName().isEmpty());
        assertTrue(enemy.isAlive());
        assertTrue(enemy.getMaxHp() >= 1);
        assertTrue(enemy.getAtk() >= 1);
    }


    @Test
    public void testConstructorBadInputNegativeDepth() {
        // A nonsensical negative depth shouldn't crash the constructor,
        // and Player's own clamping should keep stats valid (maxHp >= 1,
        // atk >= 1) even though depth is bad input.
        Enemy enemy = new Enemy(-100, true);
        assertNotNull(enemy.getName());
        assertTrue(enemy.getMaxHp() >= 1);
        assertTrue(enemy.getAtk() >= 1);
        assertTrue(enemy.getHp() >= 0);
    }


    // ~ attack ................................................................
    @Test
    public void testAttackNormal() {
        Enemy enemy = new Enemy(1, false);
        Player target = new Player(100, 10, 0.5);
        int result = enemy.attack(target, -20);
        assertEquals(80, result);
        assertEquals(80, target.getHp());
    }


    @Test
    public void testAttackBadInputHugeDamage() {
        // A ridiculously large damage value should still clamp the
        // target's hp at 0 rather than going negative.
        Enemy enemy = new Enemy(1, false);
        Player target = new Player(100, 10, 0.5);
        int result = enemy.attack(target, -999999);
        assertEquals(0, result);
        assertFalse(target.isAlive());
    }


    // ~ takeDamage ............................................................
    @Test
    public void testTakeDamageNormal() {
        Enemy enemy = new Enemy(1, false);
        int startingHp = enemy.getHp();
        int result = enemy.takeDamage(-1);
        assertEquals(Math.max(startingHp - 1, 0), result);
        assertEquals(result, enemy.getHp());
    }


    @Test
    public void testTakeDamageBadInputHugeDamage() {
        // A huge negative change should clamp the enemy's own hp at 0,
        // not go negative or crash.
        Enemy enemy = new Enemy(1, false);
        int result = enemy.takeDamage(-999999);
        assertEquals(0, result);
        assertFalse(enemy.isAlive());
    }
}
