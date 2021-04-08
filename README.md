# GitHub API Demo
The purpose of this documentation is briefly description of the individual component, its content and the basic configuration.


## Description
Service to retrieve all public repositories from https://github.com website based on owners' name.
Response contains 5 fields for each repository:
* repository name
* repository description
* repository clone_url
* repository stargazers
* repository creation date in ISO format

Repositories can be sorted by given stars in ascending/descending order.

## Call example
To retrieve repositories sorted ascending by stars:
* http://localhost:8080/repositories/:owner?sort=stars,asc

To retrieve repositories sorted descending by stars:
* http://localhost:8080/repositories/:owner?sort=stars,desc

## Module
* GithubApiDemo - all common parts of the back-end applications (data model, services, annotations, etc.) 


## Service ports
Service provided by the system runs on communication port:
* service
    * githubAPI - 8080 (http)

## How to build
To build project just type in terminal:
```
mvn clean install
```