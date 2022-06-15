package dm_actions.iterator;

import dm_actions.StockValue;

import java.util.Iterator;

public interface QuotationIterator extends Iterator {

    @Override
    boolean hasNext();

    @Override
    StockValue next();

}
