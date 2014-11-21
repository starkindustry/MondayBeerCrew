MondayBeerCrew
==============

It's Monday...where's the beer?


Analyzer Pre-Configuration
=====================
The following provides details on pre-configuring the necessary file paths prior to running our project code.  
Both analyzers will be run on a single Java codebase via the run_analyzers.bat file. Once analyzer output has been 
generated, the project jar will run our Fuser and output two files: dependencyData.csv and orbitData.csv. Both 
files will be in the parent MondayBeerCrew folder. Finally, the script will then launch Google Chrome browser 
with --allow-file-access-from-files to display Visualizer.

This project will require the following software:
* SourceMonitor (Windows only)
* DependencyFinder (Windows and Unix)
* Google Chrome

Running our Project
-------------------
* 1. Create a config.txt file that holds all the variables needed to run the bat file. See below for details.
* 2. Create a SMCommand.xml file to configure SourceMonitor output. See below for details.
* 3. Run run_analyzers.bat
* 4. Close command prompt windows when output is finished


config.txt Variables
----------------------------------
* CODEBASE_PATH 	- path of codebase java files. Default: ..\MondayBeerCrew\CodeBases\(project folder)
* DF_OUTPUT 		- path of DependencyFinder XML output.
* SM_PATH			- path of SourceMonitor executable. 
* SM_CONFIG_PATH	- path of SMCommand.xml configuration file.
* PROJECT_JAR		- path of project Jar file. Must be in the parent MondayBeerCrew folder.
* JAR_FST_ARG		- path of SourceMonitor output file. Default: "Result XMLs/(csv file)"
* JAR_SND_ARG		- path of DependencyFinder output file. Default: "Result XMLs/(xml file)"
* VISUALIZER		- path of visualizer. Default: file:///../MondayBeerCrew/Visualization/Visualizer/OrbitingPlanets.html


SourceMonitor Pre-configuration
----------------------------------
* SourceMonitor is available at: http://www.campwoodsw.com/sourcemonitor.html
* Default directory: C:\SourceMonitor\SourceMonitor.exe
* Default Output file names: 
* Codebase 1 (Vntful):  ..\MondayBeerCrew\Result XMLs\VntfulCheckpoint.csv
* Codebase 2 (PetFinder): ..\MondayBeerCrew\Result XMLs\PetFinderCheckpoint.csv 
* Default SM_CONFIG_PATH:    	..\MondayBeerCrew\automation scripts\SMCommand.xml

***NOTE: Change the CSV value separator to semi-colon in order to avoid unwanted comma splitting
* File -> Options -> Export tab -> CSV Export Options -> Semi-colon

To configure SMCommand.xml:
* project_file - Input SourcMonitor *.smp file. For our purposes, this file can be deleted after script
* source_directory - Enter the directory of the codebases
* export_file - Enter the analyzer output filepath. The default is ../Result XMLs/
* export_options - Enter 3 to export to CSV


DependencyFinder Pre-configuration
----------------------------------
Please refer to manual for setup: http://depfind.sourceforge.net/Manual.html
* Default DependencyFinder directory: C:\DependencyFinder-1.2.1-beta4
* Default Output file names: 
* Codebase 1 (Vntful): ..\MondayBeerCrew\Result XMLs\vntfulResults.xml
* Codebase 2 (PetFinder): ..\MondayBeerCrew\Result XMLs\petFinderResults.xml

Visualizer:
------------

**Please view in Chrome with "--allow-file-access-from-files" argument**

For the visualizer, we have mock data to be fed into the html document and can be visually inspected. The data can be located in the Visualization/oldstuff/mock_data folder:
[mockdata](https://github.com/starkindustry/MondayBeerCrew/tree/master/Visualization/oldstuff/mock_data)

Simple move the dependencyData.csv and orbitData.csv into the root directory (/MondayBeerCrew) and run [OrbitingPlanets.html](https://github.com/starkindustry/MondayBeerCrew/blob/master/Visualization/Visualizer/OrbitingPlanets.html)

Our data contains 3 star systems and 10 dependency lines.  All should be visible once the document is loaded.  For more detail on the information, inspect
the actual csv files.

*Core behaviour includes:*

* tooltip with information containing "Class", "Package", "Lines of Code", "Complexity" when orbits are hovered
* dependency lines drawn when orbits hovered
* all dependency lines can be toggled on and off with the toggle button at the top left


(integration test, manual)
    Input: A small, dummy codebase for which you know roughly what the visualization should look like (like maybe even a single source file).
    Output: A visualization.
    Test: Look at the visualization and confirm that it looks/behaves as expected.
    
Testing: 
==============

For the analysis portion of the project, we have unit tests, including tests for each of the parsers, 
tests for the Fuser and tests for the output format required for our visualizer.  
