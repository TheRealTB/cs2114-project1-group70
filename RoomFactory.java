
package dungeonCrawler;
 
// -------------------------------------------------------------------------
/**
 * Makes the rooms for the dungeon
 *
 * @author Will
 * @version Sep 21, 2026
 */
public class RoomFactory {
    // ~ Fields ................................................................
    private static final double ENCOUNTER_CHANCE = 0.7;
 
    // ----------------------------------------------------------
    /**
     * Makes a new room for the given depth.
     *
     * @param depth deepness
     * @return EncounterRoom or UpgradeRoom
     */
    public static Room makeRoom(int depth) {
        if (depth < 0) {
            depth = 0;
        }
 
        if (depth > 0 && depth % 10 == 0) {
            return EncounterRoom.generate(depth);
        }
         if (Math.random() < ENCOUNTER_CHANCE) {
            return EncounterRoom.generate(depth);
        }
        return new UpgradeRoom(depth);
    }
}
