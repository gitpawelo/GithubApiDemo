package com.mycompany.githubapi.service.impl;

import com.google.gson.Gson;
import com.mycompany.githubapi.model.GithubModel;
import com.mycompany.githubapi.service.GitHubService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Class with implementation of {@link GitHubService}
 */
@Service
@Slf4j
public class GitHubServiceImpl implements GitHubService {

    @Override
    public List<GithubModel> getSortedReposByStars(@NonNull final String ownerRepo, @NonNull final String sortOrder) {
        log.debug("Collect all repositories in sorted order");
        Map<String, Object>[] mapCollection = getMapFromJson(githubOwnersRepo(ownerRepo));
        List<GithubModel> repos = new ArrayList<>();
        for (Map<String, Object> githubRepo : mapCollection
        ) {
            repos.add(convertMapData(githubRepo));
        }
        return sortDataInOrder(repos, sortOrder);
    }

    @Override
    public GithubModel convertMapData(@NonNull final Map githubRepo) {
        log.debug("Convert data from Map to entity");
        final String FULL_NAME = "full_name";
        final String DESCRIPTION = "description";
        final String CLONE_URL = "clone_url";
        final String CREATED_AT = "created_at";
        final String STARGAZERS = "stargazers_count";

        log.debug("Retrieve data from Map to String values");
        String repoName = githubRepo.get(FULL_NAME).toString();
        String repoDescription = "";
        if (githubRepo.get(DESCRIPTION) == null) {
            repoDescription = null;
        } else {
            repoDescription = githubRepo.get(DESCRIPTION).toString();
        }
        String repoCloneUrl = githubRepo.get(CLONE_URL).toString();
        String repoCreationDate = githubRepo.get(CREATED_AT).toString();
        String repoStargazers = githubRepo.get(STARGAZERS).toString();

        log.debug("Create model with necessary values");
        GithubModel gitHubEntity = createModel(repoName, repoDescription, repoCloneUrl, repoCreationDate, repoStargazers);

        return gitHubEntity;
    }

    @Override
    public GithubModel createModel(String fullNameRepo, String descriptionRepo, String cloneUrlRepo, String createdDateRepo, String stargazers) {
        log.debug("Fulfill entity with provided parameters");
        return GithubModel.builder()
                .repositoryName(fullNameRepo)
                .repositoryDescription(descriptionRepo)
                .repositoryCloneUrl(cloneUrlRepo)
                .repositoryCreationDate(createLocalDate(createdDateRepo))
                .repositoryNumberOfStargazer(convertDecimalStringToInteger(stargazers))
                .build();
    }

    @Override
    public HashMap<String, Object>[] getMapFromJson(@NonNull final String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, HashMap[].class);
    }

    @Override
    public String githubOwnersRepo(@NonNull final String owner) {
        log.debug("Save url response as String value");
        String githubApiResponse = downloadJsonFromURL("https://api.github.com/users/" + owner + "/repos");
        return githubApiResponse;
    }

    @Override
    public List<GithubModel> sortDataInOrder(@NonNull final List<GithubModel> repos, @NonNull final String sortOrder) {
        log.debug("Sort all repositories by provided order");
        String orderParameter = Stream
                .of(sortOrder.split(","))
                .reduce((first, last) -> last).get();

        if (orderParameter.equalsIgnoreCase("desc")) {
            log.debug("Sort collection in descending order");
            repos.sort(Comparator.comparing(GithubModel::getRepositoryNumberOfStargazer).reversed());
        } else {
            log.debug("Sort collection in ascending order");
            repos.sort(Comparator.comparing(GithubModel::getRepositoryNumberOfStargazer));
        }
        return repos;
    }

    /**
     * Method to convert decimal String to Integer
     *
     * @param stargazers the count of stars for repository
     * @return number as a Integer
     */
    private Integer convertDecimalStringToInteger(@NonNull final String stargazers) {
        log.debug("Convert decimal String to Integer");
        return (int) Double.parseDouble((stargazers));
    }

    /**
     * Method to convert Date to ISO format
     *
     * @param createdDateRepo the creation date of respository
     * @return date in ISO format
     */
    private String createLocalDate(String createdDateRepo) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdDateRepo);
        return zonedDateTime.toLocalDate().toString();
    }

    @Override
    public String downloadJsonFromURL(@NonNull final String urlText) {
        log.debug("Save Url response as a String value");
        try {
            URL myUrl = new URL(urlText);
            StringBuilder jsonText = new StringBuilder();
            try (InputStream myInputStream = myUrl.openStream();
                 Scanner scanner = new Scanner(myInputStream)) {
                while (scanner.hasNextLine()) {
                    jsonText.append(scanner.nextLine());
                }
                return jsonText.toString();
            }
        } catch (IOException e) {
            System.err.println("Failed to get content from URL " + urlText + " due to exception:" + e.getMessage());
            return null;
        }
    }
}
