@echo off
REM Compila el proyecto y arma hojaldrita-web.war
REM Cambia esta ruta por la carpeta donde tienes instalado Tomcat 10
set TOMCAT=C:\apache-tomcat-10.1.30

if exist build rmdir /s /q build
mkdir build\war\WEB-INF\classes
dir /s /b src\main\java\*.java > fuentes.txt
javac -encoding UTF-8 -cp "%TOMCAT%\lib\servlet-api.jar" -d build\war\WEB-INF\classes @fuentes.txt
xcopy /e /i /y src\main\webapp build\war >nul
cd build\war
jar cf ..\..\hojaldrita-web.war .
cd ..\..
del fuentes.txt
echo Listo: se creo hojaldrita-web.war
