Both analyzers will be run on our codebases via the run_analyzers.bat file. 

1. Create C:\410dataOutput folder (analyzer output files)
2. Place SMCommand.xml into C:\410dataOutput folder
3. Configure SMCommand.xml as below
4. Configure codebase directories in run_analyzers.bat file for both DependencyFinder commands
5. Run run_analyzers.bat
6. Close command prompt window when output is finished

SourceMonitor Pre-configuration
-------------------------------
Default SourceMonitor directory: C:\SourceMonitor\SourceMonitor.exe
Output files: VntfulCheckpoint.csv, PetFinderCheckpoint.csv 

To configure SMCommand.xml:
- <project_file>     -> Input SourcMonitor *.smp file. The default is C:\410dataOutput 
- <source_directory> -> Enter the directory of the codebases
- <export_file>	     -> Enter the analyzer output filepath. The default is C:\410dataOutput  
- <export_options>   -> Enter 3 to export to CSV

DependencyFinder Pre-configuration
----------------------------------
Default DependencyFinder directory: C:\DependencyFinder-1.2.1-beta4
Output files: vntfulResults.XML, petFinderResults.xml

