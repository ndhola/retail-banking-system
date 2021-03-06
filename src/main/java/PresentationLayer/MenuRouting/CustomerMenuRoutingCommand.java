package PresentationLayer.MenuRouting;

import BusinessLogicLayer.ActionFactory;
import BusinessLogicLayer.CommonAction.IAbstractAction;
import BusinessLogicLayer.IActionFactory;
import PresentationLayer.Pages.IAbstractPage;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerMenuRoutingCommand extends MenuRoutingCommand {

    private static final String PAGE_NAME = "Customer";

    private Map<String, IAbstractAction> menu;

    public CustomerMenuRoutingCommand() {
        IActionFactory actionFactory = new ActionFactory();
        menu = new LinkedHashMap<>();
        menu.put("1", actionFactory.createUpdatePersonalDetails());
        menu.put("2", actionFactory.createWithdraw());
        menu.put("3", actionFactory.createDeposit());
        menu.put("4", actionFactory.createTransfer());
        menu.put("5", actionFactory.createBankStatement());
        menu.put("6", actionFactory.createCheckBalance());
        menu.put("7", actionFactory.createUpdatePassword());
        menu.put("8", actionFactory.createLoanEstimator());
        menu.put("9", actionFactory.createCheckPreApprovedLoan());
        menu.put("10", actionFactory.createSignOut());
    }

    @Override
    public void execute() {
        IAbstractPage menuPage = new MenuPage(menu, PAGE_NAME);
        menuPage.printPage();
    }
}
