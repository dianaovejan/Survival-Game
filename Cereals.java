public class Cereals extends Gatherable {
    public Cereals(int quantity, Quality quality) {
        super(quantity, quality, true);
    }

    @Override
    public void gather() {
        if (isGatherable) {
            int actualQuantity = quantity * getMultiplier();
            System.out.println("Collected " + actualQuantity + " units of food from the grain.");
            isGatherable = false;
        } else {
            System.out.println("This grain has already been gathered.");
        }
    }

    private int getMultiplier() {
        switch (quality) {
            case RARE: return 2;
            case EPIC: return 3;
            default: return 1;
        }
    }

    @Override
    public String toString() {
        return "Cereals [quantity=" + quantity + ", quality=" + quality + "]";
    }
}
