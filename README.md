# Mixed-Reality Server
Mixed-Reality Server is a set of projects for Mixed-Reality (MR) core systems.
Including all modules necessary to set up a complete MR enviroment.

## Version

1.0.1-beta

## Dependencies

MR-Server uses a number of libraries that will be downloaded automaticaly during build process.
You need gradle installed to run build script. You can use Eclipse and the Eclipse Gradle Plugin to build the project.


## Installation

* Clone project from github
* Import projects into eclipse (if prefered) using gradle import assist

## Build runnable
* Run gradle buildBinarys task

Run from console

     gradle buildBinarys

it creates a dist directory that includes all need files. Use start scripts mrserver to start default server config.

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
