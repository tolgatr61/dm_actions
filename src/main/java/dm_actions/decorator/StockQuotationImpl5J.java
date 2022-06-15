package dm_actions.decorator;

import dm_actions.StockQuotation;
import dm_actions.StockValue;
import dm_actions.iterator.QuotationIterator5J;

import java.util.Iterator;

public class StockQuotationImpl5J extends StockQuotationDecorator {
    public StockQuotationImpl5J(StockQuotation stockQuotation) {
        super(stockQuotation);
    }

    @Override
    public Iterator<StockValue> getValues() {
        return new QuotationIterator5J(super.getFileName());
    }

}
