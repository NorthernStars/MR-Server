Mixed-Reality Server
=========
Mixed-Reality Server is a set of projects for Mixed-Reality (MR) core systems.
Including all modules necessary to set up a complete MR enviroment.

Version
----

1.0.1-beta

Dependencies
-----------

MR-Server uses a number of libraries that are already included inside the repository.

* log4j-api-2.0-beta9.jar
* dyn4j-javadocs-v3.1.7.zip
* dyn4j-v3.1.7.jar
* log4j-core-2.0-beta9.jar
* commons-cli-1.2.jar
* jcip-annotations.jar

You need ant installed to run build script.

### Linux (Debian / Ubuntu / ...)
```sh
sudo apt-get install ant
```

### Windows
https://ant.apache.org/

Installation
--------------

* Import project from github
* Create libraries directory
* Run ant build script from everythingbuild directory

```sh
git clone [git-repo-url] MR-Server
cd MR-Server
cd everythingbuild
mkdir libraries
ant -f buildserver.xml
```


License
----
CC BY-NC-SA 3.0
For further details see LICENCE file.
