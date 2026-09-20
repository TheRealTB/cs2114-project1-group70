package dungeonCrawler;

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
    public Enemy(int depth, boolean boss) {
        // makes an enemy with modifiers to stats for the depth
        super(0, 0, 0);
        if (boss) {
            this.name = (bossNames[(int)(Math.random()*7)]);
            int maxHp = (int)(Math.random() * (2 + depth)) + 5;
            this.changeMaxHp(maxHp);
            this.changeHp(maxHp);
            this.changeAtk((int)(Math.random() * (1 + depth)) + 5);
            this.changeDef((int)(Math.random() * (1 + depth)) + 5);
        }
        else {
            this.name = (names[(int)(Math.random() * 7)]);
            int maxHp = (int)(Math.random() * (2 + depth)) + 5;
            this.changeMaxHp(maxHp);
            this.changeHp(maxHp);
            this.changeAtk((int)(Math.random() * (1 + depth)) + 5);
            this.changeDef((int)(Math.random() * (1 + depth)) + 5);
        }
    }


    // ~Public Methods ........................................................
    public String getName() {
        return this.name;
    }


    public int attack(Player player, int change) {
        player.changeHp(change);
        return player.getHp();
    }


    public int takeDamage(int change) {
        this.changeHp(change);
        return this.getHp();
    }
}

// TODO (teammate): Finish Enemy. It must extend Player, have Enemy() and
// Enemy(int depth, boolean boss), set a name plus random hp/atk/def (boss
// should be stronger and stats should scale with depth), and provide
// getName(), attack(Player, int), and takeDamage(int). EncounterRoom already
// calls new Enemy(depth, boss), e.getName(), e.attack(...), and e.takeDamage(...).
// noted by Sophy
