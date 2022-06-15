package dm_actions.visitor;

import dm_actions.OlderStockQuotationImpl5J;
import dm_actions.StockQuotation;
import dm_actions.StockQuotationImpl1J;
import dm_actions.StockValue;
import dm_actions.composite.QuotationsGroup;
import dm_actions.composite.Wallet;
import dm_actions.decorator.StockQuotationDecorator;

import java.util.Iterator;

public class VisitorFiltersBadValues implements IVisitor {

    /***
     * First visitor, who will visit a StockQuotationImpl1J parameter & update his bad StockValues
     * @param quotation : visitor parameter instance (which is StockQuotationImpl1J)
     */
    @Override
    public void visit(StockQuotationImpl1J quotation) {
        Iterator it = quotation.getValues();
        updateValues(it);
    }

    /***
     * Second visitor, who will visit a OlderStockQuotationImpl5J parameter & update his bad StockValues.
     * Here it's doing the same trick as
     * @param quotation : visitor parameter instance (which is OlderStockQuotationImpl5J)
     */
    @Override
    public void visit(OlderStockQuotationImpl5J quotation) {
        Iterator it = quotation.getValues();
        updateValues(it);
    }

    /***
     * Decorator visitor, we update the value of the decorated instance (which is a StockQuotationImpl)
     * @param quotation : decorator to work with
     */
    @Override
    public void visit(StockQuotationDecorator quotation) {
        Iterator it = quotation.getValues();
        updateValues(it);
    }

    /***
     * QuotationsGroup visitor, the difference will be the depth of iteration
     * @param quotation : QuotationsGroup instance to visit
     */
    @Override
    public void visit(QuotationsGroup quotation) {
        Iterator it = quotation.getValues();

        while (it.hasNext()) {
            StockQuotation quotationSt = (StockQuotation) it.next();
            Iterator quotationtIt = quotationSt.getValues();
            updateValues(quotationtIt);
        }
    }

    /***
     * Wallet visitor which is the biggest group we have, the iteration depth will also increase by 1
     * @param quotation : Wallet instance to work with
     */
    @Override
    public void visit(Wallet quotation) {
        Iterator it = quotation.getValues();

        while (it.hasNext()) {
            QuotationsGroup group = (QuotationsGroup) it.next();
            visit(group);
        }
    }

    /***
     * Function to update the values with the mean of the previous & the next only if it's needed
     * @param quotationtIt : StockQuotation depth iterator
     */
    private void updateValues(Iterator quotationtIt) {
        boolean firstIteration = true;
        double[] values;
        double[] previousValues = new double[6];

        while (quotationtIt.hasNext()) {
            StockValue st = (StockValue) quotationtIt.next();
            if (!firstIteration) {
                values = new double[]{st.getDuration(), st.end(), st.begin(), st.low(), st.high(), st.volume()};
                for (int i = 0; i < values.length; ++i) {
                    if (values[i] <= 0) {
                        values[i] = previousValues[i] + values[i] / 2;
                        st.correctValue(i, values[i]);
                    }
                }
            } else {
                previousValues = new double[]{st.getDuration(), st.end(), st.begin(), st.low(), st.high(), st.volume()};
                firstIteration = false;
            }
        }
    }
}
