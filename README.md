# REST Assured API Automation

A hands-on learning project for API test automation in Java with **REST Assured** and **TestNG**. It covers everything from basic HTTP verbs to POJO (de)serialization, JSON schema validation, filters, authentication and file upload/download, mostly against the Postman API, Postman Echo and Postman mock servers.

## Tech stack

| Tool | Version |
|---|---|
| Java (JDK) | 17+ |
| Maven | 3.6+ |
| REST Assured | 6.3.0 |
| REST Assured JSON Schema Validator | 6.3.0 |
| TestNG | 7.12.0 |
| Jackson Databind | 2.23.0 |
| JSONAssert | 1.5.3 |
| dotenv-java | 3.2.0 |

> REST Assured 6.x requires **Java 17 or newer**. The build compiles with `maven.compiler.release=17`.

## Project structure

```
src
├── main
│   ├── java/com/rest/pojo
│   │   ├── collection/   # POJOs for Postman collections (request/response, folders, headers, body, URL…)
│   │   ├── workspace/    # POJOs for Postman workspaces
│   │   └── simple/       # Minimal POJO example
│   └── resources/
│       └── CreateWorkspacePayload.json
└── test
    ├── java/com/rest/    # Test classes (one topic per class)
    │   └── google/oauth2/GmailApi.java
    └── resources/
        ├── EchoGet.json  # JSON schema used for schema validation
        └── JsonSample.json
```

## What's covered

| Topic | Classes |
|---|---|
| CRUD requests | `AutomateGet`, `AutomatePost`, `AutomatePut`, `AutomateDelete` |
| Headers, query/path/form params | `AutomateHeaders`, `RequestParameters` |
| Request & response specifications | `RequestSpecificationExample`, `RequestSpecificationExample2`, `ResponseSpecificationExample` |
| Static vs. non-static imports, method chaining | `StaticImports`, `NonStaticImports`, `MethodChaining` |
| Payloads as JSON objects/arrays/complex JSON | `RequestPayloadAsJsonArray`, `RequestPayloadComplexJson`, `RequestPayloadComplexJson1` |
| Jackson `ObjectNode` / `ArrayNode` | `JacksonAPI_JSONObject`, `JacksonAPI_JSONArray` |
| POJO serialization / deserialization | `SimplePojoTest`, `WorkspacePojoTest`, `ComplexPojoTest` |
| JSON schema validation | `JsonSchemaValidation` |
| Extracting values from responses | `FetchKeyValue` |
| Logging and filters | `Logging`, `Filters` |
| Form auth with CSRF, sessions, cookies | `FormAuthentication` |
| Base64 encoding | `Base64Encoding` |
| File upload / download | `UploadFile`, `DownloadFile` |
| OAuth 2.0 (Gmail API) | `google/oauth2/GmailApi` |

## Prerequisites

1. JDK 17+ on your `PATH` (`java -version`).
2. Maven (`mvn -v`).
3. Credentials for the APIs you want to hit:
   - **Postman API key** — most tests send an `X-Api-Key` header to `https://api.postman.com`. Generate one at *Postman → Settings → API keys*.
   - **Google OAuth 2.0 access token** — only for `GmailApi`.
   - **Local app on `https://localhost:8081`** — only for `FormAuthentication`.

## Running the tests

```bash
# Compile everything
mvn clean test-compile

# Run a single test class
mvn test -Dtest=AutomateGet

# Run a single test method
mvn test -Dtest=AutomateGet#validate_get_status_code

# Run all tests
mvn test
```

You can also run any class directly from IntelliJ IDEA or VS Code via the TestNG runner.

> Many tests call live third-party services and mock servers, so failures can come from expired keys, deleted mock servers or changed responses rather than from the code.

## Checking for dependency updates

The `versions-maven-plugin` is configured in the build:

```bash
mvn versions:display-dependency-updates
mvn versions:use-latest-releases   # updates pom.xml (backup saved as pom.xml.versionsBackup)
```

## Notes on the REST Assured 4.x → 6.x upgrade

- **Java 17 baseline** — the compiler level was raised from 8 to 17.
- **CSRF in form auth** — `FormAuthConfig.withAutoDetectionOfCsrf()` was removed. CSRF is now configured via `CsrfConfig`:

  ```java
  given()
      .auth().form("user", "pass", new FormAuthConfig("/signin", "txtUsername", "txtPassword"))
      .config(RestAssured.config().csrfConfig(csrfConfig().with().csrfTokenPath("/login")))
  ```

  By default REST Assured looks for an input field named `_csrf`; use `csrfInputFieldName(...)` if your app uses a different name.
