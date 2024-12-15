import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int wood = 0;
    private int stone = 0;
    private int food = 0;
    private int maxHealth;
    private List<Item> inventory;

    public Player(String name, int attack, int defense, int health) {
        super(name, attack, defense, health);
        this.wood = 10;
        this.stone = 10;
        this.food = 10;
        this.maxHealth = health;
        this.inventory = new ArrayList<>();
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.min(health, this.maxHealth);
        if (this.health <= 0) {
            this.die();
        }
    }

    public void restoreHealth() {
        this.health = this.maxHealth;
        System.out.println("Health fully restored.");
    }

    @Override
    public void damage(Character opponent) {
        if (this.status == Status.ALIVE && opponent.getStatus() == Status.ALIVE) {
            System.out.println(this.name + " attacks " + opponent.getName() + "!");
            opponent.takeDamage(this.attack);
        }
    }

    @Override
    public void takeDamage(int damage) {
        int effectiveDamage = Math.max(damage - this.defense, 5);
        this.health -= effectiveDamage;
        System.out.println(this.name + " takes " + effectiveDamage + " damage. Health now: " + this.health);

        if (this.health <= 0) {
            this.die();
        }
    }

    @Override
    public void die() {
        this.status = Status.DEAD;
        System.out.println(this.name + " has died. Game over!");
    }

    public void gatherResource(Gatherable item) {
        if (item.isGatherable()) {
            item.gather();
            int quantity = item.getQuantity() * getMultiplier(item.getQuality());
            switch (item.getClass().getSimpleName()) {
                case "Trees":
                    this.wood += quantity;
                    break;
                case "Rocks":
                    this.stone += quantity;
                    break;
                case "Cereals":
                    this.food += quantity;
                    break;
                default:
                    System.out.println("Unknown gatherable item.");
            }
            item.setGatherable(false);
            System.out.println("Gathered " + item.getQuantity() + " " + item.getClass().getSimpleName() + "(s).");
        } else {
            System.out.println("This resource is no longer gatherable.");
        }
    }

    private int getMultiplier(Quality quality) {
        return switch (quality) {
            case RARE -> 2;
            case EPIC -> 3;
            default -> 1;
        };
    }

    public void craftItem(Item item) {
        if (this.wood >= item.getWoodCost() && this.stone >= item.getStoneCost()) {
            this.wood -= item.getWoodCost();
            this.stone -= item.getStoneCost();
            this.inventory.add(item);

            System.out.println("Crafted " + item.getName() + " and added to inventory.");
        } else {
            System.out.println("Not enough resources to craft " + item.getName());
            System.out.println("Required - Wood: " + item.getWoodCost() + ", Stone: " + item.getStoneCost());
            System.out.println("Available - Wood: " + this.wood + ", Stone: " + this.stone);
        }
    }

    public void consumeFood(int amount) {
        if (this.food >= amount) {
            this.food -= amount;
            int healthRestored = amount * 2;
            this.setHealth(this.health + healthRestored);
            System.out.println(this.name + " consumed " + amount + " units of food and restored " + healthRestored + " health.");
        } else {
            System.out.println("Not enough food to consume.");
        }
    }

    public void buildBuilding(Building building) {
        if (this.wood >= building.getWoodCost() && this.stone >= building.getStoneCost()) {
            this.wood -= building.getWoodCost();
            this.stone -= building.getStoneCost();
            System.out.println(building.getName() + " has been built!");
            building.applyEffect(this);
        } else {
            System.out.println("Not enough resources to build " + building.getName() + ".");
            System.out.println("Required - Wood: " + building.getWoodCost() + ", Stone: " + building.getStoneCost());
            System.out.println("Available - Wood: " + this.wood + ", Stone: " + this.stone);
        }
    }

    public int getWood() { return wood; }
    public int getStone() { return stone; }
    public int getFood() { return food; }

    public void setWood(int wood) { this.wood = wood; }
    public void setStone(int stone) { this.stone = stone; }
    public void setFood(int food) { this.food = food; }

    @Override
    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void displayStatus() {
        System.out.println("Player: " + name);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Resources -> Wood: " + wood + ", Stone: " + stone + ", Food: " + food);
        System.out.println("Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Item item : inventory) {
                System.out.println(" - " + item);
            }
        }
    }

}
