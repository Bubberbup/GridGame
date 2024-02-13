import java.util.Scanner;
import java.util.Random;

public class GridGame {
    private static char[][] grid = new char[5][5]; // Declare grid as a class member
    private static int playerX = 1;
    private static int playerY = 0;
    private static int oldPosX;
    public static boolean[][] visited = new boolean[5][5];
    private static int vampireHP = 100;
    private static String[] inventory = new String[5];
    private static boolean playing = true;
    static Scanner myObj = new Scanner(System.in);
    private static PlayerCharacter character = new PlayerCharacter();
    private static int playerHP;
    private static boolean characterCreated = false;
    boolean hasRepairedBronzeKey = false;

    public static void main(String[] args) {
        System.out.println("  ________      .__    .___   ________                       ");
        System.out.println(" /  _____/______|__| __| _/  /  _____/_____    _____   ____  ");
        System.out.println("/   \\  __\\_  __ \\  |/ __ |  /   \\  ___\\__  \\  /     \\_/ __ \\ ");
        System.out.println("\\    \\_\\  \\  | \\/  / /_/ |  \\    \\_\\  \\/ __ \\|  Y Y  \\  ___/ ");
        System.out.println(" \\______  /__|  |__\\____ |   \\______  (____  /__|_|  /\\___  >");
        System.out.println("        \\/              \\/          \\/     \\/      \\/     \\/ ");
        System.out.println("");
        System.out.println(
                "Our brave adventurer finds themselves in a cursed village after taking a detour on their trek home from their long journey in search of a warm meal and comfortable bed to spend the night.");
        System.out.println("");
        System.out.println(
                "Upon entering the village outskirts, a strange, haggard old man greets the player on the main road, warning the traveller that the village they approach has been engulfed by fog and fallen under a strange curse:");
        System.out.println(
                "Strange, Haggard Old Man- 'Grim tidings weary traveller. If it is company and assistance you seek, be warned that there will be none found here.'");
        System.out.println(
                "'The only village this road will lead thee to is home only to the souls of those who once lived there, now trapped for all eternity, and doomed to wander aimlessly in search of redemption and, ultimately, release from this mortal plane…'");
        System.out.println(
                "'Be sure that you have your wits about you and heed my warning, should you wish to exit the village with your life, and soul, intact…'");
        System.out.println("");
        System.out.println(
                "You are in an open area. The fog surrounds you, and your destination is unclear.");
        initializeGrid(); // Initialize the grid

        do {
            String choice = menu();
            if (characterCreated) {
                switch (choice) { // calls menu to return a choice
                    case "1": // choice from menu
                        characterPos();
                        initializeGrid();
                        break;
                    case "2":
                        inventoryCheck();
                        System.out.println();
                        break;
                    case "3":
                        playing = false;
                        System.out.println("\nThanks for playing\n");
                        System.out.println("--------------------------------");
                        break;
                    default:
                        System.out.println("\nInvalid input, please try again!\n");
                        System.out.println("--------------------------------");
                        System.out.println("");
                        break;
                }
            } else {
                switch (choice) { // calls menu to return a choice
                    case "1": // choice from menu
                        createCharacter();
                        break;
                    case "2":
                        playing = false;
                        System.out.println("\nThanks for playing\n");
                        System.out.println("--------------------------------");
                        break;
                    default:
                        System.out.println("\nInvalid input, please try again!\n");
                        System.out.println("--------------------------------");
                        break;
                }
            }
        } while (playing && character.getCharacterHP() > 0);
    }

    public static String menu() { // returns menu choice - called by main and returns to choice

        String choice;
        if (characterCreated) {
            System.out.println("|------------------------------|");
            System.out.println("|What would you like to do?    |");
            System.out.println("|------------------------------|");
            System.out.println("|1: Go to a location:          |");
            System.out.println("|2: Check inventory:           |");
            System.out.println("|3: Quit:                      |");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = myObj.next();
        } else {
            System.out.println("|------------------------------|");
            System.out.println("|What would you like to do?    |");
            System.out.println("|------------------------------|");
            System.out.println("|1: Create Character:          |");
            System.out.println("|2: Quit:                      |");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = myObj.next();
        }
        return choice;
    } // closes menu

    public static void createCharacter() {
        System.out.println("--------------------------------");
        System.out.println("What is your characters name?:");
        String characterName = myObj.next();
        character.setCharacterName(characterName);

        System.out.println("Your character's name is: " + character.getCharacterName());
        System.out.println("You currently have " + character.getCharacterHP() + "HP");
        System.out.println("--------------------------------");
        playerHP = character.getCharacterHP();
        character.setCharacterHp(playerHP);
        characterCreated = true;
    }

    // Initialize the grid
    public static void initializeGrid() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = ' ';
                visited[i][j] = false;
            }
        }
        grid[0][0] = 'T'; // Town Centre
        grid[0][1] = 'B'; // Blue Castle Inn
        grid[1][3] = 'A'; // Abandoned Blacksmith's Shack
        grid[1][4] = 'D'; // Decrepit Cemetery
        grid[2][0] = 'F'; // Black Forest
        grid[2][2] = 'F'; // Black Forest
        grid[2][3] = 'F'; // Black Forest
        grid[2][4] = 'F'; // Black Forest
        grid[2][1] = 'L'; // Lake of Tears
        grid[3][1] = 'S'; // Saint Belmont's Church
        grid[4][3] = 'M'; // Manor Exterior
        grid[4][4] = 'I'; // Manor Interior
    }

    // Display the grid
    public static void displayGrid() {
        System.out.println("  A B C D E");
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 5; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check if the player's move is valid
    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public static void inventoryCheck() {
        System.out.print("\nInventory: \n");
        for (String item : inventory) {
            if (item != null) {
                System.out.print(item + "\n");
            }
        }
        System.out.println();
    }

    public static void characterPos() {
        grid[playerX][playerY] = '*';
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------");
        displayGrid();
        System.out.println("-------------");
        System.out.println("Player Grid Position: " + "(" + playerX + "," + playerY + ")");
        System.out.println("Player HP: " + character.getCharacterHP());

        // Get user input f
        System.out.print("Enter your move (N, S, E, W): ");
        System.out.println("\n--------------------------------");
        String move = scanner.nextLine().toUpperCase();

        // Update player position
        int newPlayerX = playerX;
        int newPlayerY = playerY;
        oldPosX = playerX;

        if (move.equals("N") && playerX > 0) {
            newPlayerX--;
        } else if (move.equals("S") && playerX < 4) {
            newPlayerX++;
        } else if (move.equals("E") && playerY < 4) {
            newPlayerY++;
        } else if (move.equals("W") && playerY > 0) {
            newPlayerY--;
        } else {
            System.out.println("Invalid move!!!");
            System.out.println("");
        }

        // Check if the new position is valid
        if (isValidMove(newPlayerX, newPlayerY)) {

            // Update player's position
            playerX = newPlayerX;
            playerY = newPlayerY;

            // Check if the player has reached any special locations
            checkSpecialLocations(playerX, playerY);

            // Set the new player position

        } else {
            System.out.println("Invalid move. Try again.");
        }
    }

    public static boolean hasRepairedBronzeKey() {
        for (String item : inventory) {
            if ("Repaired Bronze Key".equals(item)) {
                return true;
            }
        }
        return false;
    }

    // Check if the player has reached a special location
    public static void checkSpecialLocations(int x, int y) {
        switch (grid[x][y]) {
            case 'T':
                System.out.println("--------------------------------");
                System.out.println("You have reached the Town Centre.\n");

                townCenter();
                break;
            case 'B':
                System.out.println("--------------------------------");
                System.out.println("You have reached the Blue Castle Inn.\n");
                blueCastleInn();
                break;
            case 'A':
                System.out.println("--------------------------------");
                System.out.println("You have reached the Abandoned Blacksmith's Shack.\n");
                abandonedBSShed();
                break;
            case 'D':
                System.out.println("--------------------------------");
                System.out.println("You have reached the Decrepit Cemetery.\n");
                decrepitCemetery();
                break;
            case 'F':
                System.out.println("--------------------------------");
                System.out.println("You have entered the Black Forest.\n");
                blackForest();
                break;
            case 'L':
                System.out.println("--------------------------------");
                System.out.println("You have reached the Lake of Tears.\n");
                lakeofTears();
                break;
            case 'S':
                System.out.println("--------------------------------");
                System.out.println("You have reached Saint Belmont's Church.\n");
                sbChurch();
                break;
            case 'M':
                System.out.println("--------------------------------");
                System.out.println("You arrive at the entrance of a large Manor.\n");
                abandonedManorEX();
                break;
            case 'I':
                if (hasRepairedBronzeKey()) {
                    abandonedManorIN();
                } else {
                    System.out.println("--------------------------------");
                    System.out.println("You need the Repaired Bronze Key to enter the Manor.\n");
                }
                break;
        }
    }

    public static void townCenter() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "Your find yourself in the centre of a dark town, seemingly devoid of people and surrounded by low resting fog, but the sound of faint, distant laughter can be heard all around you...\n");
        System.out.println("A faint light pierces the fog to your east, like a dim beacon of civilisation.");
        System.out.println("--------------------------------");
    }

    public static void blueCastleInn() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "You move through the fog towards the dim light until you reach a door witha  sign above it that reads Blue Castle Inn.");
        System.out.println("");
        System.out.println(
                "Upon entering the inn the player met with the sight of a young man polishing a dusty beer stein behind the bar.");
        System.out.println("");
        System.out.println(
                "Young Bartender: Ah hah! Greetings weary traveller! Come to save us have you?\nYou've heard the legends of this forsaken town and come to this establishment, hoping to learn how we may be helped out of this dreadful limbo we find ourselves in?\nOr perhaps you have come only for a warm meal and a soft bed…I fear you may be disappointed, dear traveller, to learn that our food is not warm nor our beds comfy.");
        System.out.println("");
        System.out.println(
                "The bartender sets the dusty beer stein down on the bar before the traveler and walks away, vanishing into thin air moments later.");
        System.out.println("");
        System.out.println("Do you take the stein?");
        System.out.println("");
        System.out.println("1 for yes and 2 for no:");
        System.out.println("--------------------------------");
        int decision = myObj.nextInt();

        if (decision == 1) {
            System.out.println(
                    "As the player stares into the glass, letters look to appear on the glass itself, slowly spelling out the word “mausoleum,” potentially guiding the player towards the town's dark and all but forgotten cemetery.\n");
            inventory[0] = "stein";
        } else {
            System.out.println("Good luck and bye!\n");
        }
        System.out.println("--------------------------------");
    }

    public static void decrepitCemetery() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("The player finds themselves in the dank and dark of an ominous-looking cemetery...");
        System.out.println(
                "Unfathomably large webs connectthe remains of crumbling stone protrusions that were once clearly marked headstones, and the skeleton like remains of what were once mighty oaks. At the end of the nightmare-inducing landscape stands a mighty, but webby, stone mausoleum.");
        // Check if the player has the "stein" in their inventory
        if (inventory[0] != null && inventory[0].equals("stein")) {
            // The player has the stein, interact with the Guardian of the tomb
            tombGuardian();
        } else {
            System.out.println("You need to have the stein to proceed to the mausoleum.");
            System.out.println("--------------------------------");
        }
    }

    // interaction with NPC inside cemetery, taking player input for choice made
    public static void tombGuardian() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "The player approaches the stone monument to what must have been a once great person, but is halted at the entrance by a quiet, but shrill, disembodied voice.");
        System.out.println("Guardian of the tomb- 'Seek thy lord's key, you do? ...");
        System.out.println(
                "Tell me, dear traveller, do you wish to free these people of their curse? Are you of a kind and pure heart? Tell me, yes or no!");
        System.out.println("--------------------------------");
        String response = myObj.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("\nGuardian of the tomb- 'You may proceed.'");
            System.out.println("You search the mausoleum, finding a damaged bronze key and silver crucifix.");
            System.out.println("You place them in your pack.");
            System.out.println("--------------------------------");
            // Provide the player with Item2 - Damaged Bronze key and Item3 - Silver
            // Crucifix
            inventory[1] = "Damaged Bronze Key";
            inventory[2] = "Silver Crucifix";
        } else {
            System.out.println(
                    "Guardian of the tomb- 'Your heart is not pure. You may not proceed.'\nThe Guardian attacks you and you are left defenceless to their powers.");
            System.out.println("--------------------------------");
            playerHP = 0;
        }
    }

    public static void abandonedBSShed() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "You enter the Abandoned Blacksmiths Shed and encounter the Blacksmith, an old, weathered man with a grimy apron, hunched over a workbench.");

        // Check if the inventory contains the "Damaged Bronze Key"

        if (inventory.length > 1 && "Damaged Bronze Key".equals(inventory[1])) {
            System.out.println("Blacksmith: 'Aye, let me see that key of yours.'");
            System.out.println("The player hands over the damaged bronze key.");
            System.out.println(
                    "Blacksmith: Well, it's seen better days, that's for sure. I can fix it, but it'll take some time. I'll make it as good as new.");

            // Choice to thank
            System.out.println("Do you want to thank the Blacksmith for his help?");
            System.out.println("1 for yes and 2 for no");
            int thanksDecision = myObj.nextInt();

            // thanksDecision has no effect on game outcome
            if (thanksDecision == 1) {
                System.out.println("You thank the Blacksmith for his assistance.");
            } else {
                System.out.println("You choose not to thank the Blacksmith.");
            }
            System.out.println("The Blacksmith takes some time to repair the key.");
            System.out.println("After a while, he hands you the Repaired Bronze Key.");
            inventory[1] = "Repaired Bronze Key"; // Add the repaired key to the player's inventory
            System.out.println(
                    "The player checks the beer stein again and it directs them to a church on the opposite side of the forest south-west.");
            System.out.println("--------------------------------");System.out.println("--------------------------------");

        } else {
            System.out.println("Blacksmith: 'Sorry, I have no business with thee.'");
            System.out.println("--------------------------------");
        }
    }

    // non-passable area for the player
    public static void blackForest() {
        Scanner myObj = new Scanner(System.in);
        System.out.println(
                "You find yourself in the Black Forest, and while wandering through the dense trees, you encounter a mysterious figure, cloaked in the shadows.");
        System.out.println(
                "???: Halt, weary traveller, and heed these words. The path ahead is shrouded in mystery, and your crucifix holds the power to guide your way. Venture across the Lake of Tears to Saint Belmont's church, an ancient priest dwells there. He can bless your crucifix for battles yet to come.");
        System.out.println("--------------------------------");
        // player is moved back to their original position after trying to enter the area
        if (oldPosX == 1) {
            playerX = 1;
        } else if (oldPosX == 3) {
            playerX = 3;
        }
    }

    // non-interaction/action area
    public static void lakeofTears() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("You swim across the lake. Hmmmm, what an odd name for a seemingly normal body of water…");
        System.out.println("--------------------------------");
    }

    public static void sbChurch() {
        System.out.println(
                "The player enters Saint Belmont's church, a centuries-old sanctuary with stunning stained glass windows casting colourful patterns across the pews. The air is heavy with the scent of incense, and an aura of peace envelops the place.");
        System.out.println("An old priest stands at a lecturn ahead of the empty rows, his head cloakd and lowered.");

        // checkinginventory array for desired item
        if (inventory.length > 2 && "Silver Crucifix".equals(inventory[2])) {
            System.out.println(
                    "The old priest, dressed in tattered vestments, notices the Silver Crucifix you carry. His eyes widen with a glimmer of hope.");
            System.out.println(
                    "Ah, dear traveller, you bear the sign of faith, and in these dark times, we need it more than ever. Let me bless this Silver Crucifix for your safety.");
            // item was found and replaced in the inventory array slot
            inventory[2] = "Blessed Silver Crucifix";
            System.out.println(
                    "The priest holds the crucifix, muttering a holy incantation, and you feel a divine energy emanating from it. Your Silver Crucifix is now the Blessed Silver Crucifix.");
            System.out.println(
                    "The priest leans in and whispers, If you seek to face the Count, the one who's brought misery to this town, you'll find him in the abandoned manor nearby. Beware, for he is a fearsome adversary...");
            System.out.println("--------------------------------");
        } else {
            // item was not found in inventory array after checking
            System.out.println(
                    "The priest remains still, muttering quietly as his head looks down at his lecturn, paying no mind to the player");
            System.out.println("--------------------------------");
        }
    }

    public static void abandonedManorEX() {
        System.out.println(
                "The once-grand edifice is now a decrepit, decaying ruin. Ivy and moss crawl up its walls, and shattered windows gape like hollow eye sockets.");
        System.out.println("The massive wooden door, weathered and splintered, creaks ominously in the breeze.");

        // checking inventory array for desired item
        if (inventory.length > 1 && "Repaired Bronze Key".equals(inventory[1])) {
            System.out.println(
                    "In your hand, you hold the Repaired Bronze key, a tool that may grant access to the secrets hidden within.");
            System.out.println("--------------------------------");
            // item was found in inventory array slot, player is now allowed to move to
            // "Manor Interior"
        } else {
            // item was not found in inventory array slot, player is move back off of the
            // "Manor Exterior" location and not allowed to progress further without item
            System.out.println(
                    "You search the entrance and examine the locked, battered door, unable to find a way inside...");
            System.out.println("--------------------------------");
        }
    }

    // player enter final area, interacting/fighting with BBEG
    public static void abandonedManorIN() {
        System.out.println(
                "As you step through the massive door, you're plunged into darkness, and the air is chilly, carrying the unmistakable scent of dampness and decay. You hear a shuffling sound, and a figure emerges from the shadows with red eyes.");
        System.out.println("Vampire: 'So you've finally arrived " + character.getCharacterName()
                + ", Have you decided to come to end my reign?");
        System.out.println("--------------------------------");
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        // creating a loop to keep the fight going until either the player or NPC is out of health
        while (character.getCharacterHP() > 0 && vampireHP > 0) {
            System.out.println("Choose an item from your inventory to use:");
            System.out.println("1. Stein");
            System.out.println("2. Silver Crucifix");
            System.out.print("Enter your choice (1 or 2): ");
            System.out.println("--------------------------------");
            int choice = scanner.nextInt();

            int playerAttack = 0;

            if (choice == 1) {
                // Use the sword
                playerAttack = random.nextInt(11) + 10; // Random damage between 10 and 20
                System.out.println("You search your pack for your sword but cannot find it, finding and grabbing the beer stien instead.");
                System.out.println(
                        "You bash the vampire on the head with your stein for " + playerAttack + " damage.");
            } else if (choice == 2) {
                // Use the silver crucifix
                playerAttack = random.nextInt(6) + 15; // Random damage between 15 and 20
                System.out
                        .println("You use the silver crucifix to attack the vampire for " + playerAttack
                                + " damage.");
            } else {
                System.out.println("Invalid choice. Please select 1 for Sword or 2 for Silver Crucifix.");
                System.out.println("--------------------------------");
                continue; // Allow the player to choose again
            }

            vampireHP -= playerAttack;

            if (vampireHP <= 0) {
                System.out.println("You have defeated the vampire!");
                System.out.println("The curse over the town is lifted, and you have won the game!");
                System.out.println("--------------------------------");
                return;
            }

            int vampireAttack = random.nextInt(11) + 10; // Random damage between 10 and 20
            playerHP -= vampireAttack;
            character.setCharacterHp(playerHP);
            System.out.println("The vampire attacks you for " + vampireAttack + " damage.");

            if (character.getCharacterHP() <= 0) {
                System.out.println("The vampire has defeated you.");
                System.out.println("Game over, you have failed.");
                System.out.println("--------------------------------");
                return;
            }
        }
    }


}
