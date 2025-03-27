FROM openjdk:23-slim

# Install dependencies
RUN apt-get update && apt-get install -y wget unzip

# Download and install Maven 3.9.9
RUN wget https://downloads.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz \
    && tar -xzvf apache-maven-3.9.9-bin.tar.gz \
    && mv apache-maven-3.9.9 /opt/maven

# Set Maven environment variables
ENV MAVEN_HOME /opt/maven
ENV PATH $MAVEN_HOME/bin:$PATH

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Run Maven to install dependencies and package the tests
RUN mvn clean install

# Run the tests
CMD mvn test
