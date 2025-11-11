@echo off
REM Simple run script - compiles and runs MEMS

echo Compiling MEMS...
javac -d bin domain/*.java factory/*.java command/*.java registry/HistoryManager.java registry/FactoryRegistry.java MEMS.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful! Running MEMS...
    echo.
    java -cp bin MEMS
) else (
    echo.
    echo Compilation failed!
    pause
)
