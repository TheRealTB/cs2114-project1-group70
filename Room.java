package dungeonCrawler;

import java.util.Scanner;

public abstract class Room {
    //~ Fields ................................................................
    private boolean leftOpen;
    private Room nextLeft;
    private boolean forwardOpen;
    private Room nextForward;
    private boolean rightOpen;
    private Room nextRight;
    private String decription;
    private int depth;
    private boolean cleared;
    
    //~ Constructor
    public Room() {
        leftOpen=false;
        nextLeft=null;
        forwardOpen=false;
        nextForward=null;
        rightOpen=false;
        nextRight=null;
        description = "A creepy stone chamber, the skull was tottering from the top of the room.";
        depth = 0;
        cleared = false;
        rollExits();
    }
    
    //~Public  Methods ........................................................
    public abstract boolean enter(Player player, Scanner in);
    public abstract String getRoomType();
    public void rollExits() {
        forwardOpen = true;
        leftOpen = Math.random() < 0.4;
        rightOpen = Math.random() < 0.4;
        if (!leftOpen && !rightOpen) {
            if (Math.random() < 0.5) {
                leftOpen = true;
            } else {
                rightOpen = true;
            }
        }
    }

    public String look() {
        String exits = "";
        if (leftOpen) {
            exits ++ "left";
        }
        if (forwardOpen) {
            exits += "forward";
        }
        if (rightOpen) {
            exits += "right";
        }
        return getRoomType() + “ ” + depth + "\n" + description + "\nExits:" + exits;
    }

    public boolean hasExit(String dir) {
        dir = cleanDir(dir);
        if (dir.equals("left")) {
            return leftOpen:
        }
        if (dir.equals("forward")) {
            return forwardOpen;
        }
        if (dir.equals("right")) {
            return rightOpen;
        }
        return false;
    }

    public Room go(String dir, int nextDepth) {
        dir = cleanDir(dir);
        if (dir.equals("left") && leftOpen) {
            if (nextLeft == null) {
                nextLeft = RoomFactory.makeRoom(nextDepth);
            }
            return nextLeft;
        }
        if (dir.equals("forward") && forwardOpen) {
            if (nextForward == null) {
                nextForward = RoomFactory.makeRoom(nextDepth);
            }
            return nextRight;
        }
        return null;
    }

    private String cleanDir(String dir) {
        dir = dir.trim().toLowerCase();
        if (dir.equals("left")) {
            return "left";
        }
        if (dir.equals("f"))
            







    
}
