#!/bin/bash
# set -e 옵션을 비활성화
# set -e
set -x  # 디버깅 모드 활성화

# 현재 날짜와 시간을 변수에 저장
CURRENT_DATE=$(date +"%Y%m%d-%H%M%S")
LOG_FILE="build_push_log_$CURRENT_DATE.txt"
VALUES_BLUE="values-blue.yaml"
VALUES_GREEN="values-green.yaml"

# 초기 상태 변수 설정
STATUS=0

# 사용자의 입력을 받음
echo "어느 환경으로 배포하시겠습니까? (1: Blue, 2: Green)"
read -p "선택하세요: " ENV_CHOICE

# 선택된 환경에 따라 변수 설정
if [ "$ENV_CHOICE" == "1" ]; then
    ENVIRONMENT="blue"
    VALUES_FILE="$VALUES_BLUE"
    echo "Blue 환경을 선택하셨습니다."
elif [ "$ENV_CHOICE" == "2" ]; then
    ENVIRONMENT="green"
    VALUES_FILE="$VALUES_GREEN"
    echo "Green 환경을 선택하셨습니다."
else
    echo "잘못된 선택입니다. 1 또는 2를 선택하세요."
    exit 1
fi

# 로그 파일 생성 및 빌드 로그 기록 시작
{
    echo "=== Docker 이미지 빌드 및 푸시 시작: $CURRENT_DATE ==="
    echo "이미지 태그: 192.168.0.212:5555/spring-orchestrator:$CURRENT_DATE"
    echo ""

    # Docker 이미지 빌드
    echo "Docker 이미지 빌드 중..."
    docker build -t 192.168.0.212:5555/spring-orchestrator:$CURRENT_DATE -f ../Dockerfile .. 2>&1
    BUILD_STATUS=$?
    if [ $BUILD_STATUS -ne 0 ]; then
        echo "Docker 이미지 빌드 실패"
        STATUS=1
    else
        echo "Docker 이미지 빌드 성공"
    fi

    echo ""

    # Docker 이미지 푸시
    echo "Docker 이미지 푸시 중..."
    docker push 192.168.0.212:5555/spring-orchestrator:$CURRENT_DATE 2>&1
    PUSH_STATUS=$?
    if [ $PUSH_STATUS -ne 0 ]; then
        echo "Docker 이미지 푸시 실패"
        STATUS=1
    else
        echo "Docker 이미지 푸시 성공"
    fi

    echo ""
    echo "=== Docker 이미지 빌드 및 푸시 완료: $CURRENT_DATE ==="

    # Values 파일 업데이트
    echo "Values 파일 업데이트 중 ($ENVIRONMENT)..."
    if [ ! -f "$VALUES_FILE" ];then
        echo "Values 파일이 존재하지 않습니다: $VALUES_FILE"
        exit 1
    fi
    sed -i.bak "s|imageTag: \".*\"|imageTag: \"$CURRENT_DATE\"|g" $VALUES_FILE
    rm "$VALUES_FILE.bak"

    echo "Values 파일 업데이트 완료: $VALUES_FILE"

    # 선택된 환경에 대한 설치 또는 업데이트
    echo "Helm 작업 시작 ($ENVIRONMENT)..."
    if helm list -n prod | grep "backend-$ENVIRONMENT" > /dev/null; then
        echo "Helm 업데이트 실행 중: backend-$ENVIRONMENT"
        helm upgrade backend-$ENVIRONMENT . -f values.yaml -f $VALUES_FILE -n prod | tee -a $LOG_FILE
        if [ $? -ne 0 ]; then
            STATUS=1
        fi
    else
        echo "Helm 설치 실행 중: backend-$ENVIRONMENT"
        helm install backend-$ENVIRONMENT . -f values.yaml -f $VALUES_FILE -n prod | tee -a $LOG_FILE
        if [ $? -ne 0 ]; then
            STATUS=1
        fi
    fi

    if [ $STATUS -eq 0 ]; then
        echo "$ENVIRONMENT 배포 성공"
    else
        echo "$ENVIRONMENT 배포 실패"
        exit 1
    fi

    # 트래픽을 선택된 환경으로 전환
    echo "트래픽을 $ENVIRONMENT 환경으로 전환 중..."
    kubectl patch service orchestrator-service -n prod -p '{"spec": {"selector": {"app": "orchestrator", "version": "'$ENVIRONMENT'"}}}' 2>&1 | tee -a $LOG_FILE
    if [ $? -ne 0 ]; then
        echo "트래픽 전환 실패"
        STATUS=1
    else
        echo "트래픽 전환 성공"
    fi

} | tee $LOG_FILE

# 모든 작업이 성공적으로 완료된 경우 로그 파일 삭제 및 로컬 이미지 삭제
if [ $STATUS -eq 0 ]; then
    rm $LOG_FILE
    docker rmi 192.168.0.212:5555/spring-orchestrator:$CURRENT_DATE
else
    echo "작업 중 오류가 발생했습니다. 로그 파일을 확인하세요: $LOG_FILE"
fi

set +x  # 디버깅 모드 비활성화
