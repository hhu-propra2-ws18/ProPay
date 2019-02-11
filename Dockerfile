FROM gradle:jdk11-slim as BUILD
WORKDIR /code
COPY --chown=gradle:gradle . .
RUN gradle build

FROM openjdk:11-jre-slim
COPY --from=BUILD /code/build/libs/*.jar propay.jar
EXPOSE 8888
CMD ["java","-jar","propay.jar"]
