package BusinessLogicLayer.BankAction.FormActionCommands;

import BusinessLogicLayer.CustomerAction.FormCommands.FormCommand;
import BusinessLogicLayer.User.User;
import PresentationLayer.CommonPages.UserInterface;
import PresentationLayer.IPresentationFactory;
import PresentationLayer.MenuPages.IUserForm;
import PresentationLayer.PresentationFactory;

import java.util.Map;

public class EditFormCommand extends FormActionCommand {
    String menuLabel;
    User user;
    Map<Integer, FormCommand> formFieldMap;
    public EditFormCommand(String menuLabel, User user, Map<Integer, FormCommand> formFieldMap) {
        super();
        this.menuLabel = menuLabel;
        this.user = user;
        this.formFieldMap = formFieldMap;
    }

    @Override
    public void execute() {
        IPresentationFactory presentationFactory = new PresentationFactory();
        IUserForm userForm = presentationFactory.createUserForm(formFieldMap, user);
        userForm.executeForm();
        UserInterface userInterface = new UserInterface();
        for (int fieldIndex = 1; fieldIndex <= formFieldMap.size(); fieldIndex++) {
            FormCommand command = formFieldMap.get(fieldIndex);

            userInterface.displayMessage(command.getMenuLabel() + ": " + command.getFieldValue());

        }

    }

    @Override
    public String getMenuLabel() {
        return menuLabel;
    }

}
