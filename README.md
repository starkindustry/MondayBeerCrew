MondayBeerCrew
==============

It's Monday...where's the beer?

Testing: 
==============

For the analysis portion of the project, we have unit tests, including tests for each of the parsers, 
tests for the Fuser and tests for the output format required for our visualizer.  

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