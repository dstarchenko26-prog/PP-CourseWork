package ua.nulp.trainmanager.DPL.wagons;

public class Passengers extends Wagon{
    private final int capacity;
    private final int comfort;
    private final int amountOfLuggage;

    public Passengers(String name, int speed, int weight, int capacity, int comfort, int amountOfLuggage) {
        super(name, speed, weight);
        this.capacity = capacity;
        this.comfort = comfort;
        this.amountOfLuggage = amountOfLuggage;
    }

    public Passengers get() {
        return new Passengers(getName(),getSpeed(),getWeight(),capacity,comfort,amountOfLuggage);
    }

    public String getString() {
        return "Вагон: " + getName() + " Вага: " + getWeight() + " т. Швидкість: " + getSpeed() + " км./год. Рівень комфорту: " + comfort + " Пасажирських місць: " + capacity + " Місць для багажу: " + amountOfLuggage;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getComfort() {
        return comfort;
    }

    public int getAmountOfLuggage() {
        return amountOfLuggage;
    }
}
