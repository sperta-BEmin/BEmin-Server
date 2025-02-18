package run.bemin.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import run.bemin.api.auth.jwt.JwtUtil;

public class JwtUtilTokenTest {

  @Test
  public void testGetUserEmailFromRealToken() {

    JwtUtil jwtUtil = new JwtUtil();

    String realToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWFAZ21haWwuY29tIiwiYXV0aCI6Ik1BU1RFUiIsImV4cCI6MTczOTk0ODgxNywiaWF0IjoxNzM5ODYyNDE3fQ.NYbc0ht8n10TSYWN8LD25NV8CkSo6qljv-dGswYbikg";

    jwtUtil.secretKey = "4TZesOUVNyExWRyAzKSE2Gfiz55u+u2HxVCJrbvnEQU=";
    jwtUtil.init();

    // 토큰에서 이메일 추출
    String extractedEmail = jwtUtil.getUserEmailFromToken(realToken);
    assertEquals("aaa@gmail.com", extractedEmail);
  }
}