package dungeonCrawler;
public class EncounterRoom extends Room {
    //~ Fields ................................................................
    private int numEnemies;
    private Enemy[] enemies;
    
    //~ Constructors ..........................................................
    public EncounterRoom() {
        super();
        //randomly fill room with 3-5 enemies
        numEnemies=(int)(Math.random()*3)+3;
        enemies=new Enemy[numEnemies];
        for(int i = 0; i < numEnemies; i++) {
            //create numEnemies enemies
        }
    }
    //~Public  Methods ........................................................

}
