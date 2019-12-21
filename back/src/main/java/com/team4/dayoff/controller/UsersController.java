package com.team4.dayoff.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team4.dayoff.api.loginAPI.GoogleAPI;
import com.team4.dayoff.api.loginAPI.KakaoAPI;
import com.team4.dayoff.api.loginAPI.LoginAPI;
import com.team4.dayoff.entity.Code;
import com.team4.dayoff.entity.LoginHistory;
import com.team4.dayoff.entity.Users;
import com.team4.dayoff.entity.WithdrawHistory;
import com.team4.dayoff.repository.CodeRepository;
import com.team4.dayoff.repository.LoginHistoryRepository;
import com.team4.dayoff.repository.UsersRepository;
import com.team4.dayoff.repository.WithdrawHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin("*")
@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	@Autowired
	private WithdrawHistoryRepository withdrawHistoryRepository;

	@Autowired
	private CodeRepository codeRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	private String mapping = "";

	@GetMapping(value = "/list1")
	public List<Users> userList() {
		List<Users> st = null;
		st = usersRepository.findAll();
		System.out.println(st);
		return st;
	}

	//@PostMapping("/login")
	public Users loginUsers(@RequestParam String code, @RequestParam String socialType) {
		LoginAPI login = null;
		switch (socialType) {
		case "kakao":
			login = new KakaoAPI();
			break;
		case "naver":
			break;
		case "google":
			break;
		}
		Map<String, String> token = login.getToken(code);
		String accessToken = token.get("access_token");
		String refreshToken = token.get("refresh_token");

		Users userInfo = login.getUserInfo(accessToken);
		System.out.println(userInfo);
		Users users = usersRepository.findBySocialIdAndRoleNot(userInfo.getSocialId(), "withdraw");
		System.out.println("db:" + users);
		if (users != null) {
			users.setAccessToken(accessToken);
			users.setRefreshToken(refreshToken);
			usersRepository.save(users);
			LoginHistory loginHistory = new LoginHistory();
			loginHistory.setUsers(users);
			loginHistoryRepository.save(loginHistory);
			// 세션 객체 생성
			return users;
		}
		users = userInfo;
		users.setAccessToken(accessToken);
		users.setRefreshToken(refreshToken);
		System.out.println("new:" + users);

		return users;
	}

	@GetMapping("/signUp")
	public Users loginUsers(OAuth2AuthenticationToken authenticationToken) {
		OAuth2AuthorizedClient client=authorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getPrincipal().getName());
		String socialType=authenticationToken.getAuthorizedClientRegistrationId();
		LoginAPI login = null;
		switch (socialType) {
		case "kakao":
			login = new KakaoAPI();
			break;
		case "naver":
			break;
		case "google":
			login=new GoogleAPI();
			break;
		}
		String accessToken = client.getAccessToken().getTokenValue();
		String refreshToken=null;
		//String refreshToken = client.getRefreshToken().getTokenValue();

		Users userInfo = login.getUserInfo(accessToken);
		System.out.println(userInfo);
		//Users users = usersRepository.findBySocialIdAndRoleNot(userInfo.getSocialId(), "withdraw");
		//System.out.println("db:" + users);
		// if (users != null) {
		// 	users.setAccessToken(accessToken);
		// 	users.setRefreshToken(refreshToken);
		// 	usersRepository.save(users);
		// 	LoginHistory loginHistory = new LoginHistory();
		// 	loginHistory.setUsers(users);
		// 	loginHistoryRepository.save(loginHistory);
		// 	// 세션 객체 생성
		// 	return users;
		// }
		Users users = userInfo;
		users.setAccessToken(accessToken);
		users.setRefreshToken(refreshToken);
		System.out.println("new:" + users);

		return users;
	} 

	@PostMapping("/signUpProcess")
	public Users signUpProcess(@RequestBody Users users) {

		Users savedUsers = usersRepository.save(users);
		System.out.println(savedUsers);
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setUsers(savedUsers);
		loginHistoryRepository.save(loginHistory);
		// 세션 객체 생성
		return savedUsers;
	}

	@GetMapping("/withdraw")
	public List<Code> withdrawUsers() {
		List<Code> list = codeRepository.findByCodeLikeOrderByCode("02%");
		return list;
	}

	@PostMapping("/withdrawProcess")
	public void withdrawUsersProcess(OAuth2AuthenticationToken authenticationToken, Code code) {
		String socialType=authenticationToken.getAuthorizedClientRegistrationId();
		String socialId=socialType+"_"+(authenticationToken.getName());
		Users users = usersRepository.findBySocialIdAndRoleNot(socialId, "withdraw");
		LoginAPI login = null;
		switch (socialType) {
		case "kakao":
			login = new KakaoAPI();
			break;
		case "naver":
			break;
		case "google":
			login=new GoogleAPI();
			break;
		}
		System.out.println(login.withdrawUser(users.getAccessToken()));
		usersRepository.withdrawUser(users.getId());
		WithdrawHistory withdrawHistory = new WithdrawHistory();
		withdrawHistory.setCode(code);
		withdrawHistory.setUsers(users);
		withdrawHistoryRepository.save(withdrawHistory);
		authorizedClientService.removeAuthorizedClient(socialType, authenticationToken.getName());
		System.out.println(authenticationToken.getName());
	}

	//@PostMapping("/logout")
	public void logoutUsers(Principal principal, @RequestParam("userId") Integer id) {
		// int id = Integer.parseInt(principal.getName());
		// Users users = usersRepository.findById(id).get();
		// String socialId = users.getSocialId();
		// LoginAPI login = null;
		// switch (socialId.substring(0, socialId.indexOf('_'))) {
		// case "kakao":
		// login = new KakaoAPI(); //로그아웃시켜도 로그인할때 아이디 비번 입력 창은 다시 나오지 않음. 왜?? 안나오면 굳이
		// api쓰는 의미가 없음
		// break;
		// case "naver":
		// // 네이버는 로그아웃 api가 없는 걸로 알고있음. 여기서 로그아웃 불가하다고 네이버 가서 로그아웃하라고 메시지 뿌리던가 하자.
		// break;
		// case "google":
		// break;
		// }
		// System.out.println(login.logoutUser(users.getAccessToken()));
		// 세션 객체 삭제

	}

	@GetMapping("/loginSuccess")
	public ModelAndView getLoginInfo(Model model,
			Authentication authentication, OAuth2AuthenticationToken authenticationToken, HttpServletRequest request) {
		String referer=request.getHeader("referer"); // 이전 페이지 주소
		System.out.println(referer);
		String socialType=authenticationToken.getAuthorizedClientRegistrationId();
		System.out.println(socialType); // 소셜 구별용
		System.out.println(authenticationToken.getDetails());

		
		OAuth2AuthorizedClient client=authorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getPrincipal().getName());

		String userInfoEndpointUri = client.getClientRegistration()
	.getProviderDetails().getUserInfoEndpoint().getUri();
   
	System.out.println("test================"+client.getAccessToken().getTokenValue());
	if (!org.springframework.util.StringUtils.isEmpty(userInfoEndpointUri)) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
		  .getTokenValue());
		HttpEntity entity = new HttpEntity("", headers);
		ResponseEntity <Map>response = restTemplate
		  .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		Map userAttributes = response.getBody();
		System.out.println(headers+"/"+entity+"/////"+response);
		model.addAttribute("name", userAttributes.get("name"));
	}
	
    if(authentication.getName().equals("2337851999660197")){


	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	

    List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
    
    updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    authentication = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
    
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
    }

    System.out.println(authentication.getAuthorities()); //어디서 로그인했는지
	System.out.println(authentication.getDetails());
	System.out.println(authentication.getCredentials());
	System.out.println(authentication.getPrincipal()); //userinfo

    if(referer==null)referer="http://localhost:3000/";
    return new ModelAndView("redirect:"+referer);
}

@GetMapping("/login")
public ModelAndView getMethodName2() {
	System.out.println(123);
	return new ModelAndView("redirect:https://localhost:3000/oauth2/authorization/google");
}
@RequestMapping(value = "/logout", method = RequestMethod.GET)
public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
System.out.println(request.getHeader("referer"));
if (auth != null){
new SecurityContextLogoutHandler().logout(request, response, auth);
}
return new ModelAndView("redirect:"+request.getHeader("referer"));
}
}
