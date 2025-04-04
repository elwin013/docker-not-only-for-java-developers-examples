build:
	docker build . -t temurin-containeraware

shell:
	docker run -it --rm --memory 300m --memory-swap 300m --cpu-period 1_00000 --cpu-quota 2_00000 \
	--entrypoint /bin/bash temurin-containeraware

containerAwareCPUx2:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware

containerAwareCPUx4:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 4_00000 temurin-containeraware

run600m:
	docker run -it --rm --memory 600m --memory-swap 600m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=30.0 -XX:MaxRAMPercentage=60.0 -XshowSettings:vm \
		-Xlog:gc+init \
		/example/ShowMxBeanCpuAndMemoryInfo.java

run300m:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=30.0 -XX:MaxRAMPercentage=60.0 -XshowSettings:vm \
		-Xlog:gc+init \
		/example/ShowMxBeanCpuAndMemoryInfo.java

run600mWithXmxAndXms:
	docker run -it --rm --memory 600m --memory-swap 600m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=60.0 -XX:MaxRAMPercentage=60.0 -Xmx200m -Xms100m -XshowSettings:vm \
		-Xlog:gc+init \
		/example/ShowMxBeanCpuAndMemoryInfo.java