package com.ysli.personalprojectmanagementsystem.service;

import com.ysli.personalprojectmanagementsystem.modal.Chat;
import com.ysli.personalprojectmanagementsystem.modal.Project;
import com.ysli.personalprojectmanagementsystem.modal.User;

import java.util.List;


public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;

//	List<Project> getProjectsByOwner(User owner) throws ProjectException;

    List<Project> getProjectsByTeam(User user, String category, String tag) throws Exception;


    Project getProjectById(Long projectId) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception, Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception, Exception;

    Chat getChatByProjectId(Long projectId) throws Exception, Exception;


}
