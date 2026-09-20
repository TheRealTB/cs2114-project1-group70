package dungeonCrawler;

import java.util.Scanner;

public class UpgradeRoom extends Room {
    private double healUp;
    private double defUp;
    private double atkUp;
    // ~ Constructors ..........................................................
    public UpgradeRoom() {
        this(0);
    }


    public UpgradeRoom(int depth) {
        super();
        setDepth(depth);
        double rarity = Math.random();
        double healRoll = Math.random();
        if(healRoll>.9) {
            healUp = 1.15;
        }else if(healRoll>rarity) {
            healUp = 1.1;
        }else {
            healUp = 1.05;
        }
        
        double atkRoll = Math.random();
        if(atkRoll>.9) {
            atkUp = 1.30;
        }else if(atkRoll>rarity) {
            atkUp = 1.20;
        }else {
            atkUp = 1.10;
        }
        
        double defRoll = Math.random();
        if(defRoll>.9) {
            defUp = .85;
        }else if(defRoll>rarity) {
            defUp = .9;
        }else {
            defUp = .95;
        }
        
    }


    // ~Public Methods ........................................................
    public String getRoomType() {
        return "Upgrade Room";
    }


    public boolean enter(Player player, Scanner in) {
        //plyaer enters and gets to choose between hp, def, or atk increase.
    }
}
