#####

# Etapa 1: Construcción
FROM  maven:3.9.9-eclipse-temurin-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto Maven al contenedor
COPY pom.xml ./
COPY src ./src
COPY files ./files
# Compila la aplicación y genera el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido en la etapa 1
COPY --from=build /app/target/Server-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY --from=build /app/files/ /app/files

# Expone el puerto de la aplicación
EXPOSE 12345

# Configura el comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
