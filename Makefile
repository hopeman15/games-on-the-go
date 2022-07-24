FLAVOR ?= Staging
BUILD_TYPE ?= Debug
GRADLE_ARGS ?= --build-cache

.PHONY: all assemble build bundle clean dependencies format install lint pod-install publish \
release report report-html test-android test-shared upload-coverage

all: clean pod-install format lint test report-html assemble

assemble:
	./gradlew assemble${FLAVOR}${BUILD_TYPE} ${GRADLE_ARGS}

build:
	./gradlew build${FLAVOR}${BUILD_TYPE} ${GRADLE_ARGS}

bundle:
	./gradlew bundle${FLAVOR}${BUILD_TYPE} ${GRADLE_ARGS}

clean:
	./gradlew clean ${GRADLE_ARGS}

dependencies:
	./gradlew dependencyUpdates

format:
	./gradlew formatKotlin ${GRADLE_ARGS}

install:
	./scripts/install.sh ${FLAVOR} ${BUILD_TYPE}

lint:
	./gradlew lint${FLAVOR}${BUILD_TYPE} lintKotlin detekt ${GRADLE_ARGS}

pod-install:
	./gradlew :shared:podInstall

publish:
	./scripts/publish.sh ${FLAVOR} ${BUILD_TYPE} ${PLAY_PUBLISH_PASSWORD}

release:
	./scripts/release.sh ${FLAVOR} ${BUILD_TYPE}

report:
	./gradlew koverXmlReport ${GRADLE_ARGS}

report-html:
	./gradlew koverMergedHtmlReport ${GRADLE_ARGS}

test: test-android test-shared

test-android:
	./gradlew android:test${FLAVOR}${BUILD_TYPE} ${GRADLE_ARGS}

test-shared:
	./gradlew :shared:test${BUILD_TYPE}UnitTest

upload-coverage:
	./scripts/upload-coverage.sh ${CODECOV_TOKEN}
