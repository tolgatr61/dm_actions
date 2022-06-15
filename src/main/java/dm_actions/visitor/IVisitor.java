package dm_actions.visitor;

import dm_actions.OlderStockQuotationImpl5J;
import dm_actions.StockQuotationImpl1J;
import dm_actions.composite.QuotationsGroup;
import dm_actions.composite.Wallet;
import dm_actions.decorator.StockQuotationDecorator;

public interface IVisitor {
    // Visitors
    void visit(StockQuotationImpl1J quotation);

    void visit(OlderStockQuotationImpl5J quotation);

    void visit(StockQuotationDecorator quotation);

    void visit(QuotationsGroup quotation);

    void visit(Wallet quotation);
}
