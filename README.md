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


## Maintainers

* Stefan Haun ([@penguineer](https://github.com/penguineer))


## Contributing

This project is end-of-life and has been replaced by [cleanURI](https://github.com/penguineer/cleanURI).

Please check that project and the linked sub-projects if you want to use or contribute to this tool!

## License

[The Apache Software License, Version 2.0](LICENSE.txt) © 2013-2022 Stefan Haun and contributors
