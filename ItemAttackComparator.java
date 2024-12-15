import java.util.Comparator;

public class ItemAttackComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return Integer.compare(b.getAttackBoost(), a.getAttackBoost()); // Descending order
    }
}

