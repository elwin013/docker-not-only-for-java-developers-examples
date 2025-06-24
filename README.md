# Docker (not only) for Java Developers

Examples for presentation 'Docker (not only) for Java Developer' ("Docker (nie tylko) dla Java developerów").

## Alpine figlet example

Figlet is a program that generates text banners.

Dockerfile for `alpine-figlet`:

```dockerfile
FROM alpine

RUN apk add figlet --no-cache

ENTRYPOINT ["figlet"]
CMD ["Hello World!"]
```

To build save into `Dockerfile` and  run `docker build . -t alpine figlet`.

* `FROM` – starts from alpine image (with latest) tag
* `RUN` – installs figlet
* `ENTRYPOINT` – set default executable to figlet.
* `CMD` – set default parameters passed to figlet to Hello World

Running `docker run alpine-figlet` shows default "Hello World!" message. Different params can be passed
and will replace the `CMD` - e.g. `docker run alpine-figlet I like trains!` will print "I like trains".

We can still replace the entrypoint and run shell: `docker run -it --entrypoint "/bin/sh" alpine-figlet`.

## Container aware Java example

To build:
`docker build . -t openjdk-containeraware`

To run with CPU/memory limits:

`docker run -it --rm --memory 300m --memory-swap 300m --cpu-period 100000 --cpu-quota 200000 --entrypoint /bin/bash openjdk-containeraware`

Inside run built jar with:

`java ShowMxBeanCpuAndMemoryInfo`

We can combine it with `-XshowSettings:system` or set explicit number of processors: `-XX:ActiveProcessorCount=4`.

You can also use available makefile.

## Todos app

Simple kind-of todos app that allows to add and remove entries (todos):
* after adding the todo has `done = false`.
* after marking as done the todo has `done = true`.
* todo with `done = true` is in done section and can be deleted.

By default app is using port `7070` and can be available at `http://localhost:7070/`.

Used libraries:
* Javalin - https://javalin.io/
* Jte - https://jte.gg/
* MongoDB sync driver - https://www.mongodb.com/docs/languages/java/
* Bulma CSS - https://bulma.io/
* JUnit - https://junit.org/
* TestContainers - https://testcontainers.com/


## Docker examples

All dockerfiles and docker compose files are placed in `src/main/docker`.

Tested with both Oracle OpenJDK 21 images and Temurin one.

### #1 Simplest docker image with app (shaded) - `Dockerfile-outsidejar`

Simplest image  - requires built uber jar in target.

To build:
1. `./mvnw package -DskipTests`
2. `docker build -f src/main/docker/Dockerfile-outsidejar -t todoapp-outsidejar .`

To run:
1. `docker run -p 7070:7070 todoapp-outsidejar:latest -it`


### #2 Build app in the docker image with app (shaded, naive way) - `Dockerfile-insidejar`

Solves "works on my machine" problem - app is built in the container.

Requires both JDK and JRE - to build and run. There is no cache.

To build:
1. `docker build -f src/main/docker/Dockerfile-insidejar -t todoapp-naive .`

To run:
1. `docker run -p 7070:7070 todoapp-naive:latest -it`

### #3 Build app in the docker image with app (shaded, multi stage) - `Dockerfile-shaded`

Improved version of #2 - uses multiple stages:
* download and cache dependencies (uses JDK image) in `dependencies` stage.
* build and package app (uses JDK image) in `package` stage.
* run app (uses JRE image) in `final` stage.

As a result the final image contains only the app (shaded, uber jar).

Uber jar is created using `maven-shade-plugin` - classes from all dependencies
are put in the final jar.

To build:
1. `docker build -f src/main/docker/Dockerfile-shaded -t todoapp-multistage1 .`

To run:
1. `docker run -p 7070:7070 todoapp-multistage1:latest -it`

### #4 Build app in the docker image with app (not shaded, multi stage) - `Dockerfile-dependency`

Improved version of #3 - stages are the same (`dependencies`, `package` and `final`), but
image used is `alpine` (instead of Ubuntu one).

Uses `maven-jar-plugin` (configuration in `pom.xml:92-105`):
* in `dependencies` stage to copy dependencies to `dependency` directory 
  in repository layout.
* in `package` stage to build jar with manifest file containing main class and
  classpath (for `dependency` directory).

In `final` stage dependency from `dependencies` are copied.

As a result the final image contains only the app (shaded, uber jar).

To build:
1. `docker build -f src/main/docker/Dockerfile-dependency -t todoapp-multistage2 .`

To run:
1. `docker run -it --rm -p 7070:7070 todoapp-multistage2:latest`

To debug:
1. `docker run -it --rm -p 7070:7070 -p 5005:5005 -e JVM_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" todoapp-multistage2:latest`
2. Run remote debug on port 5005.

## Docker Compose examples

###  #1 Running dependencies `docker-compose.deps.yml`

Running only dependencies:
* MongoDB - exposed at 27017, with data stored in host (`src/main/docker/mongo-data`)
* Mongo Express - simple GUI Mongo, exposed at 8081.

###  #2 Running app (memory DAO) `docker-compose.memory.yml`

Running the Todos app (memory version) only - available on 7070 port.


###  #3 Running app (build from Dockerfile) `docker-compose.dockerfile.yml`

Running the Todos app (memory version) only - built from the Dockerfile. 
App is available on port 7070.

###  #4 Running app (Mongo DAO) `docker-compose.mongo.yml`

Running the Todos app (mongo version) and Mongo + Mongo Express.

Data is stored within Mongo container only.