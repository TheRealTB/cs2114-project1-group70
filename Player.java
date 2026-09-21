package dungeonCrawler;

public class Player {
    // ~ Fields ................................................................
    private int maxHp;
    private int hp;
    private int atk;
    private double def;
    private int healPotions;
    // etc...

    // ~ Constructors ..........................................................
    public Player(int maxHp, int atk, double def) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.atk = atk;
        this.def = def;
        this.healPotions = 0;
    }
    // ~Public Methods ........................................................


    // getters
    public int getHp() {
        return hp;
    }
    
    public int getMaxHp() {
        return maxHp;
    }


    public int getAtk() {
        return atk;
    }


    public double getDef() {
        return def;
    }


    public boolean isAlive() {
        return this.hp > 0;
    }


    // setters
    public int changeMaxHp(int change) {
        if (maxHp + change < 1) {
            maxHp = 1;
        }
        else {
            maxHp += change;
        }
        return maxHp;
    }


    public int changeHp(int change) {
        if (hp + change < 0) {
            hp = 0;
        }
        else if (hp + change > maxHp) {
            hp = maxHp;
        }
        else {
            hp += change;
        }
        return hp;
    }


    public int changeAtk(int change) {
        if (atk + change < 1) {
            atk = 1;
        }
        else {
            atk += change;
        }
        return atk;
    }


    public double changeDef(double change) {
        if (def + change < 0.01) {
            def = 0.01;
        }
        else if (def + change > 0.99) {
            def = 0.99;
        }
        else {
            def += change;
        }
        return def;
    }
    
    public int addHealPotions(int c) {
        if(c+healPotions>10) {
            healPotions = 10;
        }else {
            healPotions+=c;
        }
        return healPotions;
    }
    
    public int getHealPotions() {
        return healPotions;
    }


    /**
     * Uses one heal potion. Returns true if a potion was actually spent and
     * false if there were none left, which is how EncounterRoom knows whether
     * to print "No potions."
     */
    public boolean usePotion() {
        if (healPotions > 0) {
            healPotions--;
            this.changeHp(10);
            return true;
        }
        return false;
    }


    public void useHealPotion() {
        usePotion();
    }


    /**
     * One-line readout of the player's current stats, printed each combat
     * turn by EncounterRoom.
     */
    public String status() {
        return "HP " + hp + "/" + maxHp + "   ATK " + atk + "   DEF "
            + String.format("%.2f", def) + "   Potions " + healPotions;
    }
}
