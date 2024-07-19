```markdown
# PadoIn'(PadoIng) Project

![Project Image](https://i.imgur.com/4wYiECq.jpeg)

## 📜 프로젝트 소개

PadoIng 프로젝트는 동영상 스트리밍 플랫폼입니다. 사용자는 소셜 로그인을 통해 회원 가입을 할 수 있으며, 동영상을 재생하고, 광고를 시청할 수 있습니다. 또한 동영상의 조회수와 광고 시청 횟수를 기반으로 통계와 정산 데이터를 생성하고 조회할 수 있습니다.

## 🛠 기술 스택

### 백엔드
- **Spring Boot:** 애플리케이션의 전반적인 개발을 위한 프레임워크
- **Spring Security:** 인증 및 권한 부여
- **Spring Batch:** 배치 작업을 위한 프레임워크
- **MySQL:** 관계형 데이터베이스 관리 시스템
- **Gradle:** 프로젝트 빌드 도구
- **Docker, Docker Compose:** 애플리케이션 컨테이너화 및 배포

## 📂 프로젝트 구조

```
PadoIng-master/
├── ERD/
│   ├── PadoIng_ERD.png
│   └── PadoIng_ERD.sql
└── pado-ing/
├── Dockerfile
├── build.gradle
├── docker-compose.yml
├── gradlew
├── gradlew.bat
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/com/sparta/padoing/
│   │   │   ├── batch/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── domain/
│   │   │   ├── dto/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   └── PadoIngApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/sparta/padoing/
│       └── PadoIngApplicationTests.java
└── gradle/
└── wrapper/
```

## 📦 설치 및 실행

### 1. 클론 및 빌드

```bash
git clone https://github.com/your-username/PadoIng.git
cd PadoIng/pado-ing
./gradlew build
```

### 2. 도커 설정

```bash
docker-compose up -d
```

### 3. 애플리케이션 실행

```bash
./gradlew bootRun
```

## 🔍 주요 기능

### 회원 서비스

- **소셜 로그인**
    - 소셜 로그인을 통해 회원 가입 기능을 제공합니다.
    - 사용자 계정과 판매자 계정의 권한을 구분합니다.

- **JWT 토큰 기반 로그인 및 로그아웃**
    - JWT 토큰을 활용한 로그인 기능을 제공합니다.
    - 로그아웃 기능을 제공합니다.

### 스트리밍 서비스

- **동영상 재생**
    - 동영상을 재생할 수 있습니다.
    - 재생 시 조회수가 증가하고, 중단 시 재생 시점이 저장됩니다.

- **광고 시청**
    - 동영상에 등록된 광고 영상을 재생할 수 있습니다.
    - 광고 영상의 시청 횟수를 카운트합니다.

### 통계 및 정산

- **통계 데이터 생성 및 조회**
    - 1일, 1주일, 1달 동안 조회수가 높은 동영상 TOP5와 재생 시간이 긴 동영상 TOP5를 조회할 수 있습니다.

- **정산 데이터 생성 및 조회**
    - 동영상과 광고 영상의 정산 금액을 계산하고 조회할 수 있습니다.
    - 동영상별 정산 금액은 조회수에 따라 다르게 책정됩니다.
    - 광고 영상의 정산 금액은 광고 조회수에 따라 다르게 책정됩니다.
    - 정산 데이터는 1일 단위로 생성됩니다.
    - 정산 데이터는 영상별 단가와 광고별 단가를 기반으로 계산됩니다.
    - 동영상 길이에 따라 광고가 자동으로 등록됩니다 (5분당 1개씩).
```