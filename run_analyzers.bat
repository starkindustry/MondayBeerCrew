for /f "delims=" %%x in (config.txt) do (set "%%x")

start DependencyExtractor -class-filter -xml -out %DF_OUTPUT% -filter-excludes /^java\./ %CODEBASE_PATH%
start %SM_PATH% /C %SM_CONFIG_PATH%
sleep 2
start javaw -jar %PROJECT_JAR% %JAR_FST_ARG% %JAR_SND_ARG%
echo %JAR_FST_ARG%
echo %JAR_SND_ARG%
start "Chrome" chrome --new-window --allow-file-access-from-files %VISUALIZER%



