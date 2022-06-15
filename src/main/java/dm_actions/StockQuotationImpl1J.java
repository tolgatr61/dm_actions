package dm_actions;

import dm_actions.iterator.QuotationIterator1J;
import dm_actions.visitor.IVisitor;

import java.util.Iterator;

public class StockQuotationImpl1J implements StockQuotation {

    private final String fileName;

    /***
     *
     * @param fileName : fileName to parse
     */
    public StockQuotationImpl1J(String fileName) {
        this.fileName = fileName;
    }

    /***
     *
     * @return an Iterator for 1 day with the fileName passed on the class
     */
    @Override
    public Iterator<StockValue> getValues() {
        return new QuotationIterator1J(fileName);
    }

    /***
     * Getter
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /***
     * Accept function from the visitor Pattern
     * @param visitor : visitor to use and who will visit this function (add a new functionnality)
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    // Not used there, Composite methods
    @Override
    public void addChildren(StockQuotation child) {

    }

    @Override
    public void removeChildren(StockQuotation child) {

    }

}
