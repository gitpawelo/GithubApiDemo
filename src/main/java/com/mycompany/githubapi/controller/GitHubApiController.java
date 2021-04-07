package com.mycompany.githubapi.controller;

import com.mycompany.githubapi.model.GithubModel;
import com.mycompany.githubapi.service.GitHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Github API controller for managing owner's repositories and sorting
 */
@Slf4j
@RestController
@RequestMapping("/repositories")
public class GitHubApiController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubApiController(final GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{owner}")
    public List<GithubModel> getRepoDetailsByOwner(@PathVariable final String owner,
                                                   @RequestParam String sort) {
        log.debug("Collect all repositories for owner with provided sorting order");
        return gitHubService.getSortedReposByStars(owner, sort);
    }
}