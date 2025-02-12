package com.ysli.personalprojectmanagementsystem.service;


import com.ysli.personalprojectmanagementsystem.modal.Chat;
import com.ysli.personalprojectmanagementsystem.modal.Issue;
import com.ysli.personalprojectmanagementsystem.modal.Project;
import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.repository.ProjectRepository;
import com.ysli.personalprojectmanagementsystem.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;


    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();

        createdProject.setOwner(user);
        createdProject.setIssues(project.getIssues());
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        System.out.println(createdProject);
        Project savedProject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

//        IssueRequest issueRequest = new IssueRequest();
//        issueRequest.setProjectId(savedProject.getId());
//        savedProject.setIssues(issueRequest, );


        return savedProject;
    }

    @Override
    public List<Project> getProjectsByTeam(User user, String category, String tag) throws Exception {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream()
                    .filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if (tag != null) {
            projects = projects.stream()
                    .filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new Exception("No project exists with the id " + projectId);
        }
        return project.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        getProjectById(projectId);
//        userService.findUserById(userId);
        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {

        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());


        return projectRepository.save(project);
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        String partialName = "%" + keyword + "%";
        List<Project> list = projectRepository.findByNameContainingAndTeamContains(keyword, user);
        if (list != null) {
            return list;
        }
        throw new Exception("No Projects available");
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception, Exception {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("project not found"));
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception, Exception {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("project not found"));
        User user = userService.findUserById(userId);
        if (project.getTeam().contains(user)) {
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception, Exception {

        Project project = getProjectById(projectId);
        return project.getChat();
    }
}
