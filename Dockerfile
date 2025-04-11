# Amazon Corretto 17 이미지를 베이스 이미지로 사용
FROM amazoncorretto:17-alpine

# 작업 디렉토리 설정
WORKDIR /app

# .dockerignore 파일에 명시된 파일을 제외하고 프로젝트 파일을 컨테이너로 복사
COPY . /app

# Gradle Wrapper를 사용하여 애플리케이션 빌드
RUN ./gradlew build --no-daemon -x test

# 80 포트를 노출
EXPOSE 80

# 환경 변수 설정
ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
# JVM 옵션을 환경 변수로 설정 (기본값은 빈 문자열)
ENV JVM_OPTS=""

# 애플리케이션 실행 명령어 설정 (환경 변수로 정의한 정보 활용)
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]