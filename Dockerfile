# 1단계: 빌드 환경 설정
FROM gradle:jdk21 AS builder
WORKDIR /home/gradle/project

# 필요한 모든 소스 코드와 리소스 파일 복사
COPY --chown=gradle:gradle . .

# Gradle Wrapper에 실행 권한 부여
RUN chmod +x gradlew

# Gradle 캐시 클리어 및 빌드 (테스트 건너뛰기)
RUN ./gradlew clean build -x test --no-daemon

# 2단계: 실행 환경 설정
FROM eclipse-temurin:21-jre
WORKDIR /app

# 필수 패키지 설치
RUN apt-get update && apt-get install -y netcat-openbsd

# 빌드된 JAR 파일과 리소스 파일을 실행 환경으로 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
COPY --from=builder /home/gradle/project/src/main/resources /app/resources

# Kafka가 실행 중인지 확인하는 스크립트를 추가합니다.
COPY check-kafka.sh /app/check-kafka.sh

# 스크립트에 실행 권한 부여
RUN chmod +x /app/check-kafka.sh

# application.properties 파일 내용 출력 후 애플리케이션 시작
ENTRYPOINT ["sh", "-c", "/app/check-kafka.sh && cat /app/resources/application.properties && java -jar app.jar --spring.config.location=/app/resources/application.properties"]
