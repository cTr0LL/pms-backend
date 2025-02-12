package com.ysli.personalprojectmanagementsystem.controller;

import com.ysli.personalprojectmanagementsystem.modal.Chat;
import com.ysli.personalprojectmanagementsystem.modal.Message;
import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.request.CreateMessageRequest;
import com.ysli.personalprojectmanagementsystem.service.MessageService;
import com.ysli.personalprojectmanagementsystem.service.ProjectService;
import com.ysli.personalprojectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)
            throws Exception {

        User user = userService.findUserById(request.getSenderId());
        if (user == null) throw new Exception("user Not found with id " + request.getSenderId());

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();  // This method should throw ChatException if the chat is not found
        if (chats == null) throw new Exception("Chats not found");
        
        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId)
            throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
