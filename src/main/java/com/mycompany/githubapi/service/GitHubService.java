package com.mycompany.githubapi.service;

import com.mycompany.githubapi.model.GithubModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GitHubService {

    /**
     * Method for return List of {@link GithubModel} in sorted order
     *
     * @param ownerRepo the owner of the repository
     * @param sortOrder the order of sorting
     * @return Collection of {@link GithubModel}
     */
    List<GithubModel> getSortedReposByStars(String ownerRepo, String sortOrder);

    /**
     * Method for convert data from json response to Map
     *
     * @param githubRepo the owner's repository
     * @return entity of {@link GithubModel}
     */
    GithubModel convertMapData(Map githubRepo);

    /**
     * Method for create entity of{@link GithubModel}
     *
     * @param fullNameRepo    the repository name
     * @param descriptionRepo the repository description
     * @param cloneUrlRepo    the repository url
     * @param createdDateRepo the repository creation date
     * @param stargazers      the repository stars counter
     * @return entity of {@link GithubModel}
     */
    GithubModel createModel(String fullNameRepo, String descriptionRepo, String cloneUrlRepo, String createdDateRepo, String stargazers);

    /**
     * Method to retrieve Map data from received json
     *
     * @param jsonString the JSON response as String value
     * @return {@link HashMap} of json response
     */
    HashMap<String, Object>[] getMapFromJson(String jsonString);

    /**
     * Method for parse json response to String value
     *
     * @param owner the repository owner
     * @return json response as a String
     */
    String githubOwnersRepo(String owner);

    /**
     * Method to sort owner repositories by collected stars in ascending/descending order
     *
     * @param repos     the owner repository
     * @param sortOrder the sorted order
     * @return collection of {@link GithubModel}
     */
    List<GithubModel> sortDataInOrder(List<GithubModel> repos, String sortOrder);

    /**
     * Method for parse response json to String
     *
     * @param urlText the url text
     * @return converted json response as a String
     */
    String downloadJsonFromURL(String urlText);
}
