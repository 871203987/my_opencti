@echo off
echo Starting OpenCTI Frontend...
echo.
echo Connecting to Java GraphQL Service at http://localhost:8082
echo.

cd /d d:\project\open_cti\opencti\opencti-platform\opencti-front

set BACK_END_URL=http://localhost:8082
set FRONT_END_PORT=3000

echo Installing dependencies if needed...
call npm install --legacy-peer-deps

echo.
echo Starting development server...
echo Frontend will be available at: http://localhost:3000
echo.
call npm run dev
