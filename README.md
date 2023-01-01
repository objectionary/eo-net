<img src="https://www.yegor256.com/images/books/elegant-objects/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/kerelape/eo-sockets/blob/main/LICENSE.txt)
[![codecov](https://github.com/objectionary/eo-net/actions/workflows/codecov.yml/badge.svg)](https://github.com/objectionary/eo-net/actions/workflows/codecov.yml)

[EOLANG](https://www.eolang.org) socket object.

This is how to connect to a TCP server and send 88:
```
+alias org.eolang.net.socket

[] > main
  seq > @
    as-output. > output
      connect.
        socket
          "localhost"
          8080
    output.write 88
    output.flush
```

Server:
```
# Create TCP server and echo

+alias org.eolang.net.socket

[] > main
  seq > @
    write.
      as-output. > output
        accept. > conn
          listen.
            socket
              "localhost"
              8080
      conn.as-input.read
    output.flush
```

## How to Contribute

Fork repository, make changes, send us a pull request.
We will review your changes and apply them to the `main` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install -Pqulice
```

You will need Maven 3.3+ and Java 8+.
