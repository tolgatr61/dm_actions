package dm_actions.composite;

import dm_actions.StockQuotation;
import dm_actions.visitor.IVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuotationsGroup implements StockQuotation {

    private final List<StockQuotation> quotations; // list of StockQuotation

    public QuotationsGroup() {
        quotations = new ArrayList<StockQuotation>();
    }

    /***
     *
     * @return an iterator of our list
     */
    @Override
    public Iterator getValues() {
        return quotations.iterator();
    }

    /***
     * Add a children only if it doesn't already exists
     * @param child : StockQuotation child to add
     */
    @Override
    public void addChildren(StockQuotation child) {
        if (!quotations.contains(child)) {
            quotations.add(child);
        }
    }

    /***
     * Remove a children only if it exists
     * @param child : StockQuotation child to remove
     */
    @Override
    public void removeChildren(StockQuotation child) {
        quotations.remove(child);
    }

    /**
     * Visitor accept method
     *
     * @param visitor : visitor to work with
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    /***
     * File names
     * @return all the concatenated fileNames
     */
    @Override
    public String getFileName() {
        StringBuilder str = new StringBuilder();
        Iterator it = quotations.iterator();
        while (it.hasNext()) {
            StockQuotation stockQuotation = (StockQuotation) it.next();
            str.append(stockQuotation.getFileName());
            str.append(" + ");
        }
        return str.toString();
    }

}
