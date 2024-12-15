public class BuildingFactory {

    public static Building createFountainOfLife() {
        return new Building(
                "Fântâna Vieții",
                30, 30,
                new BuildingEffect() {
                    @Override
                    public void apply(Player player) {
                        player.restoreHealth();
                    }
                }
        );
    }

    public static Building createSwordMonument() {
        return new Building(
                "Monument cu Sabie",
                10, 10,
                new BuildingEffect() {
                    @Override
                    public void apply(Player player) {
                        player.setAttack((int) (player.getAttack() * 1.10));
                    }
                }
        );
    }

    public static Building createDefenseTower() {
        return new Building(
                "Turn de Apărare",
                10, 20,
                new BuildingEffect() {
                    @Override
                    public void apply(Player player) {
                        player.setDefense(player.getDefense() + 5);
                    }
                }
        );
    }
}
