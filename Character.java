import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    protected String name;
    protected int attack;
    protected int defense;
    protected int health;
    protected Status status;
    protected List<Item> inventory;

    public Character(String name, int attack, int defense, int health) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.status = Status.ALIVE;
        this.inventory = new ArrayList<>();
    }

    public abstract void damage(Character opponent);
    public abstract void takeDamage(int damage);
    public abstract void die();

    // Getters and Setters for character attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                ", health=" + health +
                ", status=" + status +
                ", inventory=" + inventory +
                '}';
    }
}
