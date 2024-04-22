FROM eclipse-temurin:8u362-b09-jre-jammy
EXPOSE 8080
RUN mkdir "/app"
COPY ./build/install/OTA_Server /app/
CMD /app/bin/OTA_Server