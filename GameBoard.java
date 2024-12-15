import java.io.IOException;
import java.util.Random;
import java.util.*;

public class GameBoard {
    private Object[][] board;
    private int size;
    private Player player;
    private int playerX;
    private int playerY;
    private Random random;

    public GameBoard(int size, Player player) throws IOException {
        this.size = size;
        this.player = player;
        this.board = new Object[size][size];
        this.playerX = size / 2;
        this.playerY = size / 2;
        this.random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == playerX && j == playerY) {
                    continue;
                }

                double rand = random.nextDouble();

                if (rand < 0.2) {
                    Enemy enemy = new Enemy("Goblin", 5, 2, 20);
                    board[i][j] = enemy;
                }
                else if (rand < 0.5) {
                    board[i][j] = null;
                }
                else if (rand < 0.7) {
                    Gatherable trees = new Trees(10, Quality.COMMON);
                    board[i][j] = trees;
                }
                else if (rand < 0.8) {
                    Gatherable rocks = new Rocks(5, Quality.RARE);
                    board[i][j] = rocks;
                }
                else {
                    Gatherable cereals = new Cereals(15, Quality.EPIC);
                    board[i][j] = cereals;
                }
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print("P "); // Player
                } else if (board[i][j] instanceof Enemy) {
                    System.out.print("E "); // Enemy
                } else if (board[i][j] instanceof Building) {
                    System.out.print("B "); // Building
                } else if (board[i][j] instanceof Trees) {
                    System.out.print("T "); // Tree
                } else if (board[i][j] instanceof Rocks) {
                    System.out.print("R "); // Rock
                } else if (board[i][j] instanceof Cereals) {
                    System.out.print("C "); // Cereals
                } else if (board[i][j] == null) {
                    System.out.print(". "); // Empty slot
                }
            }
            System.out.println();
        }
    }

    public void movePlayer(char direction) {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
            case 'W', 'w':
                newX = Math.max(playerX - 1, 0);
                break;
            case 'S', 's':
                newX = Math.min(playerX + 1, size - 1);
                break;
            case 'A', 'a':
                newY = Math.max(playerY - 1, 0);
                break;
            case 'D', 'd':
                newY = Math.min(playerY + 1, size - 1);
                break;
            default:
                System.out.println("Invalid input. Use W/A/S/D to move.");
                return;
        }

        if (newX == playerX && newY == playerY) {
            System.out.println("Cannot move in that direction.");
            return;
        }

        playerX = newX;
        playerY = newY;
        System.out.println("Moved to position (" + playerX + ", " + playerY + ").");
        System.out.println();

        if (board[playerX][playerY] instanceof Enemy) {

            Enemy enemy = (Enemy) board[playerX][playerY];
            System.out.println("Encountered an enemy: " + enemy.getName());
            engageCombat(enemy);
            if (enemy.getStatus() == Status.DEAD) {
                board[playerX][playerY] = null;
            }

        } else if (board[playerX][playerY] instanceof Building) {

            Building building = (Building) board[playerX][playerY];
            System.out.println("You are at the building: " + building.getName());
            building.applyEffect(player);

        } else if (board[playerX][playerY] instanceof Gatherable) {

            Gatherable gatherable = (Gatherable) board[playerX][playerY];
            if (gatherable instanceof Trees) {
                System.out.println("You found a Tree!");
                player.gatherResource(gatherable);
            } else if (gatherable instanceof Rocks) {
                System.out.println("You found a Rock!");
                player.gatherResource(gatherable);
            } else if (gatherable instanceof Cereals) {
                System.out.println("You found Cereals!");
                player.gatherResource(gatherable);
            }
        }
    }

    private void engageCombat(Enemy enemy) {
        while (player.getStatus() == Status.ALIVE && enemy.getStatus() == Status.ALIVE) {
            System.out.println("Player Health: " + player.getHealth());
            System.out.println("Enemy Health: " + enemy.getHealth());

            player.damage(enemy);

            if (enemy.getStatus() == Status.DEAD) {
                System.out.println("Defeated " + enemy.getName());
                Item droppedItem = enemy.dropItem();
                if (droppedItem != null) {
                    player.getInventory().add(droppedItem);
                    System.out.println("Picked up " + droppedItem.getName());
                }
                break;
            }

            enemy.damage(player);

            if (player.getStatus() == Status.DEAD) {
                System.out.println();
                System.out.println(player.getName() + " has been defeated!");
                break;
            }
        }

        if (player.getStatus() == Status.DEAD) {
            System.out.println();
            System.out.println("Game Over! " + player.getName() + " has been defeated.");
        } else {
            System.out.println("Combat ended.");
        }
    }

    private void placeRandomGatherable() {
        double rand = random.nextDouble();
        Gatherable gatherable = null;
        if (rand < 0.33) {
            gatherable = new Trees(10, Quality.COMMON);
        } else if (rand < 0.66) {
            gatherable = new Rocks(5, Quality.RARE);
        } else {
            gatherable = new Cereals( 15, Quality.EPIC);
        }
        System.out.println("You found " + gatherable.getClass().getSimpleName() + "!");

        player.gatherResource(gatherable);
    }

}