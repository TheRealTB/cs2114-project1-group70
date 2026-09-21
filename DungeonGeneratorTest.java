package dungeonCrawler;

import static org.junit.Assert.*;
import org.junit.Test;

// -------------------------------------------------------------------------
/**
 *  JUnit tests for DungeonGenerator.makeRoom, with a normal case and a
 *  bad-input case. Room type is random, so these check the guarantees that
 *  must hold every time rather than a specific room.
 *
 *  @author group70
 */
public class DungeonGeneratorTest {

    @Test
    public void testMakeRoomNormal() {
        Room room = DungeonGenerator.makeRoom(3);
        assertNotNull(room);
        assertEquals(3, room.getDepth());
        assertFalse(room.isCleared());
        // every room must always have a forward exit to descend through
        assertTrue(room.hasExit("forward"));
    }


    @Test
    public void testMakeRoomBossDepthIsEncounter() {
        // Every tenth depth is forced to be a boss fight, never a shop.
        Room room = DungeonGenerator.makeRoom(10);
        assertNotNull(room);
        assertEquals(10, room.getDepth());
        assertTrue(room instanceof EncounterRoom);
    }


    @Test
    public void testMakeRoomBadInputNegativeDepth() {
        // A nonsensical negative depth is clamped to 0 instead of producing a
        // broken room or crashing.
        Room room = DungeonGenerator.makeRoom(-5);
        assertNotNull(room);
        assertEquals(0, room.getDepth());
    }


    @Test
    public void testMakeRoomBadInputDirectionReturnsNull() {
        // Room.go should hand back null for a direction that is not a real
        // exit, which is how GameRunner detects a blocked path.
        Room room = DungeonGenerator.makeRoom(1);
        assertNull(room.go("northeast", 2));
        assertFalse(room.hasExit("northeast"));
    }
}
