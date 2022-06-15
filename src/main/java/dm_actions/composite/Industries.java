package dm_actions.composite;

import dm_actions.StockQuotation;
import dm_actions.StockQuotationImpl1J;

public class Industries extends QuotationsGroup {
    /***
     * Industries class as declared in the tree of the question
     */
    public Industries() {
        initializeIndustriesGroup();
    }

    private void initializeIndustriesGroup() {
        StockQuotation stA = new StockQuotationImpl1J("AIRBUS.csv");
        StockQuotation stB = new StockQuotationImpl1J("BOUYGUES.csv");
        addChildren(stA);
        addChildren(stB);
    }

}
