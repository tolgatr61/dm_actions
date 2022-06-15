package dm_actions;

import dm_actions.exception.StockValueException;

public class StockValueImpl implements StockValue {

    private final double[] tuple;

    /***
     * Basic constructor who needs all the parameters one by one
     * @param duration : time in seconds
     * @param end : end value
     * @param begin : begin value
     * @param high : high value
     * @param low : low value
     * @param volume : volume value
     */
    public StockValueImpl(double duration, double end, double begin, double high, double low, double volume) {
        if (duration < 0 || volume < 0) throw new StockValueException("Duration or volume can't be negative !");
        tuple = new double[]{duration, end, begin, high, low, volume};
    }

    /**
     * Another constructor with an array
     *
     * @param tuple : tuple of size 6 who needs all the values constructed in order : [duration, end, begin, high, low, volume]
     */
    public StockValueImpl(double[] tuple) {
        if (tuple.length != 6) throw new StockValueException("Your double array needs to be 6-sized");
        this.tuple = tuple;
    }

    // Getters on the array
    @Override
    public double getDuration() {
        return tuple[0];
    }

    @Override
    public double end() {
        return tuple[1];
    }

    @Override
    public double begin() {
        return tuple[2];
    }

    @Override
    public double high() {
        return tuple[3];
    }

    @Override
    public double low() {
        return tuple[4];
    }

    @Override
    public double volume() {
        return tuple[5];
    }

    /***
     * Change the value of the array at the index
     * @param index : index of the array
     * @param value : new value
     */
    @Override
    public void correctValue(int index, double value) {
        if (index < 0 || index > 6) throw new StockValueException("Your correcting index should be between 0 and 5");
        if (value <= 0) throw new StockValueException("It's not a correction");
        tuple[index] = value;
    }
}
