@echo off
setlocal
cd /d "%~dp0"

echo Building the verified Create Propulsion release...
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0scripts\build-release.ps1"
set "RESULT=%ERRORLEVEL%"

if not "%RESULT%"=="0" (
    echo.
    echo Build failed. The existing release and dist copies were not replaced.
) else (
    echo.
    echo Verified release package completed successfully.
)

echo.
pause
exit /b %RESULT%
