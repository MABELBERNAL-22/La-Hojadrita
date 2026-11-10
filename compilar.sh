#!/bin/bash
# Compila el proyecto y arma hojaldrita-web.war
# Cambia esta ruta por la carpeta donde tienes instalado Tomcat 10
TOMCAT=/opt/tomcat10

rm -rf build && mkdir -p build/war/WEB-INF/classes
javac -encoding UTF-8 -cp "$TOMCAT/lib/servlet-api.jar" -d build/war/WEB-INF/classes $(find src/main/java -name "*.java")
cp -r src/main/webapp/* build/war/
(cd build/war && jar cf ../../hojaldrita-web.war .)
echo "Listo: se creo hojaldrita-web.war"
