package BusinessLogicLayer.BankCentricAction;

import BusinessLogicLayer.ProfileForm.CommonProfileForm.IAbstractFormCommand;
import BusinessLogicLayer.ProfileForm.CommonProfileForm.IProfileFormFactory;
import BusinessLogicLayer.ProfileForm.CommonProfileForm.ProfileFormFactory;
import BusinessLogicLayer.CommonAction.AbstractAction;
import BusinessLogicLayer.User.AbstractProfile;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnrollNewEmployeeAction extends AbstractAction {
    private static final String ACTION_TITLE = "Enroll New Employee";

    private Map<Integer, IAbstractFormCommand> formActionCommandMap;
    private Map<Integer, IAbstractFormCommand> openNewAccountFormFieldMap;
    private AbstractProfile bankEmployeeProfile;
    private IProfileFormFactory profileFormFactory;

    public EnrollNewEmployeeAction() {
        super();
        profileFormFactory = new ProfileFormFactory();
        bankEmployeeProfile = userFactory.createBankEmployeeProfile();

        generateOpenNewAccountFormFieldMap();
        formActionCommandMap = new LinkedHashMap<>();
        formActionCommandMap.put(1, profileFormFactory.createEditProfileFormActionCommand(bankEmployeeProfile, openNewAccountFormFieldMap));
        formActionCommandMap.put(2, profileFormFactory.createSaveNewEmployeeProfileFormActionCommand(bankEmployeeProfile));
        formActionCommandMap.put(3, profileFormFactory.createBackToMainMenuProfileFormActionCommand());
    }

    public EnrollNewEmployeeAction(Map<Integer, IAbstractFormCommand> openNewAccountFormFieldMap, Map<Integer, IAbstractFormCommand> formActionCommandMap) {
        super();
        profileFormFactory = new ProfileFormFactory();
        bankEmployeeProfile = userFactory.createBankEmployeeProfile();

        this.openNewAccountFormFieldMap = openNewAccountFormFieldMap;
        this.formActionCommandMap = formActionCommandMap;
    }

    @Override
    public String getActionTitle() {
        return ACTION_TITLE;
    }

    @Override
    protected void setCurrentPageInContext() {
        loggedInUserContext.setCurrentPage(ACTION_TITLE);
    }

    @Override
    public void performAction() {
        setCurrentPageInContext();
        userInterface.displayMessage("Please enter the details of following fields:");
        userInterface.displayMessage("Note: (*) are mandatory fields.");
        userInterface.insertEmptyLine();

        for (Map.Entry<Integer, IAbstractFormCommand> formQuestionEntry : openNewAccountFormFieldMap.entrySet()) {
            IAbstractFormCommand formCommand = formQuestionEntry.getValue();
            formCommand.execute();
        }

        if (0 < formActionCommandMap.size()) {
            while (loggedInUserContext.checkCurrentPageStatus(ACTION_TITLE)) {
                int key = 1;
                for (int i = 0; i < formActionCommandMap.size(); i++) {
                    IAbstractFormCommand formCommand = formActionCommandMap.get(key);
                    userInterface.displayMessage(key + ". " + formCommand.getCommandLabel());
                    key = key + 1;
                }
                String action = userInterface.getMandatoryIntegerUserInput("Enter any Number between 1-" + formActionCommandMap.size() + " to perform appropriate action:");

                IAbstractFormCommand formCommand = formActionCommandMap.get(Integer.parseInt(action));
                formCommand.execute();
            }
        }
    }

    private void generateOpenNewAccountFormFieldMap() {
        openNewAccountFormFieldMap = new LinkedHashMap<>();
        openNewAccountFormFieldMap.put(1, profileFormFactory.createFirstNameFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(2, profileFormFactory.createMiddleNameFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(3, profileFormFactory.createLastNameFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(4, profileFormFactory.createAddressLine1FieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(5, profileFormFactory.createAddressLine2FieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(6, profileFormFactory.createCityFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(7, profileFormFactory.createProvinceFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(8, profileFormFactory.createContactFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(9, profileFormFactory.createEmailFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(10, profileFormFactory.createPassPortNumberFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(11, profileFormFactory.createSsnNumberFieldCommand(bankEmployeeProfile));
        openNewAccountFormFieldMap.put(12, profileFormFactory.createDateOfBirthFieldCommand(bankEmployeeProfile));
    }
}
