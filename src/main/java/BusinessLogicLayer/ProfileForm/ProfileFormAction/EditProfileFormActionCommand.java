package BusinessLogicLayer.ProfileForm.ProfileFormAction;

import BusinessLogicLayer.ProfileForm.CommonProfileForm.AbstractFormCommand;
import BusinessLogicLayer.ProfileForm.CommonProfileForm.IAbstractFormCommand;
import BusinessLogicLayer.User.AbstractProfile;
import BusinessLogicLayer.User.CustomerProfile;
import PresentationLayer.Pages.CommonPages.IUserInterfacePage;
import PresentationLayer.Pages.CommonPages.IUserFormPage;

import java.util.Map;

public class EditProfileFormActionCommand extends AbstractFormCommand {
    private static final String COMMAND_LABEL = "Edit";
    private static final String COMMAND_TYPE = "ACTION";

    AbstractProfile profile;
    Map<Integer, IAbstractFormCommand> formFieldMap;

    public EditProfileFormActionCommand(AbstractProfile profile, Map<Integer, IAbstractFormCommand> formFieldMap) {
        super(new CustomerProfile());
        this.profile = profile;
        this.formFieldMap = formFieldMap;
    }

    @Override
    public void execute() {
        IUserFormPage userForm = commonPagesFactory.createUserForm(formFieldMap, profile);
        userForm.printForm();

        IUserInterfacePage userInterface = commonPagesFactory.createUserInterface();
        for (int fieldIndex = 1; fieldIndex <= formFieldMap.size(); fieldIndex++) {
           IAbstractFormCommand command = formFieldMap.get(fieldIndex);

            userInterface.displayMessage(command.getCommandLabel() + ": " + command.getFieldValue());
        }
    }

    @Override
    public String getFieldValue() {
        return COMMAND_TYPE;
    }

    @Override
    public String getCommandLabel() {
        return COMMAND_LABEL;
    }

}
