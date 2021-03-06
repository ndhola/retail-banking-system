package PresentationLayer.Pages.CustomerCentricPages;

import BusinessLogicLayer.TransactionAction.ITransactionModel;
import PresentationLayer.Pages.AbstractPage;

import java.util.ArrayList;

public class BankStatementTablePage extends AbstractPage {
    private ArrayList<ITransactionModel> transactionList;

    public BankStatementTablePage(ArrayList<ITransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public void printPage() {
        System.out.println("===================================");
        System.out.printf("%10s %10s %10s", "Type", "Amount", "Date");
        System.out.println();
        System.out.println("===================================");
        for (ITransactionModel transaction : transactionList) {
            System.out.format("%10s %10s %10s",
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    transaction.getDate());
            System.out.println();
        }
        System.out.println();
    }
}
