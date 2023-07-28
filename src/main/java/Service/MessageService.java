package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    // Create a new message
    public Message createMessage(Message message) {
        // Check if the message text is valid.
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }

        // Call the DAO to create the message.
        return messageDAO.createMessage(message);
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }    

    // Get a specific message by its message_id.
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    // Delete a message with a specific message_id.
    public Message deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    // Update the message_text of a message with a specific message_id.
    public Message updateMessage(Message message) {
        // Check if the message text is valid
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }

        // Call the DAO to update the message.
        return messageDAO.updatMessage(message);
    }

    // Get all messages posted by a specific account.
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
