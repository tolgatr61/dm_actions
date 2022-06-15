package dm_actions;

import dm_actions.visitor.IVisitor;

import java.util.Iterator;

public interface StockQuotation {

    Iterator getValues(); // returns an Iterator over the StockValues, or even the StockQuotation for biggest depth Iterators (like Composite)

    String getFileName(); // for my 5J decorator that relates on another Iterator

    // Composite
    void addChildren(StockQuotation child);

    void removeChildren(StockQuotation child);

    // Visitor
    void accept(IVisitor visitor);

}
