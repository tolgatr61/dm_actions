package dm_actions.decorator;

import dm_actions.StockQuotation;
import dm_actions.StockValue;
import dm_actions.visitor.IVisitor;

import java.util.Iterator;

public abstract class StockQuotationDecorator implements StockQuotation {

    private final StockQuotation stockQuotation; // decorated object

    public StockQuotationDecorator(StockQuotation stockQuotation) {
        this.stockQuotation = stockQuotation;
    }

    @Override
    public Iterator<StockValue> getValues() {
        return stockQuotation.getValues();
    }

    @Override
    public String getFileName() {
        return stockQuotation.getFileName();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    // Not used without a group decorator but needed for the Itf
    @Override
    public void addChildren(StockQuotation child) {

    }

    @Override
    public void removeChildren(StockQuotation child) {

    }
}
