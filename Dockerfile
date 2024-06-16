FROM eclipse-temurin:21-alpine

RUN mkdir "/app"
RUN mkdir "/files"

COPY ./build/install/OTA_Server /app/
CMD /app/bin/OTA_Server