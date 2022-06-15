package dm_actions.decorator;

import dm_actions.StockQuotation;
import dm_actions.StockValue;
import dm_actions.StockValueImpl;

import java.util.ArrayList;
import java.util.Iterator;

public class StockQuotationImplBadValues extends StockQuotationDecorator {
    public StockQuotationImplBadValues(StockQuotation stockQuotation) {
        super(stockQuotation);
    }

    /***
     * We use our decorator iterator to be able to see all the StockValues. Here I avoided to use a mutator, and prefered to create a new list,
     * to keep the encapsulation and see the StockValues as ValueObjects. (visitor will not keep this, but my intention here was that)
     * @return a new  iterator on a decorated (filtered) list of StockValues
     */
    @Override
    public Iterator<StockValue> getValues() {
        Iterator iterator = super.getValues();
        boolean firstIteration = true;

        ArrayList<StockValue> stock = new ArrayList<>();
        double[] values;
        double[] previousValues = new double[6];

        while (iterator.hasNext()) {
            StockValue st = (StockValue) iterator.next();
            if (!firstIteration) {
                values = new double[]{st.getDuration(), st.end(), st.begin(), st.low(), st.high(), st.volume()};
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] <= 0)
                        values[i] = previousValues[i] + values[i] / 2;
                }
            } else {
                previousValues = new double[]{st.getDuration(), st.end(), st.begin(), st.low(), st.high(), st.volume()};
                values = previousValues;
                firstIteration = false;
            }
            stock.add(new StockValueImpl(values));
        }

        return stock.iterator();
    }
}
