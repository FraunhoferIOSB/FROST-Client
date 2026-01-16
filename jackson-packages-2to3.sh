#!/bin/bash

PROJECT_ROOT="."  # Pfad zu deinem Projekt

# Mapping: alte Packages → neue Packages
declare -A PACKAGES=(
#    ["com.fasterxml.jackson.core"]="tools.jackson.core"
#    ["com.fasterxml.jackson.databind"]="tools.jackson.databind"
    ["tools.jackson.core.JsonProcessingException"]="tools.jackson.core.JacksonException"
)

# Iteriere über alle Dateien
find "$PROJECT_ROOT" -type f -name "*.java" | while read FILE; do
    for OLD in "${!PACKAGES[@]}"; do
        NEW=${PACKAGES[$OLD]}
        # sed In-Place ersetzen
        sed -i "s|$OLD|$NEW|g" "$FILE"
    done
done

echo "Alle Package-Namen wurden ersetzt"
