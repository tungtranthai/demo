# demo-service

### Service name
`demo`
The aws service name is decided based on Traveloka's [General Service Naming Convention](https://29022131.atlassian.net/wiki/spaces/SI/pages/188466942/General+Service+Naming+Convention).


### Description
This is a RestFul service running on Spring-Boot and Kotlin to serve the endpoints for Finserve's Customer Identity management.

---

### Set up & Installation (Mac)
#### Installing dependencies:

- Homebrew (package manager)
  ```shell script
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```
- Gradle (application code dependency install)
  ```shell script
  brew install gradle
  ```
- Postgresql (database engine)
  ```shell script
  brew install postgresql
  ```
- Docker Compose (containerization) download [here](https://hub.docker.com/editions/community/docker-ce-desktop-mac/)
- Sqitch
  ```shell script
  brew install sqitch
  ```
  Set up your sqitch (Telling sqitch where your psql command is)
  ```shell script
  sqitch config --user engine.pg.client $(which psql)
  ```

---

### Builds
#### Local build (building the code only)
- Make sure that you have obtain your AWS STS (SAML) via `gsts` or `aws-google-auth`.
- Run the following command
  ```shell script
  ./scripts/build.sh -p <your_saml_role_alias>
  ```

#### Local build (with creating the container image locally)
- Make sure that you have obtain your AWS STS (SAML) via `gsts` or `aws-google-auth`.
- Run the following command
  ```shell script
  ./scripts/build.sh -p <your_saml_role_alias> -i
  ```

---

### Deployments
#### Initialize db on your localhost (Docker)
```shell script
docker-compose up -d db
```

### Running the application service on your localhost (Docker)
> :bangbang: make sure that the database container has been initialised properly!
- Check whether `db` container has run properly and finished its setup.
  ```shell script
  docker-compose logs --tail=100 db
  ```
> :bangbang: make sure that you have built the app container image by following the instruction in the previous section!
- Go to `docker-compose.yml` file, change the `image` value for `app` container with the appropriate value.
- Run `app` container
  ```shell script
  docker-compose up -d app
  ```
- Test it by hitting the `/healthcheck` endpoint, e.g.
  ```shell script
  curl localhost:8082/healthcheck
  ```

#### Deploy db schema changes
> Note: This repo uses BEI's [sqitch plugin](https://github.com/traveloka/bei-gradle-sql-plugin/tree/master/sqitch-plugin)
```shell script
./scripts/assume_role.sh -p saml && ./scripts/sqitch.sh deploy
```

---
## Essentials
#### Add any database change using Sqitch
```shell script
sqitch add <sql file name> -n '<Describe the purpose of the file>'
```

#### Generate JOOQ classes for any new schema changes
```shell script
./gradlew generateJooq
```

#### Enable git hooks for pre commit and pre push actions:
```
admin:~/repo/github/fsp-cis-service bash .githooks/init.sh
WARNING: this will override your existing githooks. Continue? [Y/N] Y
Continuing
Updated .git/hooks/pre-push
Updated .git/hooks/prepare-commit-msg
```
More info [here](https://29022131.atlassian.net/wiki/spaces/plutus/pages/781354010/Plutus+-+Set+up+git+hooks).


#### Run tests
- Unit tests:
  ```shell script
  ./scripts/assume_role.sh -p saml && ./gradlew smallTest
  ```
- Integration tests:
  ```shell script
  ./scripts/assume_role.sh -p saml && ./gradlew integrationTest`
  ```
- All tests:
  ```shell script
  ./scripts/assume_role.sh -p saml && ./gradlew test
  ```

#### Using localstack
- Add the following config text inside `~/.aws/credentials`
    ```
  [default]
  aws_access_key_id=ASIA3Z32OQKFXCCBMZXF
  aws_secret_access_key=VJaphBV66y2wcsT/f+1R/CJ5+Xb8C2ngefUPcRKJ
    ```
- Execute following commands to create your localstack (sns, sqs, s3, parameter store)
    ```bash
    docker-compose -f scripts/cis-localstack/docker-compose.yml up
    ```

- Execute following command to bring down the entire your localstack

    ```bash
    docker-compose -f scripts/cis-localstack/docker-compose.yml down

## Create Mocked Application Data
To create a mocked application data, you could use this data to call `POST` `http://localhost:8082/fsp-cis/v1/identity/`

1. Put [this JSON data](docs/mocked-payload/CreateApplication.json) as raw json data

### Connecting directly to the staging environment
Basically you need to assume-role when connecting to AWS environment from local machine. You can use `aws-cli` or follow the direction below.
1. Download and install `awsume` [here](https://awsu.me/general/quickstart.html).
2. Log in to your AWS account in terminal via `gsts` or any other tool.
3. Add this profile in `~/.aws/config` if not exists.
    ```
    [profile <profile_name>]
    role_arn = arn:aws:iam::065772069737:role/Developer
    source_profile = saml
    role_session_name = <your_traveloka_email_address>
    region = ap-southeast-1
    ```
4. Assume-role using `awsume`. It will write access and secret key into `~/.aws/credentials`.
    ```shell script
    awsume <profile_name_in_config> -o <profile_name_in_credentials>
    ```
5. Add an environment variable on your spring runner in your IDE.
    ```
    AWS_PROFILE=<profile_name_in_credentials>
    ```
6. Run the application.

### Run App
#### From IDE
1. Add `spring.profiles.active=dev` in your run configuration's environment variables
   ![Run Configuration](docs/Application.png)
2. Run class `com.traveloka.fsp.cis.Application`

3. Use Swagger UI to try the app [http://localhost:8082/fsp-cis/swagger-ui/index.html](http://localhost:8082/fsp-cis/swagger-ui/index.html)
   ![Swagger-UI](docs/swagger-ui.png)
---

### Glossary
- FSID - Finserve Identity, unique identifier for any customer who is registered to the Finserve ecosystem.
