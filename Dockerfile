FROM eclipse-temurin:21.0.3_9-jre-jammy
EXPOSE 8080
RUN mkdir "/app"
COPY ./build/install/OTA_Server /app/
CMD /app/bin/OTA_Server