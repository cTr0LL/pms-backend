package com.ysli.personalprojectmanagementsystem.service;

import com.ysli.personalprojectmanagementsystem.modal.Chat;

public interface ChatService {

    Chat createChat(Chat chat);

    //Chat getChatsByProjectId(Long projectId) throws ChatException, ProjectException;

//	Chat addUsersToChat(Long chatId, List<Long> userIds) throws ChatException;


//	 List<Chat> searchChatsByName(String name) throws ChatException;

    // Other methods as needed
}
