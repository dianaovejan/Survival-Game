public class Building {
    private String name;
    private int woodCost;
    private int stoneCost;
    private BuildingEffect effect;

    public Building(String name, int woodCost, int stoneCost, BuildingEffect effect) {
        this.name = name;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.effect = effect;
    }

    public void applyEffect(Player player) {
        if (effect != null) {
            effect.apply(player);
        } else {
            System.out.println("No effect for this building.");
        }
    }

    public String getName() { return name; }
    public int getWoodCost() { return woodCost; }
    public int getStoneCost() { return stoneCost; }

    @Override
    public String toString() {
        return name + " [Wood Cost: " + woodCost + ", Stone Cost: " + stoneCost + "]";
    }
}
