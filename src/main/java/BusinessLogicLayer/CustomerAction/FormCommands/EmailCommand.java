package BusinessLogicLayer.CustomerAction.FormCommands;

import BusinessLogicLayer.User.User;

public class EmailCommand extends FormCommand {
    private final String FIELD_LABEL = "Email";

    public EmailCommand(User user) {
        super(user);
    }

    @Override
    public void execute() {
        String userInput = userInterface.getMandatoryUserInput("Enter Email*: ");
        user.setEmailAddress(userInput);
    }

    @Override
    public String getFieldValue() {
        return user.getEmailAddress();
    }

    @Override
    public String getLabel() {
        return FIELD_LABEL;
    }
}