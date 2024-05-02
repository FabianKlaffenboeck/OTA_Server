FROM eclipse-temurin:21
EXPOSE 8080
RUN mkdir "/app"
COPY ./build/install/OTA_Server /app/
CMD /app/bin/OTA_Server