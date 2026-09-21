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
        if (healRoll > .9) {
            healUp = 1.15;
        }
        else if (healRoll > rarity) {
            healUp = 1.1;
        }
        else {
            healUp = 1.05;
        }

        double atkRoll = Math.random();
        if (atkRoll > .9) {
            atkUp = 1.30;
        }
        else if (atkRoll > rarity) {
            atkUp = 1.20;
        }
        else {
            atkUp = 1.10;
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
        System.out.println("You have entered an Upgrade Room!");
        System.out.println("Select a stat to improve:");
        System.out.println("1. Max Health (x" + healUp + ")");
        System.out.println("2. Attack (x" + atkUp + ")");
        System.out.println("3. Defense (x" + defUp + ")");
        String input = in.nextLine().trim().toLowerCase();
        if (input.equals("Health")) {
            // Replace with your actual player method, e.g.:
            player.changeMaxHp((int)(player.getMaxHp() * healUp));
            player.changeHp((int)(player.getHp() * healUp));
            System.out.println("Your health has been increased!");
        }
        else if (input.equals("Attack")) {
            player.changeAtk((int)(player.getAtk() * atkUp));
            System.out.println("Your attack has been increased!");
        }
        else if (input.equals("Defense")) {
            player.changeDef((int)(player.getDef() * defUp));
            System.out.println("Your defense has been increased!");
        }
        return true;
    }
}
