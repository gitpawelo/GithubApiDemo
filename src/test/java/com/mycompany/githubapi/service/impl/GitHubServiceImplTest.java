package com.mycompany.githubapi.service.impl;

import com.mycompany.githubapi.model.GithubModel;
import com.mycompany.githubapi.service.GitHubService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link GitHubServiceImpl}
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class GitHubServiceImplTest {

    private GitHubService gitHubService;

    @Before
    public void before() {
        gitHubService = new GitHubServiceImpl();
    }


    @Test(expected = NullPointerException.class)
    public void getSortedReposByStars_NullRepoOwner_NullPointerExceptionThrown() {
        gitHubService.getSortedReposByStars(null, "desc");
    }

    @Test(expected = NullPointerException.class)
    public void getSortedReposByStars_NullSortedOrder_NullPointerExceptionThrown() {
        gitHubService.getSortedReposByStars("JohnDoe", null);
    }

    @Test
    public void getSortedReposByStars_SortOrderDesc_WorksCorrectly() {
        final String repoOwner = "gitpawelo";
        final String sortOrderDesc = "stars,desc";

        List<GithubModel> ownerRepos = gitHubService.getSortedReposByStars(repoOwner, sortOrderDesc);

        assertNotNull(ownerRepos);
        assertEquals(ownerRepos.size(), 15);
        assertEquals(ownerRepos.get(0).getRepositoryNumberOfStargazer().intValue(), 1);
    }

    @Test
    public void getSortedReposByStars_SortOrderAsc_WorksCorrectly() {
        final String repoOwner = "gitpawelo";
        final String sortOrderAsc = "stars,asc";

        List<GithubModel> ownerRepos = gitHubService.getSortedReposByStars(repoOwner, sortOrderAsc);

        assertNotNull(ownerRepos);
        assertEquals(ownerRepos.size(), 15);
        assertEquals(ownerRepos.get(0).getRepositoryNumberOfStargazer().intValue(), 0);
    }

    @Test
    public void createModel_CorrectData_WorksCorrectly() {
        String repoName = "testRepo";
        String repoDescription = "Description of testRepo";
        String repoCloneUrl = "www.github.com/testRepo";
        String repoCreationDate = "2019-07-11T19:45:57Z";
        String repoStargazers = "1";

        GithubModel githubModel = gitHubService.createModel(repoName, repoDescription, repoCloneUrl, repoCreationDate, repoStargazers);

        assertNotNull(githubModel);
        assertEquals(githubModel.getRepositoryCreationDate(), "2019-07-11");
        assertEquals(githubModel.getRepositoryNumberOfStargazer().intValue(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void githubOwnersRepo_NullOwnerRepo_NullPointerExceptionThrown() {
        gitHubService.githubOwnersRepo(null);
    }

    @Test
    public void githubOwnersRepo_CorrectData_WorksCorrectly() {
        final String repoOwner = "gitpawelo";
        final String expectedResponseFromUrl = gitHubService.downloadJsonFromURL("https://api.github.com/users/" + repoOwner + "/repos");
        String expectedResponse = gitHubService.githubOwnersRepo(repoOwner);

        assertNotNull(expectedResponse);
        assertEquals(expectedResponseFromUrl, expectedResponse);
    }

    @Test(expected = NullPointerException.class)
    public void downloadJsonFromURL_NullUrlText_NullPointerExceptionThrown() {
        gitHubService.downloadJsonFromURL(null);
    }

    @Test
    public void downloadJsonFromURL_CorrectData_WorksCorrectly() {
        final String repoOwner = "gitpawelo";
        final String expectedResponseFromUrl = gitHubService.downloadJsonFromURL("https://api.github.com/users/" + repoOwner + "/repos");

        assertNotNull(expectedResponseFromUrl);
    }

    @Test(expected = NullPointerException.class)
    public void sortDataInOrder_NullRepos_NullPointerExceptionThrown() {
        gitHubService.sortDataInOrder(null, "stars,desc");
    }

    @Test(expected = NullPointerException.class)
    public void sortDataInOrder_NullSortOrder_NullPointerExceptionThrown() {
        gitHubService.sortDataInOrder(new ArrayList<>(), null);
    }

    @Test
    public void sortDataInOrder_SortOrderDesc_WorksCorrectly() {
        List<GithubModel> repos = new ArrayList<>();
        repos.add(GithubModel.builder()
                .repositoryName("testRepo")
                .repositoryDescription("Description of testRepo")
                .repositoryCloneUrl("cloneUrlRepo")
                .repositoryCreationDate("2019-07-11T19:45:57Z")
                .repositoryNumberOfStargazer(1)
                .build());
        repos.add(GithubModel.builder()
                .repositoryName("testRepo2")
                .repositoryDescription("Description of testRepo2")
                .repositoryCloneUrl("cloneUrlRepo")
                .repositoryCreationDate("2018-07-11T19:45:57Z")
                .repositoryNumberOfStargazer(3)
                .build());
        final String sortOrder = "stars,desc";

        List<GithubModel> sortedRepo = gitHubService.sortDataInOrder(repos, sortOrder);

        assertNotNull(sortedRepo);
        assertEquals(sortedRepo.size(), 2);
        assertEquals(sortedRepo.get(0).getRepositoryNumberOfStargazer().intValue(), 3);
    }

    @Test
    public void sortDataInOrder_SortOrderAsc_WorksCorrectly() {
        List<GithubModel> repos = new ArrayList<>();
        repos.add(GithubModel.builder()
                .repositoryName("testRepo")
                .repositoryDescription("Description of testRepo")
                .repositoryCloneUrl("cloneUrlRepo")
                .repositoryCreationDate("2019-07-11T19:45:57Z")
                .repositoryNumberOfStargazer(1)
                .build());
        repos.add(GithubModel.builder()
                .repositoryName("testRepo2")
                .repositoryDescription("Description of testRepo2")
                .repositoryCloneUrl("cloneUrlRepo")
                .repositoryCreationDate("2018-07-11T19:45:57Z")
                .repositoryNumberOfStargazer(3)
                .build());
        final String sortOrder = "stars,asc";

        List<GithubModel> sortedRepo = gitHubService.sortDataInOrder(repos, sortOrder);

        assertNotNull(sortedRepo);
        assertEquals(sortedRepo.size(), 2);
        assertEquals(sortedRepo.get(0).getRepositoryNumberOfStargazer().intValue(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void convertMapData_NullMap_NullPointerExceptionThrown() {
        gitHubService.convertMapData(null);
    }

    @Test
    public void convertMapData_CorrectData_WorksCorrectly() {
        final String FULL_NAME = "full_name";
        final String DESCRIPTION = "description";
        final String CLONE_URL = "clone_url";
        final String CREATED_AT = "created_at";
        final String STARGAZERS = "stargazers_count";

        Map<String, Object> mapData = new HashMap<>();
        mapData.put(FULL_NAME, "testRepo");
        mapData.put(DESCRIPTION, "Description of testRepo");
        mapData.put(CLONE_URL, "cloneUrlRepo");
        mapData.put(CREATED_AT, "2019-07-11T19:45:57Z");
        mapData.put(STARGAZERS, "1");

        GithubModel gitHubEntity = gitHubService.convertMapData(mapData);

        assertNotNull(gitHubEntity);
        assertEquals(gitHubEntity.getRepositoryNumberOfStargazer().intValue(), 1);
    }

    @Test
    public void convertMapData_NullDescription_WorksCorrectly() {
        final String FULL_NAME = "full_name";
        final String DESCRIPTION = "description";
        final String CLONE_URL = "clone_url";
        final String CREATED_AT = "created_at";
        final String STARGAZERS = "stargazers_count";

        Map<String, Object> mapData = new HashMap<>();
        mapData.put(FULL_NAME, "testRepo");
        mapData.put(DESCRIPTION, null);
        mapData.put(CLONE_URL, "cloneUrlRepo");
        mapData.put(CREATED_AT, "2019-07-11T19:45:57Z");
        mapData.put(STARGAZERS, "1");

        GithubModel gitHubEntity = gitHubService.convertMapData(mapData);

        assertNotNull(gitHubEntity);
        assertEquals(gitHubEntity.getRepositoryDescription(), null);
    }
}