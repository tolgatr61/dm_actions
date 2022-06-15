package dm_actions.composite;

import dm_actions.StockQuotation;
import dm_actions.StockQuotationImpl1J;

public class Health extends QuotationsGroup {
    /***
     * Health class as declared in the tree of the question
     */
    public Health() {
        initializeHealthGroup();
    }

    private void initializeHealthGroup() {
        StockQuotation stA = new StockQuotationImpl1J("SANOFI.csv");
        StockQuotation stB = new StockQuotationImpl1J("GENFIT.csv");
        addChildren(stA);
        addChildren(stB);
    }

}
