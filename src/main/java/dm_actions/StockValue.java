package dm_actions;

public interface StockValue {

    // Give the duration in second of a tupel
    double getDuration();

    // Gives the start value of a tuple
    double begin();

    // Gives the end value of a tuple
    double end();

    // Gives the lowest value of a tuple
    double low();

    // Gives the highest value of a tuple
    double high();

    // Gives the volume of a tuple
    double volume();

    // A function for the visitor, who needs to be able to change the values
    void correctValue(int index, double value);

}
