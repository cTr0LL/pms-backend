package com.ysli.personalprojectmanagementsystem.controller;

import com.ysli.personalprojectmanagementsystem.modal.*;
import com.ysli.personalprojectmanagementsystem.request.InviteRequest;
import com.ysli.personalprojectmanagementsystem.response.MessageResponse;
import com.ysli.personalprojectmanagementsystem.service.InvitationService;
import com.ysli.personalprojectmanagementsystem.service.ProjectService;
import com.ysli.personalprojectmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectsByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Project updated = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse res = new MessageResponse("project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InviteRequest request,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
        MessageResponse res = new MessageResponse("User invitation sent");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), invitation.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
