import java.io.*;
import java.util.*;

public class MainGame {
    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("src/main/java/players.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        Queue<Player> playersQueue = new LinkedList<>();

        while ((line = br.readLine()) != null) {
            String[] linie = line.split(",");
            String nume = linie[0];
            int attack = Integer.parseInt(linie[1]);
            int defense = Integer.parseInt(linie[2]);
            int health = Integer.parseInt(linie[3]);
            playersQueue.add(new Player(nume, attack, defense, health));
        }
        br.close();

        if (playersQueue.isEmpty()) {
            System.out.println("No players loaded. Exiting...");
            return;
        }

        System.out.println("Welcome to the Survival Game!");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (!playersQueue.isEmpty() && running) {
            Player currentPlayer = playersQueue.poll();
            System.out.println("Player taking the turn: " + currentPlayer.getName());

            GameBoard gameBoard = new GameBoard(6, currentPlayer);

            gameBoard.displayBoard();
            currentPlayer.displayStatus();

            while (running && currentPlayer.getStatus() == Status.ALIVE) {
                System.out.println("\nEnter command (W/A/S/D to move, I to inventory, C to craft, F to use food, B to build, Q to quit):");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.isEmpty()) continue;

                char command = input.charAt(0);

                switch (command) {
                    case 'W', 'A', 'S', 'D':
                        gameBoard.movePlayer(command);
                        gameBoard.displayBoard();
                        currentPlayer.displayStatus();
                        break;
                    case 'I':
                        displayInventory(currentPlayer);
                        break;
                    case 'C':
                        craftMenu(currentPlayer, gameBoard);
                        break;
                    case 'F':
                        useFood(currentPlayer);
                        break;
                    case 'B':
                        buildMenu(currentPlayer, gameBoard);
                        break;
                    case 'Q':
                        running = false;
                        System.out.println("Quitting the game. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid command.");
                }
            }

            if (currentPlayer.getStatus() == Status.DEAD) {
                System.out.println();
                System.out.println(currentPlayer.getName() + " has died!");
                System.out.println();
            }

            if (playersQueue.isEmpty() && currentPlayer.getStatus() == Status.DEAD) {
                System.out.println();
                System.out.println("All players are dead. Game Over!");
            }
        }

        scanner.close();
    }

    private static void useFood(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have " + player.getFood() + " units of food. Enter amount to consume:");
        int amount = scanner.nextInt();

        if (amount > 0) {
            player.consumeFood(amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private static void displayInventory(Player player) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("Inventory:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i));
        }

        System.out.println("Sort inventory by: 1. Attack, 2. Defense, 3. Health");
        System.out.println("Enter choice (1/2/3) or any other key to skip sorting:");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                Collections.sort(inventory, new ItemAttackComparator());
                System.out.println("Inventory sorted by Attack.");
                break;
            case "2":
                Collections.sort(inventory, new ItemDefenseComparator());
                System.out.println("Inventory sorted by Defense.");
                break;
            case "3":
                Collections.sort(inventory, new ItemHealthComparator());
                System.out.println("Inventory sorted by Health.");
                break;
            default:
                System.out.println("Skipping sorting.");
        }

        System.out.println("Inventory:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i));
        }
    }

    private static void craftMenu(Player player, GameBoard gameBoard) {
        System.out.println("Available Items to Craft:");
        System.out.println("1. Sword [Attack +10] - Wood:5, Stone:2");
        System.out.println("2. Armor [Defense +15] - Wood:3, Stone:4");
        System.out.println("3. Helmet [Health +10] - Wood:2, Stone:3");
        System.out.println("Enter choice (1/2/3) or any other key to cancel:");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        Item itemToCraft = null;

        switch (choice) {
            case "1":
                itemToCraft = new Item("Sword", 10, 0, 0, 5, 2);
                break;
            case "2":
                itemToCraft = new Item("Armor", 0, 15, 0, 3, 4);
                break;
            case "3":
                itemToCraft = new Item("Helmet", 0, 5, 10, 2, 3);
                break;
            default:
                System.out.println("Cancelling crafting.");
                return;
        }

        player.craftItem(itemToCraft);
    }

    private static void buildMenu(Player player, GameBoard gameBoard) {
        System.out.println("Available Buildings to Construct:");
        System.out.println("1. Fântâna Vieții [Wood: 30, Stone: 30]");
        System.out.println("2. Monument cu Sabie [Wood: 10, Stone: 10]");
        System.out.println("3. Turn de Apărare [Wood: 10, Stone: 20]");
        System.out.println("Enter choice (1/2/3) or any other key to cancel:");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        Building building = null;

        switch (choice) {
            case "1":
                building = BuildingFactory.createFountainOfLife();
                break;
            case "2":
                building = BuildingFactory.createSwordMonument();
                break;
            case "3":
                building = BuildingFactory.createDefenseTower();
                break;
            default:
                System.out.println("Cancelled.");
                return;
        }

        if (player.getWood() >= building.getWoodCost() && player.getStone() >= building.getStoneCost()) {
            player.setWood(player.getWood() - building.getWoodCost());
            player.setStone(player.getStone() - building.getStoneCost());
            System.out.println(building.getName() + " constructed!");
            building.applyEffect(player);
        } else {
            System.out.println("Not enough resources to construct this building.");
        }
    }
}
