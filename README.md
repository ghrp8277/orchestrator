# 프로젝트 설계도
![image](https://github.com/user-attachments/assets/d959759f-bd80-4182-ab76-739423653e77)
# ERD
![image](https://github.com/user-attachments/assets/b37b8dda-5374-46e3-8a6a-2c491eaf1504)


# 프로젝트 특징

## **실시간 주가 정보 지표 생성**
최신 주가 데이터를 실시간으로 분석하여 다양한 기술적 지표를 생성합니다. 이를 통해 사용자에게 신속하고 정확한 투자 정보를 제공합니다.

## **API 활용**
다양한 증권 API를 통합하여 종목별 주가 정보를 효율적으로 불러옵니다. 이 데이터를 기반으로 한 다양한 분석 기능을 제공하여 사용자 경험을 향상시킵니다.

## **Kubernetes 사용**
Kubernetes를 활용하여 클라우드 네이티브 애플리케이션을 배포하고 관리합니다. 이를 통해 서비스의 확장성과 가용성을 극대화합니다.

## **마이크로 서비스 아키텍처(MSA)**
기존의 모노리스 서비스를 마이크로 서비스 아키텍처로 전환하여, 시스템의 확장성과 유연성을 크게 향상시킵니다. 각각의 서비스는 독립적으로 개발, 배포, 운영될 수 있어, 개발 효율성과 운영 안정성을 높입니다.

# 아키텍처 구성 요소

### Orchestrator
- **역할**: 전체 시스템을 조율하는 서비스로, 여러 인스턴스로 구성되어 있으며, gRPC를 통해 다른 마이크로 서비스와 통신합니다.

### [UserService](https://github.com/ghrp8277/user_service)
- **역할**: 사용자 정보를 관리합니다.

### [AuthService](https://github.com/ghrp8277/auth_service)
- **역할**: 인증 및 권한 부여를 담당합니다.

### [EmailService](https://github.com/ghrp8277/email_service)
- **역할**: 이메일 전송과 관련된 기능을 제공합니다.

### [SocialService](https://github.com/ghrp8277/social_service)
- **역할**: 소셜 기능을 관리합니다.

### [StockService](https://github.com/ghrp8277/stock_service)
- **역할**: 주식 정보를 관리합니다.

### [BatchService](https://github.com/ghrp8277/batch_service)
- **역할**: 배치 작업을 처리하고 주가 데이터를 크롤링하여 저장합니다.
