
# Evolution generator

## What is it about?
This application was created as a project for a course on *OOP*
at the **AGH UST**. The goal is to create a parametrized simulation 
of a primitive ecosystem consisting of plants and animals placed on
a two dimentional map. 

## Features
- ability to change simulation parameters, with csv parsing
- possibility to launch multiple parallel simulations (multithreaded), all of which
can be stopped and inspected at any time
- optional exporting of daily statistics of simulation to csv file
- a simple interactive GUI for observing state of the map and animals (on click) 
- multiple variants of movement progression and map behavior

## Technology
We used the gradle build system for dependency management and running the application.
GUI was created with *JavaFX*, for csv parsing we used the *opencsv* library and the
prefered way of data processing was with the *Java Stream API*.
Application is compatible with Java version 17 or newer.
