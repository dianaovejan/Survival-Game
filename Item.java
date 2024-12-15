public class Item {
    private String name;
    private int attackBoost;
    private int defenseBoost;
    private int healthBoost;
    private int woodCost;
    private int stoneCost;

    public Item(String name, int attackBoost, int defenseBoost, int healthBoost, int woodCost, int stoneCost) {
        this.name = name;
        this.attackBoost = attackBoost;
        this.defenseBoost = defenseBoost;
        this.healthBoost = healthBoost;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
    }

    public String getName() { return name; }
    public int getAttackBoost() { return attackBoost; }
    public int getDefenseBoost() { return defenseBoost; }
    public int getHealthBoost() { return healthBoost; }
    public int getWoodCost() { return woodCost; }
    public int getStoneCost() { return stoneCost; }

    @Override
    public String toString() {
        return name + " [Attack: " + attackBoost + ", Defense: " + defenseBoost + ", Health: " + healthBoost + "]";
    }
}
