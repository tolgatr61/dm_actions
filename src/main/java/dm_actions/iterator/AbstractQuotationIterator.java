package dm_actions.iterator;

import dm_actions.StockValue;
import dm_actions.StockValueImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
This class is an abstract implementation of the Iterator Pattern over all the lines from one of our CSV file.
Using an abstract class here factorizes the code of our subclasses, and creates a better logic. We basically just need to change the discretizationStep in a subclass.
 */
public abstract class AbstractQuotationIterator implements QuotationIterator {

    private final String delimiter = ";"; // Delimiter of csv columns
    private final LinkedList<String> discretizedLines = new LinkedList<>(); // List of lines to make more generic our discretized lines treatments
    protected int discretizationStep = 1; // discretizationStep, by default 1, if a new value is needed a subclass can initialize it.
    private BufferedReader csvReader; // Our buffer object which is making more easy exploring tasks on our CSV converted to StringFormat
    private String line; // current line

    /***
     *
     * @param fileName : fileName to import (expected format is juste the name, we are using the resources folder from maven to load it from a fixed path)
     */
    public AbstractQuotationIterator(String fileName) {
        try {
            InputStream streamPath = getClass().getClassLoader().getResourceAsStream(fileName); // path of the File
            csvReader = new BufferedReader(new InputStreamReader(streamPath));
            line = csvReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * hasNext() Verify if the iterator can iterate over the next element.
     * @return False if there is a line that is null or empty (the ending of our CSV are empty lines, so we treat this case too)
     * true otherwise, it's a line
     */
    @Override
    public boolean hasNext() {
        try {
            int bufferSize = 1000;
            csvReader.mark(bufferSize);
            String nextLine = csvReader.readLine();
            csvReader.reset();
            return nextLine != null && !nextLine.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /***
     * Gives the next element depending on the discretization step from our BufferedReader working on the CSV
     * Our discretization step work with a quantitative method, if we put 5 we will iterate 5 lines by 5 lines.
     * With a bufferedReader, I declared one list aside as an attribute, to be able to work more easily on the discretized lines.
     * @return the next tuple converted as a StockValue object
     */
    @Override
    public StockValue next() {
        try {
            if (hasNext()) {
                for (int i = 0; i < discretizationStep; ++i) {
                    if (hasNext()) {
                        line = csvReader.readLine();
                        discretizedLines.add(line);
                    } else {
                        // throw new StockQuotationIteratorException("Your file, or ending lines does not fit your" +
                        //        " discretizationStep, it have been calculated over the available lines");
                        break; // an exception is too restrictive for this, we just leave the loop and work with what we have left
                    }
                }
                double[] tupleValues = (discretizationStep == 1) ? singularTupleValues() : discretizedTupleValues();
                return new StockValueImpl(tupleValues);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Function for 1 step, it will basically get all the converted lines from String format to double, and clear the list.
     * We don't need to work specifically on a list there. But the reasons is to adapt our next() to any n step and keep the same logic.
     * @return a double list corresponding to a line data
     */
    private double[] singularTupleValues() {

        double[] tupleValues = new double[6];
        tupleValues[0] = convertDateToSeconds(discretizedLines.getFirst(), null);
        String[] columns = discretizedLines.getFirst().split(delimiter);

        for (int i = 1; i < columns.length; i++) {
            columns[i] = columns[i].replace("\"", "");
            columns[i] = columns[i].replace(",", ".");
            double castedCol = Double.parseDouble(columns[i]);
            tupleValues[i] = castedCol;
        }

        discretizedLines.clear();
        return tupleValues;
    }

    /***
     * Here it needs our list to work, function to calculate the discretizated values tuple for any n sized step.
     * Our discretization is based on this idea : data = (inSecond[dateBetweenFirstLine, dateBetweenLastLine), min of all n Closures,
     * max of all n Openings, max of n High Values, min of n Low Values, sum of volumes).
     * @return our discretizated double array with the values needed for a StockValue tuple
     */
    private double[] discretizedTupleValues() {
        double[] tupleValues = new double[6];
        tupleValues[0] = convertDateToSeconds(discretizedLines.getFirst(), discretizedLines.getLast()); // get the date in seconds between start and ending line
        boolean firstDiscretizedLine = true; // boolean to initialize our first line on tupleValues array in each discretization step example with 5 : true next 5 true ...
        // it avoids creating a new array for each steps
        String[] columns;

        for (Iterator it = discretizedLines.iterator(); it.hasNext(); ) {
            // Split our current line columns
            String currentLine = (String) it.next();
            columns = currentLine.split(delimiter);

            for (int i = 1; i < columns.length; i++) {
                // Remove unnecessary characters and cast it to double
                columns[i] = columns[i].replace("\"", "");
                columns[i] = columns[i].replace(",", ".");
                double castedCol = Double.parseDouble(columns[i]);
                // firstLine we simply initialize the array
                if (firstDiscretizedLine) {
                    tupleValues[i] = castedCol;
                } else {
                    // We will discretize all the columns of our lines.
                    // i == 2 is the "Ouverture" column, i == 3 is
                    if (i == 2 || i == 3) { // max of i == 2 which is the "Ouverture" column, i == 3 is the "Valeur haute" column
                        tupleValues[i] = (tupleValues[i] < castedCol) ? castedCol : tupleValues[i];
                    } else if (i == 1 || i == 4) { // min of i == 1 which is the "Fermeture" column, i == 4 is the "Valeur basse" column
                        tupleValues[i] = (tupleValues[i] > castedCol) ? castedCol : tupleValues[i];
                    } else if (i == 5) { // sum of all "Volume" columns
                        tupleValues[i] += castedCol;
                    }
                }
            }
            firstDiscretizedLine = false;
        }
        discretizedLines.clear(); // clear the list
        return tupleValues;
    }

    /***
     *
     * @param fromLine startingLine to calculate our second date with
     * @param toLine endingLing to calculate our second date with
     * @return time in seconds between fromLine to toLine dates
     */
    private double convertDateToSeconds(String fromLine, String toLine) {
        // If we don't have a toLine, we can just return the result of the current day in seconds. (86000)
        if (toLine == null || toLine.isEmpty()) {
            return Duration.ofDays(1).getSeconds();
        }

        // We split our columns, and get our dates from the lines.
        String[] columnsFrom = fromLine.split(delimiter);
        String[] columnsTo = toLine.split(delimiter);
        String dateFrom = columnsFrom[0].replace("\"", "");
        String dateTo = columnsTo[0].replace("\"", "");

        // Convert to date object, and makes a calculate between the second date time - the firstDate time in seconds.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        long time = 0;
        try {
            Date firstDate = sdf.parse(dateFrom);
            Date secondDate = sdf.parse(dateTo);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            time = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // difference as one more day was calculated by default on the timer
        return time - Duration.ofDays(1).getSeconds();
    }

}
