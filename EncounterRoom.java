package dungeonCrawler;

import java.util.Scanner;

public class UpgradeRoom extends Room {
    private int healUp;
    private double defUp;
    private int atkUp;

    // ~ Constructors ..........................................................
    public UpgradeRoom() {
        this(0);
    }


    public UpgradeRoom(Player player, int depth) {
        super();
        setDepth(depth);
        double rarity = Math.random();
        double healRoll = Math.random();
        if (healRoll > .9) {
            healUp = (int)(player.getMaxHp()*.15);
        }
        else if (healRoll > rarity) {
            healUp = (int)(player.getMaxHp()*.1);
        }
        else {
            healUp = (int)(player.getMaxHp()*.05);
        }

        double atkRoll = Math.random();
        if (atkRoll > .9) {
            atkUp = (int)(player.getAtk()*.3);
        }
        else if (atkRoll > rarity) {
            atkUp = (int)(player.getAtk()*.2);
        }
        else {
            atkUp = (int)(player.getAtk()*.1);
        }

        double defRoll = Math.random();
        if (defRoll > .9) {
            defUp = .85;
        }
        else if (defRoll > rarity) {
            defUp = .9;
        }
        else {
            defUp = .95;
        }

    }


    // ~Public Methods ........................................................
    public String getRoomType() {
        return "Upgrade Room";
    }


    public boolean enter(Player player, Scanner in) {
        // plyaer enters and gets to choose between hp, def, or atk increase.
        boolean clear = false;
        System.out.println("You are offered a choice:");
        System.out.println("Health / Attack / Defense");
        String cmd = in.nextLine().trim().toLowerCase();
        
        if(cmd.equals("Health")) {
            player.changeMaxHp(healUp);
            player.changeHp(healUp);
        }else if(cmd.equals("Attack")) {
            player.changeAtk(atkUp);
        }else if(cmd.equals("Defense")) {
            player.changeDef(defUp);
        }
        clear = true;
        return clear;
    }
}
