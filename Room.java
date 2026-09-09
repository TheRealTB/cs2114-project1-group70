package dungeonCrawler;
public abstract class Room {
    //~ Fields ................................................................
    private boolean leftOpen;
    private Room nextLeft;
    private boolean forwardOpen;
    private Room nextForward;
    private boolean rightOpen;
    private Room nextRight;
    
    //~ Constructor
    public Room() {
        leftOpen=false;
        nextLeft=null;
        forwardOpen=false;
        nextForward=null;
        rightOpen=false;
        nextRight=null;
    }
    //~Public  Methods ........................................................
    
}
