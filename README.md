Text-based dungeon crawler :P

## How to compile and run

All source files sit in the `dungeonCrawler` package. From the folder that
contains the `.java` files:

```
javac -d build *.java
java -cp build dungeonCrawler.GameRunner
```

`GameRunner.main` is the entry point.

### Running the JUnit tests

The tests need JUnit 4 and Hamcrest on the classpath. Point the two paths
below at your own copies of the jars (Eclipse users can instead right-click
the project and choose Run As -> JUnit Test):

```
javac -d build -cp junit-4.13.2.jar:hamcrest-core-1.3.jar *.java
java -cp build:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore \
    dungeonCrawler.PlayerTest dungeonCrawler.EnemyTest \
    dungeonCrawler.InputHandlerTest dungeonCrawler.DungeonGeneratorTest
```

## How to play

- **In a room:** `attack` / `parry` / `dodge` / `heal`
- **Between rooms:** `left` / `forward` / `right`, or `quit`

Input is trimmed and lowercased, and anything unrecognized re-prompts
instead of crashing.

## Classes

| Class | Responsibility |
| --- | --- |
| `GameRunner` | Owns the game loop: intro, descend through rooms, game over |
| `Player` | Player stats and the rules for changing them (hp, atk, def, potions) |
| `Enemy` | One enemy's stats and name; scales with depth, bosses are stronger |
| `Room` | Abstract chamber: flavor text, exits, and navigation between rooms |
| `EncounterRoom` | A room holding a pack of enemies; runs the combat turn loop |
| `UpgradeRoom` | A room offering one stat upgrade (health / attack / defense) |
| `DungeonGenerator` | Builds the next `Room` for a given depth |
| `InputHandler` | Reads console input and only ever returns an allowed value |

Note: the class names here differ from the Deliverable 2 spec doc (which
listed `GameEngine`, `Monster`, `CombatManager`, `ShopManager`, and
`ScalingUtil`). We kept our own structure as the code evolved — see the
presentation for what changed from spec to code and why.

## Docs

Scoping Doc: https://docs.google.com/document/d/1FbzoOFtigqey96VnEQiqmfOPdwTFFvTEMIcfYcA_l5I/edit?usp=sharing

Spec Doc: https://docs.google.com/document/d/10mwNykMssU2njJG85EqB0oO6njh-Fx5E3KExbYd88Mk/edit?usp=sharing

System diagram: see `System diagram` in this repo.
