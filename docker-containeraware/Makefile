build:
	docker build . -t temurin-containeraware

shell:
	docker run -it --rm --memory 300m --memory-swap 300m --cpu-period 1_00000 --cpu-quota 2_00000 --entrypoint /bin/bash temurin-containeraware

containerAwareCPUx2:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware

containerAwareCPUx4:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 4_00000 temurin-containeraware

run600m:
	docker run -it --rm --memory 600m --memory-swap 600m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=60.0 -XX:MaxRAMPercentage=60.0 -XshowSettings:vm -version

run300m:
	docker run -it --rm --memory 300m --memory-swap 300m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=60.0 -XX:MaxRAMPercentage=60.0 -XshowSettings:vm -version

run600mWithXmx:
	docker run -it --rm --memory 600m --memory-swap 600m \
		--cpu-period 1_00000 --cpu-quota 2_00000 temurin-containeraware \
		-XX:InitialRAMPercentage=60.0 -XX:MaxRAMPercentage=60.0 -Xmx123m -XshowSettings:vm -version