package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    // Constructor to create an instance of AccountService and initialize the AccountDAO.
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    // Register a new account in the system.
    public Account registerAccount(Account account) {

        // Check if username and password meet the registration requirements.
        if (account.getUsername() == null || account.getUsername().isEmpty() || account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        // Create the new account in the database.
        return accountDAO.createAccount(account);
    }

    // Login an existing account in the system.
    public Account loginAccount(Account account) {
        
        // Check if username and password meet the login requirements.
        if (account.getUsername() == null || account.getUsername().isEmpty() || account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        // Check if an account with the provided username exists
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            // If the password matches, return the logged-in account.
            return existingAccount;
        }

        return null;
    }
}
