package DataAccessLayer;

import BusinessLogicLayer.TransactionAction.TransactionModel;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAccountDatabase {
    int getUserBalance(String accountNumber) throws SQLException;

    int updateBalance(int finalBalance, String accountNumber) throws SQLException;

    boolean verifyAccountNumber(String accountNumber) throws SQLException;

    int saveTransaction(String accountNumber, String transactionType, int amount) throws SQLException;

    ArrayList<TransactionModel> getMiniStatement(String accountNumber) throws SQLException;
}
