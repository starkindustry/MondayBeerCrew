start DependencyExtractor -class-filter -xml -out C:\410dataOutput\vntfulResults.xml -filter-excludes /^java\./ C:\Users\Allan\git\MondayBeerCrew\CodeBases\Vntful
start DependencyExtractor -class-filter -xml -out C:\410dataOutput\petFinderResults.xml -filter-excludes /^java\./ C:\Users\Allan\git\MondayBeerCrew\CodeBases\PetFinder
start C:\SourceMonitor\SourceMonitor.exe /C "C:\410dataOutput\SMCommand.xml"
