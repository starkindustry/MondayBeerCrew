for /f "delims=" %%x in (config.txt) do (set "%%x")

set JAVA_HOME=%JAVA_HOME%
set PATH=%DEPFINDER_PATH%;%PATH%

start DependencyExtractor -class-filter -xml -out %~dp0\%DF_OUTPUT% -filter-excludes /^java\./ %~dp0\%CODEBASE_PATH%
start %SM_PATH% /C %~dp0\%SM_CONFIG_PATH%
sleep 2
start javaw -jar %PROJECT_JAR% %JAR_FST_ARG% %JAR_SND_ARG%
echo %JAR_FST_ARG%
echo %JAR_SND_ARG%
start "Chrome" chrome --new-window --allow-file-access-from-files %~dp0\%VISUALIZER%



