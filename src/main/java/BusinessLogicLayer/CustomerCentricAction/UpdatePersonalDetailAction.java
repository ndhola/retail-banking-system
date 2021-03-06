package BusinessLogicLayer.CustomerCentricAction;

import BusinessLogicLayer.ProfileForm.CommonProfileForm.IAbstractFormCommand;
import BusinessLogicLayer.ProfileForm.CommonProfileForm.IProfileFormFactory;
import BusinessLogicLayer.ProfileForm.CommonProfileForm.ProfileFormFactory;
import BusinessLogicLayer.CommonAction.AbstractAction;
import BusinessLogicLayer.User.AbstractProfile;
import DataAccessLayer.ProfileDatabase.ICustomerProfileDatabase;
import DataAccessLayer.ProfileDatabase.IProfileDatabaseFactory;
import PresentationLayer.Pages.CommonPages.IUserFormPage;

import java.util.HashMap;
import java.util.Map;

public class UpdatePersonalDetailAction extends AbstractAction {
    private static final String ACTION_TITLE = "Update Personal Details";

    private Map<Integer, IAbstractFormCommand> formFields;
    private ICustomerProfileDatabase customerProfileDatabase;
    private IUserFormPage userForm;

    public UpdatePersonalDetailAction() {
        formFields = new HashMap<>();

        IProfileDatabaseFactory profileDatabaseFactory = databaseFactory.createProfileDatabaseFactory();
        customerProfileDatabase = profileDatabaseFactory.createCustomerProfileDatabase();
    }

    public UpdatePersonalDetailAction(ICustomerProfileDatabase customerProfileDatabase) {
        formFields = new HashMap<>();
        this.customerProfileDatabase = customerProfileDatabase;
    }

    @Override
    public String getActionTitle() {
        return ACTION_TITLE;
    }

    @Override
    public void performAction() {
        userInterface.displayMessage(ACTION_TITLE);

        String currentUserAccountNumber = loggedInUserContext.getAccountNumber();
        setCurrentPageInContext();

        AbstractProfile customerProfile = customerProfileDatabase.getCustomerProfile(currentUserAccountNumber);
        if (null == customerProfile) {
            userInterface.displayMessage("No user found!");
        } else {
            userForm = commonPagesFactory
                    .createUserForm(getFormFields(customerProfile), customerProfile, loggedInUserContext.getCurrentPage());
            userForm.printForm();
        }
    }

    private Map<Integer, IAbstractFormCommand> getFormFields(AbstractProfile profile) {
        IProfileFormFactory profileFormFactory = new ProfileFormFactory();

        formFields.put(1, profileFormFactory.createFirstNameFieldCommand(profile));
        formFields.put(2, profileFormFactory.createMiddleNameFieldCommand(profile));
        formFields.put(3, profileFormFactory.createLastNameFieldCommand(profile));
        formFields.put(4, profileFormFactory.createAddressLine1FieldCommand(profile));
        formFields.put(5, profileFormFactory.createAddressLine2FieldCommand(profile));
        formFields.put(6, profileFormFactory.createCityFieldCommand(profile));
        formFields.put(7, profileFormFactory.createProvinceFieldCommand(profile));
        formFields.put(8, profileFormFactory.createContactFieldCommand(profile));
        formFields.put(9, profileFormFactory.createEmailFieldCommand(profile));
        formFields.put(10, profileFormFactory.createPassPortNumberFieldCommand(profile));
        formFields.put(11, profileFormFactory.createSsnNumberFieldCommand(profile));
        formFields.put(12, profileFormFactory.createDateOfBirthFieldCommand(profile));
        formFields.put(13, profileFormFactory.createSaveUpdatedPersonalDetailFormActionCommand(profile));
        formFields.put(14, profileFormFactory.createBackToMainMenuProfileFormActionCommand());

        return formFields;
    }

    @Override
    protected void setCurrentPageInContext() {
        loggedInUserContext.setCurrentPage(ACTION_TITLE);
    }
}
