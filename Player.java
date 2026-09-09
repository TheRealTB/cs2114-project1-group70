package dungeonCrawler;
public class Player {
    //~ Fields ................................................................
    private int maxHp;
    private int hp;
    private int atk;
    private int def;
    //etc...
    
    //~ Constructors ..........................................................
    public Player(int maxHp, int atk, int def) {
        this.maxHp=maxHp;
        this.hp=maxHp;
        this.atk=atk;
        this.def=def;
    }
    //~Public  Methods ........................................................
    
    //getters
    public int getHp() {
        return hp;
    }
    public int getAtk() {
        return atk;
    }
    public int getDef() {
        return def;
    }
    //setters
    public int changeMaxHp(int change) {
        if(maxHp+change<1) {
            maxHp=1;
        }else {
            maxHp+=change;
        }
        return maxHp;
    }
    public int changeHp(int change) {
        if(hp+change<0) {
            hp=0;
        }else if(hp+change>maxHp) {
            hp=maxHp;
        }else {
            hp+=change;
        }
        return hp;
    }
    public int changeAtk(int change) {
        if(atk+change<1) {
            atk=1;
        }else {
            atk+=change;
        }
        return atk;
    }
    public int changeDef(int change) {
        if(def+change<1) {
            def=1;
        }else {
            def+=change;
        }
        return def;
    }
}
