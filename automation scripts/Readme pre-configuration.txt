Overview
----------------------------------
This document provides details on pre-configuring the necessary file paths prior to running our project code.  
Both analyzers will be run on a single Java codebase via the run_analyzers.bat file. Once analyzer output has been 
generated, the project jar will run our Fuser and output two files: dependencyData.csv and orbitData.csv. Both 
files will be in the parent MondayBeerCrew folder. Finally, the script will then launch Google Chrome browser 
with --allow-file-access-from-files to display Visualizer.

This project will require the following software:
- SourceMonitor (Windows only)
- DependencyFinder (Windows and Unix)
- Google Chrome


Running our project: 
----------------------------------
1. Create a config.txt file that holds all the variables needed to run the bat file. See below for details.
2. Create a SMCommand.xml file to configure SourceMonitor output. See below for details.
3. Run run_analyzers.bat
4. Close command prompt windows when output is finished


config.txt Variables
----------------------------------
CODEBASE_PATH 	- path of codebase java files. Default: ..\MondayBeerCrew\CodeBases\(project folder)
DF_OUTPUT 		- path of DependencyFinder XML output.
SM_PATH			- path of SourceMonitor executable. 
SM_CONFIG_PATH	- path of SMCommand.xml configuration file.
PROJECT_JAR		- path of project Jar file. Must be in the parent MondayBeerCrew folder.
JAR_FST_ARG		- path of SourceMonitor output file. Default: "Result XMLs/(csv file)"
JAR_SND_ARG		- path of DependencyFinder output file. Default: "Result XMLs/(xml file)"
VISUALIZER		- path of visualizer. Default: file:///../MondayBeerCrew/Visualization/Visualizer/OrbitingPlanets.html


SourceMonitor Pre-configuration
----------------------------------
SourceMonitor is available at: http://www.campwoodsw.com/sourcemonitor.html
Default directory: C:\SourceMonitor\SourceMonitor.exe
Default Output file names: 	..\MondayBeerCrew\Result XMLs\VntfulCheckpoint.csv
			   				..\MondayBeerCrew\Result XMLs\PetFinderCheckpoint.csv 
Default SM_CONFIG_PATH:    	..\MondayBeerCrew\automation scripts\SMCommand.xml

***NOTE: Change the CSV value separator to semi-colon in order to avoid unwanted comma splitting
         - File -> Options -> Export tab -> CSV Export Options -> Semi-colon

To configure SMCommand.xml:
- <project_file>     -> Input SourcMonitor *.smp file. For our purposes, this file can be deleted after script
- <source_directory> -> Enter the directory of the codebases
- <export_file>	     -> Enter the analyzer output filepath. The default is ../Result XMLs/
- <export_options>   -> Enter 3 to export to CSV


DependencyFinder Pre-configuration
----------------------------------
Please refer to manual for setup: http://depfind.sourceforge.net/Manual.html
Default DependencyFinder directory: C:\DependencyFinder-1.2.1-beta4
Default Output file names: ..\MondayBeerCrew\Result XMLs\vntfulResults.xml
			   ..\MondayBeerCrew\Result XMLs\petFinderResults.xml

