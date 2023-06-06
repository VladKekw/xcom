package com.example.xcom;

public class HeliumLevel  implements  Cloneable{
    public static final int heliumReservesMax = 1500;
    private int heliumAmount;

    public int getHeliumAmount() {
        return heliumAmount;
    }

    public void setHeliumAmount(int heluimAmount) {
        this.heliumAmount = heluimAmount;
    }

    public HeliumLevel()
    {
    this.heliumAmount = heliumReservesMax;
    }

    public void consumeHelium()
    {
        this.heliumAmount -= 0.0001;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        HeliumLevel tmp = new HeliumLevel();
        return tmp;
    }
}
