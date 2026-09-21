package dungeonCrawler;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        // player enters and gets to choose between hp, def, or atk increase.
        if (isCleared()) {
            System.out.println("This room is already cleared.");
            return true;
        }
        System.out.println(look());
        System.out.println(player.status());

        Set<String> allowed = new HashSet<String>();
        allowed.add("health");
        allowed.add("attack");
        allowed.add("defense");
        allowed.add("skip");

        InputHandler input = new InputHandler(in);
        String cmd = input.getValidatedCommand(allowed,
            "You are offered a choice: health / attack / defense / skip",
            "skip");

        if (cmd.equals("health")) {
            // healUp is a multiplier (1.05 - 1.15) but changeMaxHp is
            // additive, so the percentage has to become a flat gain first.
            int gain = (int)(player.getMaxHp() * (healUp - 1));
            if (gain < 1) {
                gain = 1;
            }
            player.changeMaxHp(gain);
            player.changeHp(gain);
            System.out.println("Max HP +" + gain + ".  " + player.status());
        }
        else if (cmd.equals("attack")) {
            int gain = (int)(player.getAtk() * (atkUp - 1));
            if (gain < 1) {
                gain = 1;
            }
            player.changeAtk(gain);
            System.out.println("Attack +" + gain + ".  " + player.status());
        }
        else if (cmd.equals("defense")) {
            // defUp is a multiplier below 1, and a lower def means less
            // damage taken, so the additive change has to be negative.
            player.changeDef(player.getDef() * (defUp - 1));
            System.out.println("Defense improved.  " + player.status());
        }
        else {
            System.out.println("You leave the offering untouched.");
        }

        setCleared(true);
        return true;
    }
}
