package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

// This class will handle the data access operations related to the 'Account' entity. It will be responsible for creating new accounts and retrieving account information from the database.

public class AccountDAO {

    // Create a new account in the database.
    public Account createAccount(Account account) {
        
        Connection connection = ConnectionUtil.getConnection();        
        
        try {

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());

            int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        int accountId = resultSet.getInt(1);
                        account.setAccount_id(accountId);
                        return account;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            return null;
    }

    // Get an account from the database by its username.
    public Account getAccountByUsername(String username) {

        Connection connection = ConnectionUtil.getConnection();
        

        try {
            
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String password = resultSet.getString("password");
                return new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
