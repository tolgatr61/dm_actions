package dm_actions;

import dm_actions.composite.Health;
import dm_actions.composite.Industries;
import dm_actions.composite.QuotationsGroup;
import dm_actions.composite.Wallet;
import dm_actions.visitor.IVisitor;
import dm_actions.visitor.VisitorFiltersBadValues;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        // Initialization
        Health health = new Health();
        Industries industries = new Industries();
        Wallet wallet = new Wallet();
        wallet.addChildren(health);
        wallet.addChildren(industries);
        IVisitor visitor = new VisitorFiltersBadValues();
        wallet.accept(visitor);

        Iterator it = wallet.getValues();

        int time = 0;
        // The process will create iterator for each branch, and iterate over to get the StockValues to work on
        while (it.hasNext()) {
            QuotationsGroup group = (QuotationsGroup) it.next();
            Iterator groupIt = group.getValues();

            while (groupIt.hasNext()) {
                StockQuotation quot = (StockQuotation) groupIt.next();
                Iterator itQuot = quot.getValues();
                while (itQuot.hasNext()) {
                    StockValue stockv = (StockValue) itQuot.next();

                    StringBuilder st = new StringBuilder();
                    st.append(time);
                    st.append(" , ");
                    st.append(stockv.begin());
                    st.append(" , ");
                    st.append(stockv.end());
                    st.append(" , ");
                    st.append(stockv.low());
                    st.append(" , ");
                    st.append(stockv.high());
                    System.out.println(st);

                    time += stockv.getDuration();
                }
            }
        }
    }

}
