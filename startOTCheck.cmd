@echo off
title startCCS
@setlocal
if "%HOME%" == "" (set "HOME=%cd%")
java -jar %HOME%\target\Haierdemo-1.0.jar ot
pause
