#!/bin/bash

# Buscar un único JAR que termine en -fat.jar
JAR_PATH=$(find . -maxdepth 1 -name '*-fat.jar' | head -n 1)

# Buscar un único archivo .cfg dentro de deploy/
CFG_PATH=$(find deploy -maxdepth 1 -name '*.cfg' | head -n 1)

# Verificar existencia del JAR
if [ -z "$JAR_PATH" ]; then
    echo "❌ No se encontró ningún archivo JAR con sufijo -fat.jar"
    exit 1
fi

# Verificar existencia del CFG
if [ -z "$CFG_PATH" ]; then
    echo "❌ No se encontró ningún archivo .cfg en el directorio deploy/"
    exit 1
fi

# Ejecutar
echo "🚀 Ejecutando $JAR_PATH con configuración $CFG_PATH..."
java -jar "$JAR_PATH" "$CFG_PATH"
