package ua.nulp.trainmanager.DPL.wagons;

public class Cargo extends Wagon{
    private final int capacity;

    public Cargo(String name, int speed, int weight, int capacity) {
        super(name, speed, weight);
        this.capacity = capacity;
    }

    public Cargo get() {
        return new Cargo(getName(),getSpeed(),getWeight(),capacity);
    }

    public String getString() {
        return "Вагон: " + getName() + " Вага: " + getWeight() + " т. Швидкість: " + getSpeed() + " км./год. Місткість: " + capacity + " т.";
    }

    public int getCapacity() {
        return capacity;
    }
}