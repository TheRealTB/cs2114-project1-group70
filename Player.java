package dungeonCrawler;

public class Player {
    // ~ Fields ................................................................
    private int maxHp;
    private int hp;
    private int atk;
    private double def;
    // etc...

    // ~ Constructors ..........................................................
    public Player(int maxHp, int atk, double def) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.atk = atk;
        this.def = def;
    }
    // ~Public Methods ........................................................


    // getters
    public int getHp() {
        return hp;
    }


    public int getAtk() {
        return atk;
    }


    public double getDef() {
        return def;
    }
    
    public boolean isAlive() {
        return this.hp>0;
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
}
