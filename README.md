# Navy Bucket Storage

<div align="center">
<img width="329" alt="logo" src="https://github.com/user-attachments/assets/00dfebcb-52bc-440b-987c-fe207ac664d6">
</div>

# Navy Bucket Storage
> **개인 프로젝트** <br/> **개발기간: 2024.11 ~ 진행중**

[//]: # (## 배포 주소)

[//]: # ()
[//]: # (> **개발 버전** : [http://voluntain.cs.skku.edu/]&#40;http://voluntain.cs.skku.edu/&#41; <br>)

[//]: # (> **프론트 서버** : [http://voluntain.cs.skku.edu:33307/]&#40;http://voluntain.cs.skku.edu:33307/&#41;<br>)

[//]: # (> **백엔드 서버** : [http://voluntain.cs.skku.edu:2223/]&#40;http://voluntain.cs.skku.edu:2223/&#41;<br>)

## 개발 인원

|                                                                장태희                                                                 |
|:----------------------------------------------------------------------------------------------------------------------------------:| 
| <img src="https://github.com/2023WB-TeamB/Backend/assets/154852834/c10d0c0c-2ece-415c-980d-67c566439e4c" width="160" height="160"> |
|                                              [@TaeHee00](https://github.com/TaeHee00)                                              |
|                                                        한국공학대학교 소프트웨어학과 1학년                                                         |

## 프로젝트 소개

NavyBucketStorage는 Amazon S3와 유사한 클라우드 스토리지 시스템을 구현한 오픈소스 프로젝트입니다. 이 프로젝트는 대용량 데이터 저장, 검색, 관리를 위한 안정적이고 확장 가능한 솔루션을 제공합니다.

[//]: # (## 시작 가이드)

[//]: # (### Requirements)

[//]: # (For building and running the application you need:)

[//]: # ()
[//]: # (- [Node.js 14.19.3]&#40;https://nodejs.org/ca/blog/release/v14.19.3/&#41;)

[//]: # (- [Npm 9.2.0]&#40;https://www.npmjs.com/package/npm/v/9.2.0&#41;)

[//]: # (- [Strapi 3.6.6]&#40;https://www.npmjs.com/package/strapi/v/3.6.6&#41;)

[//]: # ()
[//]: # (### Installation)

[//]: # (``` bash)

[//]: # ($ git clone https://github.com/Voluntain-SKKU/Voluntain-2nd.git)

[//]: # ($ cd Voluntain-2nd)

[//]: # (```)

[//]: # (#### Backend)

[//]: # (```)

[//]: # ($ cd strapi-backend)

[//]: # ($ nvm use v.14.19.3)

[//]: # ($ npm install)

[//]: # ($ npm run develop)

[//]: # (```)

[//]: # ()
[//]: # (#### Frontend)

[//]: # (```)

[//]: # ($ cd voluntain-app)

[//]: # ($ nvm use v.14.19.3)

[//]: # ($ npm install )

[//]: # ($ npm run dev)

[//]: # (```)

---

## Stacks 🐈

### Environment
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-179287?style=for-the-badge&logo=gitkraken&logoColor=white)
![GitKraken](https://img.shields.io/badge/GitKraken-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)

### Development
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Communication
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

---

[//]: # (## 화면 구성 📺)

[//]: # (| 메인 페이지  |  소개 페이지   |)

[//]: # (| :-------------------------------------------: | :------------: |)

[//]: # (|  <img width="329" src="https://user-images.githubusercontent.com/50205887/208036155-a57900f7-c68a-470d-923c-ff3c296ea635.png"/> |  <img width="329" src="https://user-images.githubusercontent.com/50205887/208036645-a76cf400-85bc-4fa2-af72-86d2abf61366.png"/>|  )

[//]: # (| 강좌 소개 페이지   |  강의 영상 페이지   |  )

[//]: # (| <img width="329" src="https://user-images.githubusercontent.com/50205887/208038737-2b32b7d2-25f4-4949-baf5-83b5c02915a3.png"/>   |  <img width="329" src="https://user-images.githubusercontent.com/50205887/208038965-43a6318a-7b05-44bb-97c8-b08b0495fba7.png"/>     |)

[//]: # ()
[//]: # (---)
## 주요 기능 📦

### ⭐️ 버킷 관리 및 객체 스토리지
- 데이터를 저장할 버킷 생성, 삭제 및 관리 기능
- 다양한 크기와 유형의 파일을 객체로 저장하고 검색 가능
- 대용량 파일을 위한 멀티파트 업로드 지원

### ⭐️ 강력한 보안 기능
- 서버 측 암호화로 저장된 데이터 자동 보호
- 세밀한 접근 제어를 위한 ACL(Access Control List) 설정
- HTTPS 지원으로 안전한 데이터 전송 보장

### ⭐️ 고급 데이터 관리 기능
- 객체 수명 주기 관리로 자동 삭제 또는 저장 클래스 변경
- 효율적인 관리를 위한 객체 태깅 시스템
- 데이터 가용성 향상을 위한 크로스 리전 복제 지원

### ⭐️ 성능 최적화 및 모니터링
- CDN 통합 및 캐싱 메커니즘으로 빠른 콘텐츠 전달
- 상세한 액세스 로그 및 성능 메트릭 제공
- 중요 이벤트에 대한 알림 설정 기능

### ⭐️ 개발자 친화적 API 및 SDK
- RESTful API를 통한 모든 기능 접근 가능
- 다양한 프로그래밍 언어를 위한 SDK 제공
- 편리한 작업을 위한 CLI(Command Line Interface) 도구 지원

---
## 아키텍쳐

### 디렉토리 구조
```bash
├── README.md
├── package-lock.json
├── package.json
├── strapi-backend : 
│   ├── README.md
│   ├── api : db model, api 관련 정보 폴더
│   │   ├── about
│   │   ├── course
│   │   └── lecture
│   ├── config : 서버, 데이터베이스 관련 정보 폴더
│   │   ├── database.js
│   │   ├── env : 배포 환경(NODE_ENV = production) 일 때 설정 정보 폴더
│   │   ├── functions : 프로젝트에서 실행되는 함수 관련 정보 폴더
│   │   └── server.js
│   ├── extensions
│   │   └── users-permissions : 권한 정보
│   ├── favicon.ico
│   ├── package-lock.json
│   ├── package.json
│   └── public
│       ├── robots.txt
│       └── uploads : 강의 별 사진
└── voluntain-app : 프론트엔드
    ├── README.md
    ├── components
    │   ├── CourseCard.js
    │   ├── Footer.js
    │   ├── LectureCards.js
    │   ├── MainBanner.js : 메인 페이지에 있는 남색 배너 컴포넌트, 커뮤니티 이름과 슬로건을 포함.
    │   ├── MainCard.js
    │   ├── MainCookieCard.js
    │   ├── NavigationBar.js : 네비게이션 바 컴포넌트, _app.js에서 공통으로 전체 페이지에 포함됨.
    │   ├── RecentLecture.js
    │   └── useWindowSize.js
    ├── config
    │   └── next.config.js
    ├── lib
    │   ├── context.js
    │   └── ga
    ├── next.config.js
    ├── package-lock.json
    ├── package.json
    ├── pages
    │   ├── _app.js
    │   ├── _document.js
    │   ├── about.js
    │   ├── course
    │   ├── index.js
    │   ├── lecture
    │   ├── newcourse
    │   ├── question.js
    │   └── setting.js
    ├── public
    │   ├── favicon.ico
    │   └── logo_about.png
    └── styles
        └── Home.module.css

```

<!--
```bash
├── README.md : 리드미 파일
│
├── strapi-backend/ : 백엔드
│   ├── api/ : db model, api 관련 정보 폴더
│   │   └── [table 이름] : database table 별로 분리되는 api 폴더 (table 구조, 해당 table 관련 api 정보 저장)
│   │       ├── Config/routes.json : api 설정 파일 (api request에 따른 handler 지정)
│   │       ├── Controllers/ [table 이름].js : api controller 커스텀 파일
│   │       ├── Models : db model 관련 정보 폴더
│   │       │   ├── [table 이름].js : (사용 X) api 커스텀 파일
│   │       │   └── [table 이름].settings.json : model 정보 파일 (field 정보)
│   │       └─── Services/ course.js : (사용 X) api 커스텀 파일
│   │ 
│   ├── config/ : 서버, 데이터베이스 관련 정보 폴더
│   │   ├── Env/production : 배포 환경(NODE_ENV = production) 일 때 설정 정보 폴더
│   │   │   └── database.js : production 환경에서 database 설정 파일
│   │   ├── Functions : 프로젝트에서 실행되는 함수 관련 정보 폴더
│   │   │   │   ├── responses : (사용 X) 커스텀한 응답 저장 폴더
│   │   │   │   ├── bootstrap.js : 어플리케이션 시작 시 실행되는 코드 파일
│   │   │   │   └── cron.js : (사용 X) cron task 관련 파일
│   │   ├── database.js : 기본 개발 환경(NODE_ENV = development)에서 database 설정 파일
│   │   └── server.js : 서버 설정 정보 파일
│   │  
│   ├── extensions/
│   │   └── users-permissions/config/ : 권한 정보
│   │ 
│   └── public/
│       └── uploads/ : 강의 별 사진
│
└── voluntain-app/ : 프론트엔드
    ├── components/
    │   ├── NavigationBar.js : 네비게이션 바 컴포넌트, _app.js에서 공통으로 전체 페이지에 포함됨.
    │   ├── MainBanner.js : 메인 페이지에 있는 남색 배너 컴포넌트, 커뮤니티 이름과 슬로건을 포함.
    │   ├── RecentLecture.js : 사용자가 시청 정보(쿠키)에 따라, 현재/다음 강의를 나타내는 컴포넌트 [호출: MainCookieCard]
    │   ├── MainCookieCard.js : 상위 RecentLecture 컴포넌트에서 전달받은 props를 나타내는 레이아웃 컴포넌트.
    │   ├── MainCard.js : 현재 등록된 course 정보를 백엔드에서 받아서 카드로 나타내는 컴포넌트 [호출: CourseCard]
    │   └── CourseCard.js : 상위 MainCard 컴포넌트에서 전달받은 props를 나타내는 레이아웃 컴포넌트
    │
    ├── config/
    │   └── next.config.js
    │
    ├── lib/
    │   └── ga/
    │   │   └── index.js
    │   └── context.js
    │
    ├── pages/
    │   ├── courses/
    │   │   └── [id].js : 강의 페이지
    │   ├── _app.js : Next.js에서 전체 컴포넌트 구조를 결정, 공통 컴포넌트(navbar, footer)가 선언되도록 customizing 됨.
    │   ├── _document.js : Next.js에서 전체 html 문서의 구조를 결정, lang 속성과 meta tag가 customizing 됨.
    │   ├── about.js : 단체 소개 페이지
    │   ├── index.js : 메인 페이지
    │   ├── question.js : Q&A 페이지
    │   └── setting.js : 쿠키, 구글 애널리틱스 정보 수집 정책 페이지
    │
    ├── public/
    │   ├── favicon.ico : 네비게이션바 이미지
    │   └── logo_about.png : about 페이지 로고 이미지
    │
    └── styles/
        └── Home.module.css

```
-->