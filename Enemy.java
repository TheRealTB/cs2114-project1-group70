package dungeonCrawler;
 
// -------------------------------------------------------------------------

public class Enemy extends Player {
    // ~ Fields ................................................................
    private String[] names = { "Goblin", "Cave Bat", "Skeleton", "Slime",
        "Mutated Spider", "Stone Golem", "Wraith" };
    private String[] bossNames = { "Gribble the Hoard-Keeper",
        "Echo-Lord Chiroptera", "General Karkas the Unyielding",
        "The Colossal Ooze of Depths", "Weaver of Tangled Veins",
        "Monolith the Immovable", "Malakor the Soul-Render" };
    private String name;
 
    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Makes a normal enemy at depth 0.
     */
    public Enemy() {
        this(0, false);
    }
 
 
    // ----------------------------------------------------------
    /**
     * Makes an enemy with stats scaled for the given depth.
     *
     * @param depth how deep in the dungeon the enemy is (negative becomes 0)
     * @param boss true to make a stronger boss enemy
     */
    public Enemy(int depth, boolean boss) {
        // all Player stats start at 0, so the change methods below set them
        super(0, 0, 0);
        if (depth < 0) {
            depth = 0;
        }
 
        // random stats that grow with depth
        int maxHp = (int)(Math.random() * (2 + depth)) + 5;
        int atk = (int)(Math.random() * (1 + depth)) + 5;
        // def is a damage multiplier like the player's (0.9 = takes 90%
        // damage), so lower is harder. Deeper enemies roll lower.
        double def = 0.99 - Math.random() * (0.01 * depth);
 
        if (boss) {
            this.name = bossNames[(int)(Math.random() * bossNames.length)];
            maxHp = maxHp * 3;
            atk = atk + 3;
            def = def - 0.1;
        }
        else {
            this.name = names[(int)(Math.random() * names.length)];
        }
 
        // keep def from getting too low deep in the dungeon
        if (def < 0.3) {
            def = 0.3;
        }
 
        this.changeMaxHp(maxHp);
        this.changeHp(maxHp);
        this.changeAtk(atk);
        this.changeDef(def);
    }
 
 
    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Gets the enemy's name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
 
 
    // ----------------------------------------------------------
    /**
     * Hits the player.
     *
     * @param player the player
     * @param change how much damage to deal
     * @return the player's hp after the hit
     */
    public int attack(Player player, int change) {
        player.changeHp(-Math.abs(change));
        return player.getHp();
    }
 
 
    // ----------------------------------------------------------
    /**
     * Lowers this enemy's hp.
     *
     * @param change how much damage to take
     * @return this enemy's hp after the hit
     */
    public int takeDamage(int change) {
        this.changeHp(-Math.abs(change));
        return this.getHp();
    }
}
