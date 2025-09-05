# 💪 GYMPT Fitness Application

<p align="center">
  <img src="./images/thumbnail.png" width="720" alt="GYMPT Thumbnail"/>
</p>

헬스 인구 증가에 따라 운동 계획 수립, 기록, 신체 변화 추적을 편리하게 제공하는 **헬스케어 앱**을 설계하고 구현한 프로젝트입니다.  
본 애플리케이션은 **운동 루틴 관리, 운동 기록 저장, 체중 및 중량 변화 분석**을 통해 사용자들의 운동 편의성과 동기부여를 강화하는 것을 목표로 합니다.  

📂 GitHub Repo: [링크 예정]

---

## 📖 프로젝트 개요
- **주제 선정 이유**: 헬스 인구 증가와 함께 운동 기록 및 분석의 필요성 증가  
- **목표**: 운동 일정/루틴 관리, 기록 및 분석을 통해 사용자 맞춤형 피트니스 지원  
- **환경**: Android Studio, MySQL, Firebase, PHP, FTP 서버  

---

## 👥 팀 구성 및 역할
- **김재엽 (팀장)**: 프로젝트 기획, FireBase 연동, Google 연동, 백엔드 구현  
- **박민우**: UI/UX 디자인, 프런트엔드 구현, 로고 디자인, 벤치마킹  
- **최지혁**: 데이터베이스 설계, DB 서버 연동, 운동 데이터 조사, 백엔드 구현  

---

## ⚙️ 주요 기능
- **회원가입/로그인**: Firebase 기반 계정 생성 및 인증  
- **운동 루틴 관리**: 운동 일정, 세트, 볼륨 입력 및 저장  
- **운동 기록**: 일별 기록 저장 및 달력 기반 조회  
- **체중/중량 분석**: 그래프 기반 변화 추이 시각화  
- **운동 보조**: 세트 타이머 및 휴식 알림 기능  
- **설정**: 사용자 정보 변경 및 회원 탈퇴 가능  

<p align="center">
  <img src="./images/features.png" width="700" alt="주요 기능 화면 예시"/>
</p>

---

## 🗄️ 시스템 구성
- **개발 환경**: Android Studio (Java), MySQL, Firebase, PHP, FTP  
- **DB 구조**: 회원, 운동, 기록, 분석 테이블 구성 (MySQL & Firebase Auth)  
- **UI 흐름**: 로그인/회원가입 → 운동 계획 → 운동 실행 → 기록 저장 → 분석 그래프  

<p align="center">
  <img src="./images/system_architecture.png" width="750" alt="시스템 아키텍처"/>
</p>

---

## 📊 결과 및 배운 점
- Volley 라이브러리의 비동기 문제 해결을 통해 데이터 순서 보장 구현  
- MVC 구조 및 서버-DB 연동 경험 강화  
- Firebase와 MySQL을 병행하여 유저 인증 및 데이터 관리 설계  
- 헬스케어 애플리케이션 설계 시 **사용자 경험(UI/UX)**의 중요성 체감  

<p align="center">
  <img src="./images/result.png" width="700" alt="결과 화면 예시"/>
</p>

---

## 🛠️ 사용 기술 (Tech Stack)
- **Frontend**: Android (Java), XML 기반 UI, Bootstrap 아이콘  
- **Backend**: PHP, Firebase Authentication, Firebase Realtime Database  
- **Database**: MySQL, Firebase (혼합 구조)  
- **Infra**: FTP 서버, Firebase Hosting  
- **Library/Tool**: Volley (비동기 통신), MPAndroidChart (그래프), GitHub, Figma  

---

## 📎 참고 자료
- 📄 [최종보고서 PDF](./docs/GYMPT_Final_Report.pdf)  
- 🔗 [Firebase 공식 문서](https://firebase.google.com/)  
- 🔗 [Volley Library Guide](https://developer.android.com/training/volley)  
