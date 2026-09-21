package dungeonCrawler;

// -------------------------------------------------------------------------
/**
 *  Builds the next Room given the current depth. Room.go() calls this when
 *  the player walks through an exit whose room has not been generated yet.
 *
 *  Does not run combat and does not know how the player got here.
 *
 *  @author group70
 *  @version Sep 21, 2026
 */
public class DungeonGenerator {
    // ~ Fields ................................................................
    private static final double UPGRADE_ROOM_CHANCE = 0.25;

    // ~Public Methods ........................................................
    /**
     * Picks a room type for the given depth. Every tenth depth is forced to
     * be a boss encounter; otherwise there is a chance of an upgrade room and
     * the rest are normal encounters.
     *
     * @param depth how deep in the dungeon the new room sits
     * @return the generated room
     */
    public static Room makeRoom(int depth) {
        if (depth < 0) {
            depth = 0;
        }
        boolean bossDepth = depth > 0 && depth % 10 == 0;
        if (!bossDepth && Math.random() < UPGRADE_ROOM_CHANCE) {
            return new UpgradeRoom(depth);
        }
        return EncounterRoom.generate(depth);
    }
}
