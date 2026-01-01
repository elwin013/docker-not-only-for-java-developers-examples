FROM container-registry.oracle.com/java/openjdk:25

WORKDIR /example

COPY ./ShowMxBeanCpuAndMemoryInfo.java /example/ShowMxBeanCpuAndMemoryInfo.java

ENTRYPOINT ["java"]
CMD ["/example/ShowMxBeanCpuAndMemoryInfo.java"]