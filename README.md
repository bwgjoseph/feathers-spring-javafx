# Feathers Spring JavaFX

This is a project showcase on how to integrate [Springboot](https://spring.io/projects/spring-boot) [JavaFX](https://openjfx.io/) Client with [feathersjs](https://feathersjs.com/) Server, communicate via [socketio-client-java](https://github.com/socketio/socket.io-client-java). It also uses numerous features from Spring, and applying Spring best practices

The key concept is to demo on how to:

1. Create JavaFX client integration with [Springboot](https://spring.io/projects/spring-boot) and [Javafx-weaver](https://github.com/rgielen/javafx-weaver)
2. Initialize websocket connection to feathersjs server via [socketio-client-java](https://github.com/socketio/socket.io-client-java), including auto-reconnection
3. Use of `SmartInitializingSingleton` to wait until after bean has initialized before performing some actions. See `WebSocketInitializer`
4. Use of `ApplicationListener` either through `implements` or `@EventListener` to trigger some action, where we can use [SpEL](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#expressions) to conditional listen to certain event. See `UserSocketListener` and `MainController`
5. Use of `ApplicationEventPublisher` to `publish` (custom) events and `@EventListener` to `subscribe` for events
6. Use of [constructor-injection](https://reflectoring.io/constructor-injection/) as DI best practice
7. Use of [@ConfigurationProperties](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-typesafe-configuration-properties) for typesafe configuration. See `FeathersConfig`
8. Use of [configuration-metadata](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/appendix-configuration-metadata.html#configuration-metadata-annotation-processor) to provide auto-completion in `application.properties`
9. Use of Spring-Security [org.springframework.security.core.Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/api/) to store authenticated user into `SecurityContextHolder` and allow to retrieve via `@AuthenticatedPrincipal` or `@CurrentUser`
10. Easily use of POJO object and send through websocket using `ObjectMapper` and `JSONObject`. See `AuthenticationRequest` or `User` entity class `toJSONObject` method

The following are yet to be done:

1. Use of [Transports and HTTP Headers](https://github.com/socketio/socket.io-client-java#transports-and-http-headers)?
2. Use of JWT Token for re-authentication after disconnect
3. Use of `@Validated` in `@ConfigurationProperties`
4. Use of [automatic-versioning-of-java-applications-using-git-version-gradle-plugin](https://98elements.com/blog/automatic-versioning-of-java-applications-using-git-version-gradle-plugin/)
5. Add support for params in CRUD
6. Use native query builder (like mongo-sdk) to write the query, and then output to json which will be sent to server through socket
7. Initialize `ObjectMapper` with default prettyprint and timeformat etc

## Configuration

1. Enable `Automatic Project Synchronization` in `Preference > Gradle`
2. Add the following to `build.gradle` which will output `build.info` in `.jar\BOOT-INF\classes\META-INF\` when build

```gradle
springBoot {
  buildInfo()
}

// optionally can add
eclipse {
    autoBuildTasks bootBuildInfo
}
```

# Note

1. No VM args is required (especially for JavaFX) in `Run Configurations` nor is it required after build and run as `jar`
2. [Google Guava EventBus](https://github.com/google/guava/wiki/EventBusExplained) or [Spring-Reactor](https://www.javacodegeeks.com/2018/06/spring-reactor-tutorial.html) is an alternative to Spring's `ApplicationEventPublisher`
3. More advance usage of `ApplicationEventPublisher` includes usage of `@TransactionalEventListener` and `@Async`
4. Can also publish event asynchronously by using `ApplicationEventMulticaster`

# Known Issues

1. Whenever client reconnects, the socket events received will multiply. For example, after reconnection, instead of receiving 1x `onUserCreated` event, it will receive n times depending on how many times it has reconnected
2. `src/main/resources/META-INF/additional-spring-configuration-metadata.json` has to be generated via `Eclipse` manually to provide auto-completion

# Usage

1. Start server by `cd feathers-server && npm ci && npm run dev`
2. Start client by `cd spring-javafx-client && gradlew clean build bootRun`

# Resources

## Github

- [spring-security51-by-example-reactive](https://github.com/rwinch/spring-security51-by-example-reactive)
- [spring-security-samples-securemail](https://github.com/rwinch/spring-security-samples-securemail)

## Stackoverflow

- [does-adding-too-many-application-listeners-affect-performance](https://stackoverflow.com/questions/38662053/does-adding-too-many-application-listeners-affect-performance)
- [spring-security-programmatic-login-without-a-password](https://stackoverflow.com/questions/11313309/spring-security-programmatic-login-without-a-password)
- [spring-security-get-password-in-userdetailsservicemethod](https://stackoverflow.com/questions/53445809/spring-security-get-password-in-userdetailsservicemethod)
- [spring-security-jwt-validation-without-using-usernamepasswordauthenticationtoken](https://stackoverflow.com/questions/62348447/spring-security-jwt-validation-without-using-usernamepasswordauthenticationtoken)
- [springboot-gradle-plugin-not-working-with-eclipse](https://stackoverflow.com/questions/60140145/springboot-gradle-plugin-not-working-with-eclipse)
- [jackson-builder-pattern](https://stackoverflow.com/questions/4982340/jackson-builder-pattern)

## Others

- [javafx-getting-started](https://www.vojtechruzicka.com/javafx-getting-started/)
- [creating-a-spring-boot-javafx-application-with-fxweaver/](https://rgielen.net/posts/2019/creating-a-spring-boot-javafx-application-with-fxweaver/)
- [run-initialization-code-spring-boot](https://careydevelopment.us/2019/01/19/run-initialization-code-spring-boot/)
- [spring-boot-application-events-explained](https://reflectoring.io/spring-boot-application-events-explained/)
- [Spring Boot @ConfigurationProperties Tips & Tricks](https://www.youtube.com/watch?v=p0SaPppQcco)
- [publishing-events-asynchronously](http://learningviacode.blogspot.com/2012/08/publishing-events-asynchronously.html)
- [custom-events-and-generic-events-in-spring](https://jstobigdata.com/spring/custom-events-and-generic-events-in-spring/)
- [spring-event-event-driven](https://laptrinhx.com/spring-event-event-driven-590833238/)
- [inter-bean-notifications-using-spring-events](https://98elements.com/blog/inter-bean-notifications-using-spring-events/)
- [Jacksonized](https://projectlombok.org/features/experimental/Jacksonized)
- [flexible-immutability-with-jackson-and-lombok/](https://sharing.luminis.eu/blog/flexible-immutability-with-jackson-and-lombok/)