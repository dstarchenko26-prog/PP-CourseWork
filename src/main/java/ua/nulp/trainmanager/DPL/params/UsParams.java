package ua.nulp.trainmanager.DPL.params;

public class UsParams {
    public int minSpeed;
    public int maxSpeed;
    public int maxWeight;
    public int minTrac;
    public int maxCons;
    public int minPass;
    public int minComf;
    public int minCargo;
    public String name;
    public int type;

    public void FiltWParams(int minSpeed, int maxSpeed, int maxWeight, int minTrac, int maxCons, int minPass, int minComf, int minCargo, String name, int type) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.maxWeight = maxWeight;
        this.minTrac = minTrac;
        this.maxCons = maxCons;
        this.minPass = minPass;
        this.minComf = minComf;
        this.minCargo = minCargo;
        this.name = name;
        this.type = type;
    }

    public void FiltTParams(int minSpeed, int maxSpeed, int maxWeight, int minTrac, int maxCons, int minPass, int minCargo, String name) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.maxWeight = maxWeight;
        this.minTrac = minTrac;
        this.maxCons = maxCons;
        this.minPass = minPass;
        this.minCargo = minCargo;
        this.name = name;
    }
}
