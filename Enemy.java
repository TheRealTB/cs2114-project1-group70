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
