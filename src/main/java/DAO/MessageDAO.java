package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    //Create a new message in the database.
    public Message createMessage(Message message) {
        // SQL query to insert a new message into the database.

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // Set values for the prepared statement.
                statement.setInt(1, message.getPosted_by());
                statement.setString(2, message.getMessage_text());
                statement.setLong(3, message.getTime_posted_epoch());

                // Execute the query and check if the message was created successfully.
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        // Set generated message_id to the message and return it.
                        int messageId = resultSet.getInt(1);
                        message.setMessage_id(messageId);
                        return message;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
    }

    // Get all messages from the database.
    public List<Message> getAllMessages() {

        List<Message> messages = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection();

        try {
            // SQL query to select all messages from the database.
            String sql = "SELECT * FROM message";
            
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery(sql);


                // Iterate through the result set and create Message objects from the retrieved data.
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    int postedBy = resultSet.getInt("posted_by");
                    String messageText = resultSet.getString("message_text");
                    long timePostedEpoch = resultSet.getLong("time_posted_epoch");

                    Message message = new Message(messageId, postedBy, messageText, timePostedEpoch);
                    messages.add(message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return messages;
    }

    // Get a specific message by its message_id from the database.
    public Message getMessageById(int messageId) {
        // SQL query to select a message by its message_id from the database.
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

                // Set the message_id value for the prepared statement.
                statement.setInt(1, messageId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Create and return the Message object with the retrieved data
                    int postedBy = resultSet.getInt("posted_by");
                    String messageText = resultSet.getString("message_text");
                    long timePostedEpoch = resultSet.getLong("time_posted_epoch");

                    return new Message(messageId, postedBy, messageText, timePostedEpoch);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
    }

    // Delete a message with a specific message_id from the database.
    public Message deleteMessage(int messageId) {
        
        Connection connection = ConnectionUtil.getConnection();

        try {

            // SQL query to delete a message by its message_id from the database.
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            // Set the message_id value for the prepared statement 
            statement.setInt(1, messageId);
            int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    // If the message was deleted successfully, return the delted Message object
                    return getMessageById(messageId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
    }

    // Update the message_text of a message with a specific message_id in the database.
    public Message updatMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();

        try {

                String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set the values for the prepared statement 
                statement.setString(1, message.getMessage_text());
                statement.setInt(2, message.getMessage_id());

                // Execute the query and check if the message was updated successfully.
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // If the message was updated sucessfully, return the updated Message object.
                    return getMessageById(message.getMessage_id());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
    }

    // Get all messages posted by a specific account from the database.

    public List<Message> getMessagesByAccountId(int accountId) {
        // SQL query to select message posted by a specific account from the database.

        List<Message> messages = new ArrayList<>();

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the accountId value for the prepared statement.
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

                // Iterate through the result set and create Message objects from the retrieved data.
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    int postedBy = resultSet.getInt("posted_by");
                    String messageText = resultSet.getString("message_text");
                    long timePostedEpoch = resultSet.getLong("time_posted_epoch");

                    Message message = new Message(messageId, postedBy, messageText, timePostedEpoch);
                    messages.add(message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return messages;
    }
}
