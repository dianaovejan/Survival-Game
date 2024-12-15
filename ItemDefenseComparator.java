import java.util.Comparator;

public class ItemDefenseComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return Integer.compare(b.getDefenseBoost(), a.getDefenseBoost());
    }
}
