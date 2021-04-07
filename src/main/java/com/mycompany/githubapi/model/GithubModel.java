package com.mycompany.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for Github API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubModel {
    @JsonProperty("fullName")
    private String repositoryName;

    @JsonProperty("description")
    private String repositoryDescription;

    @JsonProperty("cloneUrl")
    private String repositoryCloneUrl;

    @JsonProperty("stars")
    private Integer repositoryNumberOfStargazer;

    @JsonProperty("createdAt")
    private String repositoryCreationDate;
}
