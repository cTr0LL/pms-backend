package com.ysli.personalprojectmanagementsystem.controller;


import com.ysli.personalprojectmanagementsystem.modal.Issue;
import com.ysli.personalprojectmanagementsystem.modal.IssueDTO;
import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.request.IssueRequest;
import com.ysli.personalprojectmanagementsystem.response.AuthResponse;
import com.ysli.personalprojectmanagementsystem.response.MessageResponse;
import com.ysli.personalprojectmanagementsystem.service.IssueService;
import com.ysli.personalprojectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<List<Issue>> getAllIssues() throws IssueException {
//        List<Issue> issues = issueService.getAllIssues();
//        return ResponseEntity.ok(issues);
//    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId)
            throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));

    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token
    ) throws Exception {

        System.out.println("issue-----" + issue);

        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issue, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setProjectID(createdIssue.getProjectID());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());


        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);

    }

//    @PutMapping("/{issueId}")
//    public ResponseEntity<Issue> updateIssue(@PathVariable Long issueId, @RequestBody IssueRequest updatedIssue,
//                                             @RequestHeader("Authorization") String token) throws IssueException, UserException, ProjectException {
//        User user = userService.findUserProfileByJwt(token);
//        System.out.println("user______>" + user);
//        Issue updated = issueService.updateIssue(issueId, updatedIssue, user.getId()).get();
//
//        return updated != null ?
//                ResponseEntity.ok(updated) :
//                ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());
//        String deleted = issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue deleted");
//        res.setStatus(true);

        return ResponseEntity.ok(res);

    }


//    @GetMapping("/search")
//    public ResponseEntity<List<Issue>> searchIssues(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) String priority,
//            @RequestParam(required = false) Long assigneeId
//    ) throws IssueException {
//        // You can add more parameters as needed for your filtering criteria
//        // Use the parameters to build a search query and call the service method
//
//        List<Issue> filteredIssues = issueService.searchIssues(title, status, priority, assigneeId);
//
//        return ResponseEntity.ok(filteredIssues);
//    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId
    ) throws Exception {

        Issue issue = issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issue);

    }

//    @GetMapping("/assignee/{assigneeId}")
//    public ResponseEntity<List<Issue>> getIssuesByAssigneeId(
//            @PathVariable Long assigneeId
//    ) throws Exception {
//        List<Issue> issues = issueService.getIssuesByAssigneeId(assigneeId);
//        return ResponseEntity.ok(issues);
//    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable String status,
            @PathVariable Long issueId
    ) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
