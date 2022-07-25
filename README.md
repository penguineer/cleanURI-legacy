# cleanURI - legacy

> Clean URI generation toolkit. (legacy code)

Trim a URI to a "canonical" (or minimal) state so that it can serve as an easy pointer to a product or entity without unwanted meta-information (such as trackers).

Extended decorators also add meta information form the referred page and allow specific representations, e.g. Markdown or [DokuWiki](https://www.dokuwiki.org/).

**This is the legacy implementation of the URI cleaner!**

Please refer to the [cleanURI main repository](https://github.com/penguineer/cleanURI) for the current implementation.


## Deployment

### with Docker

Run with Docker:
```bash
docker run --rm -it -p 8080:8080 mrtux/clean_uri:latest
```

The container exposes port 8080 for access to the HTTP endpoint.

### Development
Version numbers are determined with [jgitver](https://jgitver.github.io/).
Please check your [IDE settings](https://jgitver.github.io/#_ides_usage) to avoid problems, as there are still some unresolved issues.
If you encounter a project version `0` there is an issue with the jgitver generator.

## Build

The build is split into two stages:
1. Packaging with [Maven](https://maven.apache.org/)
2. Building the Docker container

This means that the [Dockerfile](Dockerfile) expects one (and only one) JAR file in the target directory.
Build as follows:

```bash
cd WebApp
mvn --batch-mode --update-snapshots clean package
cd ..
docker build .
```

Why not do everything with maven and [JIB](https://github.com/GoogleContainerTools/jib)?
So far I have not been able to integrate JIB with the mechanism that determined which tags should be build (e.g. only
build *latest* when on main branch). After 5h of trying I settled with this solution:
* [Maven](https://maven.apache.org/) is sufficiently reliable to create reproducible builds, and we can make use of the build cache.
* The [Dockerfile](Dockerfile) allows for the usual integration into image build and push.

The whole process is coded in the [docker-publish workflow](.github/workflows/docker-publish.yml) and only needs to be
executed manually for local builds.

## Maintainers

* Stefan Haun ([@penguineer](https://github.com/penguineer))


## Contributing

PRs are welcome!

If possible, please stick to the following guidelines:

* Keep PRs reasonably small and their scope limited to a feature or module within the code.
* If a large change is planned, it is best to open a feature request issue first, then link subsequent PRs to this issue, so that the PRs move the code towards the intended feature.


## License

[The Apache Software License, Version 2.0](LICENSE.txt) © 2013-2022 Stefan Haun and contributors
