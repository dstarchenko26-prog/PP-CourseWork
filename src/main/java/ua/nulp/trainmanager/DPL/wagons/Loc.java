package ua.nulp.trainmanager.DPL.wagons;

public class Loc extends Wagon {
    private final int traction;
    private final int consumption;

    public Loc(int uid, String name, int speed, int weight, int traction, int consumption) {
        super(uid, name, speed, weight);
        this.traction = traction;
        this.consumption = consumption;
    }

    public Loc get() {
        return new Loc(this.getUid(), this.getName(), this.getSpeed(), this.getWeight(), this.getTraction(), this.getConsumption());
    }

    public String getString() {
        return "Локомотив: " + getName() + " Вага: " + getWeight() + " т. Швидкість: " + getSpeed() + " км./год. Тяга: " + traction + " т. Розхід: " + consumption + " л./(т. * год.)";
    }

    public int getTraction() {
        return traction;
    }

    public int getConsumption() {
        return consumption;
    }
}