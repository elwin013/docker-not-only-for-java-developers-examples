FROM eclipse-temurin

WORKDIR /example

COPY ./ShowMxBeanCpuAndMemoryInfo.java /example/ShowMxBeanCpuAndMemoryInfo.java
RUN javac /example/ShowMxBeanCpuAndMemoryInfo.java

ENTRYPOINT ["/bin/bash"]