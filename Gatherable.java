public abstract class Gatherable {
    protected int quantity;
    protected Quality quality;
    protected boolean isGatherable;

    public Gatherable(int quantity, Quality quality, boolean isGatherable) {
        this.quantity = quantity;
        this.quality = quality;
        this.isGatherable = isGatherable;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public boolean isGatherable() {
        return isGatherable;
    }

    public void setGatherable(boolean gatherable) {
        this.isGatherable = gatherable;
    }

    public abstract void gather();

    @Override
    public abstract String toString();
}
