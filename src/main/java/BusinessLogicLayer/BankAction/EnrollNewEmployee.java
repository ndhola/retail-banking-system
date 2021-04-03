package BusinessLogicLayer.BankAction;

import BusinessLogicLayer.BankAction.FormActionCommands.BackToMainMenuCommand;
import BusinessLogicLayer.BankAction.FormActionCommands.EditFormCommand;
import BusinessLogicLayer.BankAction.FormActionCommands.SaveNewEmployeeFormCommand;
import BusinessLogicLayer.CommonAction.Action;
import BusinessLogicLayer.CustomerAction.FormCommands.*;
import BusinessLogicLayer.User.BankEmployeeProfile;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnrollNewEmployee extends Action {
    private static final String menuLabel = "Enroll New Employee";
    Map<Integer,IFormCommand> formActionCommandMap;
    private Map<Integer,IFormCommand> openNewAccountFormFieldMap;
    BankEmployeeProfile bankEmployeeProfile;
    public EnrollNewEmployee() {
        super();
        bankEmployeeProfile = new BankEmployeeProfile();
        getOpenNewAccountFormFieldMap();
        formActionCommandMap = new LinkedHashMap<>();
        formActionCommandMap.put(1, new EditFormCommand("Edit", bankEmployeeProfile, openNewAccountFormFieldMap));
        formActionCommandMap.put(2, new SaveNewEmployeeFormCommand("Save", bankEmployeeProfile));
        formActionCommandMap.put(3, new BackToMainMenuCommand("Back to main menu"));
    }

    @Override
    public String getMenuLabel() {
        return menuLabel;
    }

    @Override
    protected void setCurrentPageInContext() {
        loggedInUserContext.setCurrentPage(menuLabel);
    }

    @Override
    public void performAction() {
        setCurrentPageInContext();
        userInterface.displayMessage("Please enter the details of following fields:");
        userInterface.displayMessage("Note: (*) are mandatory fields.");
        userInterface.insertEmptyLine();

        for (Map.Entry<Integer,IFormCommand> formQuestionEntry : openNewAccountFormFieldMap.entrySet()) {
           IFormCommand formCommand = formQuestionEntry.getValue();
            formCommand.execute();
        }


        while (loggedInUserContext.checkCurrentPageStatus(menuLabel)) {
            int key = 1;
            for (int i = 0; i < formActionCommandMap.size(); i++) {
               IFormCommand formState = formActionCommandMap.get(key);
                System.out.println(key + ". " + formState.getCommandLabel());
                key = key + 1;
            }
            String action = userInterface.getMandatoryIntegerUserInput("Enter any Number between 1-" + formActionCommandMap.size() + " to perform appropriate action:");

           IFormCommand formCommand = formActionCommandMap.get(Integer.parseInt(action));
            formCommand.execute();

        }
    }

    private void getOpenNewAccountFormFieldMap() {
        openNewAccountFormFieldMap = new LinkedHashMap<>();
        openNewAccountFormFieldMap.put(1, new FirstNameCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(2, new MiddleNameCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(3, new LastNameCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(4, new AddressLine1Command(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(5, new AddressLine2Command(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(6, new CityCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(7, new ProvinceCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(8, new ContactCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(9, new EmailCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(10, new PassPortNumberCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(11, new SSNNumberCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(12, new DOBCommand(bankEmployeeProfile));
    }

}
