# Mixed-Reality Server
Mixed-Reality Server is a set of projects for Mixed-Reality (MR) core systems.
Including all modules necessary to set up a complete MR enviroment.

## Version

1.0.1-beta

## Dependencies

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

## Installation

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

## Build runnable
TO build a runnable jar file execute build-runnable.xml from directory mrserver with ant

     ant -f build-runnable.xml

It creates mrserver-runnable.jar inside everythingbuild directory.

## Command-line options

    -bc,-botcontrol <ip-address:port>               Sets the botcontrol ip-address and port
   -bp,-botports <port [:port...]>                 Sets the ports the bots can connect to
   -bp_ao,-auto_open_botports                      Automatically opens the ports the bots can connect to
   -cf,-configfile <path/configfile>               The name and path of the serverconfigfile
   -gp,-graphicsport <port>                        Sets the port the graphics module can connect to
   -h,-help                                        Displays this help
   -sc,-scenarioclass <class>                      Sets the scenarioclass to use
   -sc_al,-auto_load_scenario                      Automatically loads the scenario if possible to
   -scf,-scenarioconfigfile <filename>             The configfile the scenario should use
   -scmd,-scenariocmdline <"commandlineoptions">   Commandline options for the scenario
   -sl,-scenariolibrary <libraryname>              The library with the scenario
   -sn,-servername <servername>                    Sets the name of the server
   -v,-vision <ip-address:port>                    Sets the vision ip-address and port


License
----
CC BY-NC-SA 3.0
For further details see LICENCE file.
