# UMC Spring Boot Study

Spring Boot로 REST API를 만들어보는 스터디 프로젝트입니다.  

## 사용 기술

- Java 17
- Spring Boot
- Spring Data JPA
- QueryDSL
- Spring Security, JWT
- MySQL
- Redis
- Swagger
- Docker
- Railway

## 구현 기능

### 회원

- 회원가입
- 로그인
- 회원 탈퇴
- JWT 토큰 기반 인증

### 게시글

- 게시글 작성
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제
- 게시글 목록 조회
- 키워드, 날짜 조건을 이용한 검색
- QueryDSL을 이용한 목록 조회

### 댓글

- 댓글 작성
- 댓글 삭제

### 날씨

- 도시 이름을 입력하면 현재 날씨 정보를 조회할 수 있도록 외부 API를 연동했습니다.

### 기타

- 공통 응답 형식 적용
- 예외 처리 구조 분리
- Swagger API 문서 설정
- `/api/ping`으로 서버 상태 확인

## 주요 API

| Method | URL | 설명 |
|---|---|---|
| POST | `/users/signup` | 회원가입 |
| POST | `/users/login` | 로그인 |
| DELETE | `/users/{userId}` | 회원 탈퇴 |
| POST | `/posts` | 게시글 작성 |
| GET | `/posts/{postId}` | 게시글 상세 조회 |
| PUT | `/posts/{postId}` | 게시글 수정 |
| DELETE | `/posts/{postId}` | 게시글 삭제 |
| GET | `/posts/lists` | 게시글 목록 조회 |
| GET | `/posts/lists/querydsl` | QueryDSL 게시글 목록 조회 |
| POST | `/comments` | 댓글 작성 |
| DELETE | `/comments/{commentId}` | 댓글 삭제 |
| GET | `/weather?city={city}` | 날씨 조회 |
| GET | `/api/ping` | 서버 상태 확인 |

## 프로젝트 구조

```text
src/main/java/com/example/umcspringbootstudy
├─ user
├─ post
├─ comment
├─ weather
└─ global
```

## 실행

```bash
./gradlew bootRun
```

Windows에서는 아래 명령어로 실행할 수 있습니다.

```bash
gradlew.bat bootRun
```
