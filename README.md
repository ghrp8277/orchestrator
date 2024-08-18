# 종목 토론 프로젝트

## ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)  👉🏻[Postman API Document](https://documenter.getpostman.com/view/11658972/2sA3s9CoB4)

## 프로젝트 설계도
![image](https://github.com/user-attachments/assets/d959759f-bd80-4182-ab76-739423653e77)
![image](https://github.com/user-attachments/assets/b987bebe-745d-48c2-8d6b-d0defa2b408e)

## ERD
![image](https://github.com/user-attachments/assets/b37b8dda-5374-46e3-8a6a-2c491eaf1504)

## 프로젝트 소개
사용자에게 다양한 주가 정보를 제공하고, 토론과 분석을 지원하는 서비스

## 주요 기능
### 주가 정보 수집 및 관리
- **기능 설명** : 네이버 증권 API를 활용하여 종목별 주가 데이터를 실시간으로 수집 및 관리
- **기술적 분석 지표** : 주가 데이터를 기반으로 다양한 기술적 분석 지표(이동평균선, 볼린저 밴드, MACD 등)를 생성

### 실시간 주가 정보 제공
- **기능 설명**: 최신 주가 데이터를 실시간으로 업데이트하여 사용자에게 제공
- **사용자 혜택**: 사용자는 실시간으로 주가 변동 확인 가능

### 토론 게시판
- **기능 설명**: 특정 종목에 대한 토론을 진행할 수 있는 게시판 제공
- **사용자 혜택**: 다른 투자자들과 정보를 공유하고, 종목에 대한 다양한 의견 교환




## 프로젝트 특징

### **실시간 주가 정보 지표 생성**
- 최신 주가 데이터를 실시간으로 분석하여 다양한 기술적 지표를 생성

### **API 활용**
- 다양한 증권 API를 통합하여 종목별 주가 정보를 효율적으로 조회

### **Kubernetes 사용**
- Kubernetes를 활용하여 클라우드 네이티브 애플리케이션 배포 및 관리

### **gRPC 및 오케스트레이션 방식**
- gRPC를 활용하여 마이크로 서비스 간의 고성능 통신 구현
- 오케스트레이션 방식을 통해 여러 마이크로 서비스를 효율적으로 관리하고 조율하여 시스템의 전체적인 성능과 안정성 보장

### **마이크로 서비스 아키텍처(MSA)**
- 기존의 모노리스 서비스를 마이크로 서비스 아키텍처로 전환하여, 시스템의 확장성과 유연성 향상





## 아키텍처 구성 요소

### Orchestrator
- **역할**: 전체 시스템을 조율하는 서비스로, 여러 인스턴스로 구성되어 있으며, gRPC를 통해 다른 마이크로 서비스와 통신

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
