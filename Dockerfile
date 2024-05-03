FROM eclipse-temurin:21-alpine
RUN mkdir "/app"
COPY ./build/install/OTA_Server /app/
CMD /app/bin/OTA_Server