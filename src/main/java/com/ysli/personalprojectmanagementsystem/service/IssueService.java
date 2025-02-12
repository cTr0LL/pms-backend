package com.ysli.personalprojectmanagementsystem.service;

import com.ysli.personalprojectmanagementsystem.modal.Issue;
import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, Long userid) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;

    //    Optional<Issue> updateIssue(Long issueid, IssueRequest updatedIssue, Long userid) throws IssueException, UserException, ProjectException;

//    List<Issue> getIssuesByAssigneeId(Long assigneeId) throws IssueException;

//    List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws IssueException;

    //    List<User> getAssigneeForIssue(Long issueId) throws IssueException;

    //	 List<Issue> getAllIssues() throws IssueException;

}
