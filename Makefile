app-build:
	docker build -f src/main/docker/Dockerfile-dependency -t todoapp-multistage2 .

app-debug:
	echo "Connect remote debug on -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
	docker run -it --rm -p 7070:7070 -p 5005:5005 -e JVM_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" todoapp-multistage2:latest
