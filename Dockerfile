FROM eclipse-temurin:17-jdk

ENV PATH="$PATH:/root/.local/share/coursier/bin"

RUN curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz | gzip -d > /usr/bin/cs && chmod +x /usr/bin/cs && cs setup --yes

ADD *.sbt /app/
ADD project /app/project
ADD src /app/src
WORKDIR /app

VOLUME /app/target
RUN sbt clean compile

ENTRYPOINT ["sbt"]
CMD ["Gatling / test"]