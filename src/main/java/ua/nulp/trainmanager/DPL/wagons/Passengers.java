package ua.nulp.trainmanager.DPL.wagons;

public class Passengers extends Wagon{
    private final int capacity;
    private final int comfort;
    private final int amountOfLuggage;

    public Passengers(int uid, String name, int speed, int weight, int capacity, int comfort, int amountOfLuggage) {
        super(uid, name, speed, weight);
        this.capacity = capacity;
        this.comfort = comfort;
        this.amountOfLuggage = amountOfLuggage;
    }

    @Override
    public Passengers get() {
        return new Passengers(getUid(), getName(),getSpeed(),getWeight(), getCapacity(),getComfort(),getAmountOfLuggage());
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
