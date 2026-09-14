package dungeonCrawler;
import java.util.Scanner;
public class EncounterRoom extends Room {
    //~ Fields ................................................................
    private int numEnemies;
    private Enemy[] enemies;
    
    //~ Constructors ..........................................................
    public EncounterRoom() {
        this(0, false);
    }
    
    public EncounterRoom(int depth, boolean boss) {
        super();
        setDepth(depth);
        if (boss) {
            numEnemies = 1;
        } else {
            numEnemies = (int)(Math.random()*3)+3; //randomly fill room with 3-5 enemies
        }
        enemies=new Enemy[numEnemies];
        for(int i = 0; i < numEnemies; i++) {
            enemies[i] = new Enemy(depth, boss);
        }
    }
    //~Public  Methods ........................................................
    public String getRoomType() {
        return "Encounter Room";
    }

    public boolean enter(Player player, Scanner in) {
        if(isCleared()) {
            System.out.println("This room is already cleared.");
            return true;
        }
        System.out.println(look());

        for(int i = 0; i < numEnemies && player.isAlive(); i++) {
            Enemy e = enemies[i];
            if (e == null || !e.isAlive()) {
                continue;
            }
            System.out.println("Fighting!" + e.getName() + "HP" + e.getHp());
            while (player.isAlive() && e.isAlive()) {
                System.out.println(player.status());
                System.out.println("attack / parry / dodge / heal");
                String cmd = in.nextLine().trim().toLowerCase();

                if (cmd.equals("heal")) {
                    if (player.usePotion()) {
                        System.out.println("Healed. HP " + player.getHp());
                    } else {
                        System.out.println("No potions.");
                    }
                } else if (cmd.equals("attack") || cmd.equals("parry")
                        || cmd.equals("dodge")) {
                    int dmg = player.getAtk();
                    if (cmd.equals("parry")) {
                        dmg = player.getAtk() / 2 + 2;
                    }
                    if (cmd.equals("dodge")) {
                        dmg = player.getAtk() / 2;
                    }
                    if (dmg < 1) {
                        dmg = 1;
                    }
                    e.takeDamage(-dmg);
                    System.out.println("You deal " + dmg);
                } else {
                    System.out.println("Invalid command.");
                    continue;
                }

                if (e.isAlive() && player.isAlive()) {
                    int hit = player.incomingDamage(e.getAtk());
                    e.attack(player, -hit);
                    System.out.println(e.getName() + " hits you for " + hit);
                }
            }
        }

        if (!player.isAlive()) {
            System.out.println("You died.");
            return false;
        }

        setCleared(true);
        player.addHealPotions(1);
        System.out.println("Cleared. +1 potion. Upgrade: hp / atk / def / skip");
        String u = in.nextLine().trim().toLowerCase();
        if (u.equals("hp")) {
            player.changeMaxHp(4);
            player.changeHp(4);
        } else if (u.equals("atk")) {
            player.changeAtk(2);
        } else if (u.equals("def")) {
            player.changeDef(2);
        }
        return true;
    }

}





    
}

