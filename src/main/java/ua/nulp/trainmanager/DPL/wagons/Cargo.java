package ua.nulp.trainmanager.DPL.wagons;

public class Cargo extends Wagon{
    private final int capacity;

    public Cargo(int uid, String name, int speed, int weight, int capacity) {
        super(uid, name, speed, weight);
        this.capacity = capacity;
    }

    public Cargo get() {
        return new Cargo(getUid(), getName(),getSpeed(),getWeight(),getCapacity());
    }

    public String getString() {
        return "Вагон: " + getName() + " Вага: " + getWeight() + " т. Швидкість: " + getSpeed() + " км./год. Місткість: " + capacity + " т.";
    }

    public int getCapacity() {
        return capacity;
    }
}