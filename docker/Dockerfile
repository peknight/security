FROM openjdk:8u212-jdk-alpine3.9

MAINTAINER peknight <JKpeknight@gmail.com>

ADD lib /lib
ADD applications.xml /conf/
WORKDIR /conf

ENTRYPOINT ["java"]
CMD ["-jar", "/lib/security-0.0.1-SNAPSHOT.jar"]
