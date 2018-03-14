@echo off
title test_pub
@setlocal
if "%HOME%" == "" (set "HOME=%cd%")
java -jar %HOME%\target\Haierdemo-1.0.jar pub
pause