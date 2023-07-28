package Controller;

import static org.mockito.ArgumentMatchers.contains;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

// The main controller class will handle incoming HTTP requests and assign the processing to the appropriate service classes.

public class SocialMediaControllerTEMP {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaControllerTEMP() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        // Endpoints for Account-related operations
        app.post("/register", this::handleRegistration);
        app.post("/login", this::handleLogin);

        // Endpoints for Message-related operations
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessagesById);
        app.delete("/messages/{message_id}", this::handleDeleteMessage);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);

        // Endpoints for retrieving messages by account_id
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void handleRegistration(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerAccount(account);
        if (createdAccount != null) {
            context.json(createdAccount).status(200);
        } else {
            context.status(400);
        }
    }

    private void handleLogin(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account loggedInAccount  = accountService.loginAccount(account);
        if (loggedInAccount != null) {
            context.json(loggedInAccount).status(200);
        } else {
            context.status(401);
        }
    }

    private void handleCreateMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            context.json(createdMessage).status(200);
        } else {
            context.status(400);
        }
    }

    private void handleGetAllMessages(Context context) {
        List<Message> allMessages = messageService.getAllMessages();
        context.json(allMessages).status(200);
    }

    private void handleGetMessagesById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message).status(200);
        } else {
            context.json("Message not found").status(200);
        }
    }

    private void handleDeleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage).status(200);
        } else {
            context.json("Message not found").status(200);
        }
    }

    private void handleUpdateMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = context.bodyAsClass(Message.class);
        updatedMessage.setMessage_id(messageId);
        Message message = messageService.updateMessage(updatedMessage);
        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(400);
        }
    }

    private void handleGetMessagesByAccountId(Context context) {
        int accountID = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messagesByAccount = messageService.getMessagesByAccountId(accountID);
        context.json(messagesByAccount).status(200);
    }


}