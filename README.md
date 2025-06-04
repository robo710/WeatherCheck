# WeatherCheck ☀️🌧️

**WeatherCheck**는 날씨 정보를 확인하고, 원하는 시간에 기온 및 강수확률 알림을 받을 수 있는 Android 기반 날씨 알림 앱입니다.  

---

## 주요 기능

- **날씨 정보 확인**: 오늘의 최고기온, 최저기온, 강수확률 표시
- **날씨 알림 설정**: 사용자가 설정한 시간에 날씨 정보 알림 수신
- **로컬 저장소**:  시간 및 설정값은 Room을 통해 로컬에 저장

---

## 🔧 기술 스택

| 분야 | 기술 |
|------|------|
| Architecture | MVVM, Clean Architecture |
| UI | Jetpack Compose |
| DI | Hilt |
| Local DB | Room |
| Background | WorkManager |
| Network | Retrofit, Kotlinx Serialization |
| Language | Kotlin |

---

## 🔔 알림 구성 예시

- **내용**: 최고 기온 27℃, 최저기온 6℃
- **시간**: 사용자가 지정한 오전 7시, 오후 6시 등 원하는 시각

## 📂 프로젝트 구조

```
WeatherCheck/
├── data/
│ ├── local/ // Room 엔티티 및
│ ├── remote/ // Retrofit API 정의
│ └── repository/ // Repository 구현
├── domain/
│ ├── model/ // Domain 모델 정의
│ ├── repository/ // 인터페이스 정의
│ └── usecase/ // 유즈케이스
├── presentation/
│ ├── screen/ // Compose 기반 화면 UI
│ ├── viewmodel/ // 상태 관리
│ └── alarm/ // 알림 설정 관련 로직
└── di/ // Hilt 모듈 구성
```

## 📸 스크린샷
![Screenshot_20250604_133442_WeatherCheck](https://github.com/user-attachments/assets/b59fb407-9dfd-4e32-907a-a48f1586ee7f)
![Screenshot_20250604_133450_WeatherCheck](https://github.com/user-attachments/assets/e54d979e-cad0-49a4-ba2a-5ad7e1e15d41)
