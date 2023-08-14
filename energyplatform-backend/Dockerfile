FROM maven:3.8.3-openjdk-17 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/energyplatform-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/energyplatform-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:17.0.1

ENV TZ=UTC
ENV DB_IP=ec2-34-249-247-7.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=tmfvdezbhtlwql
ENV DB_PASSWORD=0fef0044e34141c83f0b90df5a2a731adada8187abdd3e1f58dc702f461bddaf
ENV DB_DBNAME=dd0tdj8arhmo4p

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]