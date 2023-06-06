package com.example.xcom;

public class HeliumLevel  implements  Cloneable{
    public static final int heliumReservesMax = 150;
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
        this.heliumAmount--;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        HeliumLevel tmp = new HeliumLevel();
        return tmp;
    }
}
