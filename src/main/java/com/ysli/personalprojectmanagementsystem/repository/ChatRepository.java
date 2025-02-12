package com.ysli.personalprojectmanagementsystem.repository;

import com.ysli.personalprojectmanagementsystem.modal.Chat;
import com.ysli.personalprojectmanagementsystem.modal.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    Chat findByProject(Project projectById);
}
