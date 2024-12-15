public class Enemy extends Character {
    public Enemy(String name, int attack, int defense, int health) {
        super(name, attack, defense, health);
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
        int effectiveDamage = Math.max(damage - this.defense, 1);
        this.health -= effectiveDamage;
        System.out.println(this.name + " takes " + effectiveDamage + " damage. Health now: " + this.health);

        if (this.health <= 0) {
            this.die();
        }
    }


    @Override
    public void die() {
        this.status = Status.DEAD;
        System.out.println(this.name + " has been defeated.");
        dropItem();
    }

    public Item dropItem() {
        double rand = Math.random();
        if (rand < 0.33) {
            return new Item("Sword", 10, 0, 0, 5, 2);
        } else if (rand < 0.66) {
            return new Item("Armor", 0, 15, 0, 3, 4);
        } else {
            return new Item("Helmet", 0, 5, 10, 2, 3);
        }
    }


}
