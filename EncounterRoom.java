package dungeonCrawler;

import java.util.Scanner;

public class EncounterRoom extends Room {
    // ~ Fields ................................................................
    private int numEnemies;
    private Enemy[] enemies;

    // ~ Constructors ..........................................................
    public EncounterRoom() {
        this(0, false);
    }

    public EncounterRoom(int depth, boolean boss) {
        this(makeEnemies(depth, boss));
        setDepth(depth);
    }

    public EncounterRoom(Enemy[] pack) {
        super();
        if (pack == null || pack.length == 0) {
            pack = makeEnemies(0, false);
        }
        this.enemies = pack;
        this.numEnemies = pack.length;
    }

    public static EncounterRoom generate(int depth) {
        boolean boss = depth > 0 && depth % 10 == 0;
        EncounterRoom room = new EncounterRoom(depth, boss);
        room.setDepth(depth);
        return room;
    }

    private static Enemy[] makeEnemies(int depth, boolean boss) {
        int n = boss ? 1: (int)(Math.random() * 3) + 3;
        Enemy[] pack = new Enemy[n];
        for (int i = 0; i < n; i++) {
            pack[i] = new Enemy(depth, boss);
        }
        return pack;
    }
    

    // ~Public Methods ........................................................
    public String getRoomType() {
        return "Encounter Room";
    }


    public boolean enter(Player player, Scanner in) {
        if (isCleared()) {
            System.out.println("This room is already cleared.");
            return true;
        }
        System.out.println(look());

        for (int i = 0; i < numEnemies && player.isAlive(); i++) {
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
                    }
                    else {
                        System.out.println("No potions.");
                    }
                }
                else if (cmd.equals("attack") || cmd.equals("parry") || cmd
                    .equals("dodge")) {
                    int dmg = (int)(player.getAtk() * e.getDef());
                    if (cmd.equals("parry")) {
                        dmg = (int)(player.getAtk() * e.getDef() / 2) + 2;
                    }
                    if (cmd.equals("dodge")) {
                        dmg = (int)(player.getAtk() * e.getDef() / 2);
                    }
                    if (dmg < 1) {
                        dmg = 1;
                    }
                    e.takeDamage(-dmg);
                    System.out.println("You deal " + dmg);
                }
                else {
                    System.out.println("Invalid command.");
                    continue;
                }

                if (e.isAlive() && player.isAlive()) {
                    e.attack(player, (int)(e.getAtk() * player.getDef()));
                    System.out.println(e.getName() + " hits you for " + (int)(e
                        .getAtk() * player.getDef()));
                }
            }
        }

        if (!player.isAlive()) {
            System.out.println("You died.");
            return false;
        }

        setCleared(true);
        player.addHealPotions(1);
        System.out.println(
            "Cleared. +1 potion. Upgrade: hp / atk / def / skip");
        String u = in.nextLine().trim().toLowerCase();
        if (u.equals("hp")) {
            player.changeMaxHp(2);
            player.changeHp(2);
        }
        else if (u.equals("atk")) {
            player.changeAtk(1);
        }
        else if (u.equals("def")) {
            player.changeDef(-0.01);
        }
        return true;
    }
}
