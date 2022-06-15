package dm_actions;

import dm_actions.decorator.StockQuotationImpl5J;
import dm_actions.decorator.StockQuotationImplBadValues;

import java.util.Iterator;

public class OlderMain {

    public static void main(String[] argv) {
        StockQuotation microsoft = new StockQuotationImpl5J(new StockQuotationImpl1J("MICROSOFT.csv"));
        microsoft = new StockQuotationImplBadValues(microsoft);

        Iterator it = microsoft.getValues();

        int time = 0;

        while (it.hasNext()) {
            StockValue stockv = (StockValue) it.next();
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
