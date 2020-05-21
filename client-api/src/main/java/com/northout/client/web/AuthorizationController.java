/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.northout.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.security.auth.message.AuthException;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * @author Joe Grandja
 */
@Controller
public class AuthorizationController {

	@Value("${employees.base-uri}")
	private String employeesBaseUri;

	@Autowired
	private WebClient webClient;


	@GetMapping(value = "/authorize", params = "grant_type=authorization_code")
	public String authorization_code_grant(Model model) {
		String[] employees = retrieveMessages("northout-employee-client-auth-code");
		model.addAttribute("employees", employees);
		return "index";
	}

	@GetMapping("/authorized")		// registered redirect_uri for authorization_code
	public String authorized(Model model) {
		String[] employees = retrieveMessages("northout-employee-client-auth-code");
		model.addAttribute("employees", employees);
		return "index";
	}

	@GetMapping(value = "/authorize", params = "grant_type=client_credentials")
	public String client_credentials_grant(Model model) {
		String[] employees = retrieveMessages("northout-employee-client-client-creds");
		model.addAttribute("employees", employees);
		return "index";
	}

	@PostMapping(value = "/authorize", params = "grant_type=password")
	public String password_grant(Model model) {
		String[] employees = retrieveMessages("northout-employee-client-password");
		model.addAttribute("employees", employees);
		return "index";
	}


	private String[] retrieveMessages(String clientRegistrationId) {
		return this.webClient
				.get()
				.uri(this.employeesBaseUri)
				.attributes(clientRegistrationId(clientRegistrationId))
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, response -> {
					return Mono.error(new AuthException());
					} )
				.bodyToMono(String[].class)
				.block();
	}
}