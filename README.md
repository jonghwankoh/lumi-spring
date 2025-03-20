## .env 파일 구성

```properties
JWT_SECRET={256bit key(base64)}  # JWT 서명을 위한 비밀 키
SPRING_PROFILES_ACTIVE=dev  # 활성화할 Spring 프로파일
DATABASE_URL=jdbc:mysql://localhost:3306/lumi  # 데이터베이스 연결 URL
DATABASE_USER=root  # 데이터베이스 사용자명
DATABASE_PASSWORD=0000  # 데이터베이스 비밀번호
OAUTH2_GOOGLE_CLIENT_SECRET={google oauth2 client secret} # OAuth2 클라이언트 비밀번호
ADMIN_EMAIL=admin@example.com  # 관리자 이메일
```

## 🔐 인증 방식
JWT 기반 인증을 사용하며, 토큰은 HTTP 쿠키를 통해 전송됩니다.

```text
Authorization=YOUR_JWT; Path=/; HttpOnly;
```
