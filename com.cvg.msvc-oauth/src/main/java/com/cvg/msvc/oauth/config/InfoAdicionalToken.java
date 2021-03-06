package com.cvg.msvc.oauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.cvg.msvc.oauth.models.entity.UserModel;
import com.cvg.msvc.oauth.service.IUserService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {
	
	@Autowired private IUserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		UserModel model = userService.findByUsername( authentication.getName() );
		info.put("name", model.getName());
		info.put("lastName", model.getLastName());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
