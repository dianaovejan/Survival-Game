import java.util.Comparator;

public class ItemHealthComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return Integer.compare(b.getHealthBoost(), a.getHealthBoost());
    }
}
