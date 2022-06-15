package dm_actions.composite;

import dm_actions.StockQuotation;
import dm_actions.exception.StockQuotationGroupException;
import dm_actions.visitor.IVisitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Wallet implements StockQuotation {

    List<QuotationsGroup> quotationsGroups; // list of QuotationsGroup

    public Wallet() {
        quotationsGroups = new ArrayList<QuotationsGroup>();
    }

    /***
     *
     * @return Iterator of our list
     */
    @Override
    public Iterator getValues() {
        return quotationsGroups.iterator();
    }

    /***
     *
     * @return concatenated fileNames of the Group
     */
    @Override
    public String getFileName() {
        StringBuilder str = new StringBuilder();
        Iterator it = quotationsGroups.iterator();
        while (it.hasNext()) {
            StockQuotation stockQuotation = (StockQuotation) it.next();
            str.append(stockQuotation.getFileName());
            str.append(" + ");
        }
        return str.toString();
    }

    /***
     * Add a child if it is a QuotationsGroup and it doesn't exists
     * @param child : QuotationsGroup child to add
     */
    @Override
    public void addChildren(StockQuotation child) {
        if (child instanceof QuotationsGroup && !quotationsGroups.contains(child)) {
            quotationsGroups.add((QuotationsGroup) child);
        } else {
            throw new StockQuotationGroupException("Bad instance or group to add is already inside.");
        }
    }

    /***
     * Remove a child if it exists
     * @param child : QuotationsGroup child to remove
     */
    @Override
    public void removeChildren(StockQuotation child) {
        if (quotationsGroups.contains(child)) {
            quotationsGroups.remove(child);
        } else {
            throw new StockQuotationGroupException("Bad instance or is group to remove not inside.");
        }
    }

    /***
     * Visitor acceptation method
     * @param visitor : visitor to work with
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
