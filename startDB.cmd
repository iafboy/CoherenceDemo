@echo off
title startPDB
@setlocal
if "%HOME%" == "" (set "HOME=%cd%")
java -jar %HOME%\target\Haierdemo-1.0.jar db
pause
