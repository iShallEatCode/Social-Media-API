package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class SocialMediaController {
    
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI(){
        Javalin app = Javalin.create();

        app.post("/register", this::registerUserHandler);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::handleGetMessagesById);
        app.delete("/messages/{message_id}", this::handleDeleteMessage);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);
        


        return app;
    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = ctx.bodyAsClass(Account.class);
        Account registeredAccount = accountService.registerAccount(account);

        if (registeredAccount != null) {
            ctx.status(200);
            ctx.json(objectMapper.writeValueAsString(registeredAccount));
        } else {
            ctx.status(400);
        }
    }

    private void handleLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = ctx.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount != null) {
            ctx.status(200);
            ctx.json(objectMapper.writeValueAsString(loggedInAccount));
        } else {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            ctx.json(createdMessage).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void handleGetMessagesById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message).status(200);
        } else {
            ctx.json("Message not found").status(200);
        }
    }

    private void handleDeleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.status(404);
        }
    }

    private void handleUpdateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = ctx.bodyAsClass(Message.class);
        updatedMessage.setMessage_id(messageId);
        Message message = messageService.updateMessage(updatedMessage);
        if (message != null) {
            ctx.json(message).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void handleGetMessagesByAccountId(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messagesByAccount = messageService.getMessagesByAccountId(accountID);
        ctx.json(messagesByAccount).status(200);
    }
}