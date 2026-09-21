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
    public Enemy() {
        this(0, false);
    }


    public Enemy(int depth, boolean boss) {
        // makes an enemy with modifiers to stats for the depth
        super(0, 0, 0);
        int rolledHp = (int)(Math.random() * (2 + depth)) + 5;
        int rolledAtk = (int)(Math.random() * (1 + depth)) + 5;

        // def is a damage multiplier (see Player.changeDef): it is the
        // fraction of incoming damage this enemy takes, so LOWER is tougher
        // and it has to stay inside 0.01 - 0.99. That is why we scale a
        // decimal here instead of passing a whole number, which would just
        // get clamped to 0.99 every time.
        double rolledDef = 0.95 - (Math.random() * 0.05) - (depth * 0.01);

        if (boss) {
            this.name = bossNames[(int)(Math.random() * bossNames.length)];
            // a boss has to actually be stronger than a normal enemy
            rolledHp = rolledHp * 3;
            rolledAtk = rolledAtk * 2;
            rolledDef = rolledDef - 0.10;
        }
        else {
            this.name = names[(int)(Math.random() * names.length)];
        }

        this.changeMaxHp(rolledHp);
        this.changeHp(rolledHp);
        this.changeAtk(rolledAtk);
        this.changeDef(rolledDef);
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
