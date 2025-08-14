FROM maven:3.9-openjdk-17-slim

# Set working directory
WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copy pom.xml files first for better caching
COPY pom.xml .
COPY */pom.xml ./

# Copy source code
COPY . .

# Build the project (this will download all dependencies)
RUN mvn dependency:go-offline

# Set environment variables
ENV MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
ENV DISPLAY=:99

# Expose port for debugging if needed
EXPOSE 4444

# Default command
CMD ["mvn", "clean", "test", "-Dheadless=true"]
