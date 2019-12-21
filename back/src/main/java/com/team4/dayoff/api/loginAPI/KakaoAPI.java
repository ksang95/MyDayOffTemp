package com.team4.dayoff.api.loginAPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.dayoff.entity.Users;




public class KakaoAPI implements LoginAPI {

	public Map<String,String> getToken(String authorizeCode) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		Map<String,String> map=null;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=");
			sb.append("&redirect_uri=http://localhost:3000/login/process/kakao");
			sb.append("&code=" + authorizeCode);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			//JSON파싱 객체 생성
			
			ObjectMapper objectMapper=new ObjectMapper();
			map=objectMapper.readValue(result, Map.class);
			accessToken = map.get("access_token");
			refreshToken = map.get("refresh_token");

			System.out.println("access_token : " + accessToken);
			System.out.println("refresh_token : " + refreshToken);

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	public Users getUserInfo(String accessToken) {
		Users userInfo = new Users();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			ObjectMapper objectMapper=new ObjectMapper();
			Map<String,Object> map=objectMapper.readValue(result, Map.class);
			
			String socialId = "kakao_" + map.get("id");
			Map<String,Object> properties = (Map<String,Object>)map.get("properties");
			Map<String,Object> kakaoAccount =  (Map<String,Object>)map.get("kakao_account");

			userInfo.setSocialId(socialId);;
			if((boolean)kakaoAccount.get("has_gender")){
				String gender=(String)kakaoAccount.get("gender");
				userInfo.setSex(gender.substring(0,1).toLowerCase());
			};

			System.out.println(properties);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userInfo;
	}
	
	public int withdrawUser(String accessToken) {
			String reqURL = "https://kapi.kakao.com/v1/user/unlink";
			int id=-1;
			try {
				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				
				conn.setRequestProperty("Authorization", "Bearer " + accessToken);

				int responseCode = conn.getResponseCode();
				System.out.println("responseCode : " + responseCode);
				

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body : " + result);

				ObjectMapper objectMapper=new ObjectMapper();
				Map<String,Object> map=objectMapper.readValue(result, Map.class);
			
				id = (int)(map.get("id"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return id;
		}

	// @Override
	// public int logoutUser(String accessToken) {
	// 	String reqURL = "https://kapi.kakao.com/v1/user/logout";
	// 		int id=-1;
	// 		try {
	// 			URL url = new URL(reqURL);
	// 			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// 			conn.setDoOutput(true);
	// 			conn.setRequestMethod("POST");
				
	// 			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

	// 			int responseCode = conn.getResponseCode();
	// 			System.out.println("responseCode : " + responseCode);
				

	// 			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	// 			String line = "";
	// 			String result = "";

	// 			while ((line = br.readLine()) != null) {
	// 				result += line;
	// 			}
	// 			System.out.println("response body : " + result);

	// 			ObjectMapper objectMapper=new ObjectMapper();
	// 			Map<String,Object> map=objectMapper.readValue(result, Map.class);
			
	// 			id = (int)(map.get("id"));
	// 		} catch (IOException e) {
	// 			// TODO Auto-generated catch block
	// 			e.printStackTrace();
	// 		}

	// 		return id;
	// }
}
