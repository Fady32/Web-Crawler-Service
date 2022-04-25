Web crawler in Spring Boot. The crawler should be limited to one domain - so when you start with https://monzo.com/, it
would crawl all pages within monzo.com, but not follow external links, for example to the Facebook and Twitter accounts.
Given a URL, it should print a simple site map, showing the links between pages.

### Notes

- This project uses a spring cache locally.
- Current configuration is for running this application locally, to configure the application for a different type of
  environment, you'll need to add/edit some config e.g things related to docker networking. Also, you could utilize
  spring profiles along with maven profiles to create different configurations based on your needs.

### Technologies

- Java 11
- Spring Boot
- Maven
- Docker

### Deployment

To Execute the springboot application please the below docker compose file:

First, navigate to this docker-compose file

```
- docker-compose up -d
```

To stop the container

```
-  docker-compose down
```

open your browser on the home page of swagger :

(http://127.0.0.1:8080/crawler-api/swagger-ui/)