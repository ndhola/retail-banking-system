package BusinessLogicLayer.CustomerAction;

import BusinessLogicLayer.CommonAction.Action;
import DataAccessLayer.DatabaseFactory;
import DataAccessLayer.IAccountDatabase;
import DataAccessLayer.IDatabaseFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoanEstimator extends Action {
    private static final String menuLabel = "Loan Estimator";
    private double annualInterestRate = 8;
    private static final int minimumLoanAmount = 10000;
    private static final double defaultInterestRate = 8.8;
    int balance = 0;
    private IDatabaseFactory databaseFactory;
    private Map<Double, LoanInterestRange> loanInterestRangeMap;

    public LoanEstimator() {
        databaseFactory = new DatabaseFactory();
        loanInterestRangeMap = new HashMap<>();
        loanInterestRangeMap.put(8.0, new LoanInterestRange(10000, 25000));
        loanInterestRangeMap.put(8.2, new LoanInterestRange(25000, 50000));
        loanInterestRangeMap.put(8.5, new LoanInterestRange(50000, 100000));
        loanInterestRangeMap.put(8.8, new LoanInterestRange(100000, Double.POSITIVE_INFINITY));
    }

    @Override
    public String getMenuLabel() {
        return menuLabel;
    }

    @Override
    public void performAction() {
        try {
            setCurrentPageInContext();
            String accountNumber = loggedInUserContext.getAccountNumber();
            IAccountDatabase accountDatabase = databaseFactory.createAccountDatabase();

            balance = accountDatabase.getUserBalance(accountNumber);

            String userInput = userInterface.getMandatoryLongUserInputWithMinimumRange("Enter Loan Amount (minimum " + minimumLoanAmount + "): ", minimumLoanAmount);
            long loanAmount = convertStringToLong(userInput);

            userInput = userInterface.getMandatoryIntegerUserInput("Enter tenure (years): ");
            int tenure = convertStringToInteger(userInput);

            annualInterestRate = getAnnualInterest(balance);

            double emiAmount = calculateEmi(loanAmount, tenure);
            double totalPayableAmount = calculateTotalPayableAmount(emiAmount, tenure);
            double interestPayable = totalPayableAmount - loanAmount;

            userInterface.displayMessage("EMI: " + Math.round(emiAmount));
            userInterface.displayMessage("Interest Payable: " + Math.round(interestPayable));
            userInterface.displayMessage("Total Amount Payable: " + Math.round(totalPayableAmount));
            userInterface.insertEmptyLine();

        } catch (SQLException exception) {
            userInterface.displayMessage("Error occurred in database connection.");
        } catch (Exception exception) {
            userInterface.displayMessage(exception.getMessage());
        }

    }

    public double calculateTotalPayableAmount(double emiAmount, int tenure) {
        return emiAmount * tenure * 12;
    }

    private double getAnnualInterest(int balance) {
        double annualInterest = defaultInterestRate;
        for (Map.Entry<Double, LoanInterestRange> loanInterestRangeEntry : loanInterestRangeMap.entrySet()) {
            LoanInterestRange loanInterestRange = loanInterestRangeEntry.getValue();
            if (loanInterestRange.includes(balance)) {
                annualInterest = loanInterestRangeEntry.getKey();
                break;
            }
        }
        return annualInterest;
    }


    public double calculateEmi(long loanAmount, int tenure) {
        double emiAmount = 0.0;
        //R = monthlyInterestRate
        double monthlyInterestRate = annualInterestRate / 12;
        //N = numberOfMonths
        int numberOfMonths = tenure * 12;
        //EMI = [P x R x (1+R)^N]/[(1+R)^N-1]
        emiAmount = (loanAmount * (monthlyInterestRate / 100) * (Math.pow(1 + (monthlyInterestRate / 100), numberOfMonths))) /
                ((Math.pow(1 + (monthlyInterestRate / 100), numberOfMonths)) - 1);

        return emiAmount;
    }

    @Override
    protected void setCurrentPageInContext() {
        loggedInUserContext.setCurrentPage(menuLabel);
    }
}
