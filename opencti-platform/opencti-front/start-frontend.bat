@echo off
cd /d d:\project\open_cti\opencti\opencti-platform\opencti-front

set BACK_END_URL=http://localhost:8082
set FRONT_END_PORT=3000

echo Starting OpenCTI Frontend...
echo Backend URL: %BACK_END_URL%
echo Frontend Port: %FRONT_END_PORT%
echo.

node builder/dev/dev.js
