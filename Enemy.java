package dungeonCrawler;
public class Enemy extends Player{
    //~ Fields ................................................................
    private String name;
    //~ Constructors ..........................................................
    public Enemy() {
        super(0,0,0);
        //random hp, atk, def
        int maxHp=(int)(Math.random()*10)+5;
        this.changeMaxHp(maxHp);
        this.changeHp(maxHp);
        this.changeAtk((int)(Math.random()*10)+5);
        this.changeDef((int)(Math.random()*10)+5);
    }
    //~Public  Methods ........................................................
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
