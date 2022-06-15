package dm_actions;

import dm_actions.iterator.QuotationIterator5J;

import java.util.Iterator;

// Older implementation of StockQuotationImpl5J
public class OlderStockQuotationImpl5J extends StockQuotationImpl1J {

    public OlderStockQuotationImpl5J(String fileName) {
        super(fileName);
    }

    /***
     *
     * @return an Iterator for 5 step
     */
    @Override
    public Iterator<StockValue> getValues() {
        return new QuotationIterator5J(getFileName());
    }

}
