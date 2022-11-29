package com.taiwanlife.ikash.backend.configuration.Security.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.taiwanlife.ikash.backend.configuration.request.WebHttpServletRequestWrapper;
import com.taiwanlife.ikash.backend.utility.HttpRequestUtil;
import com.taiwanlife.ikash.backend.utility.JsonUtil;

@Slf4j
public class RestLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String REST_FORM_USERNAME_KEY = SPRING_SECURITY_FORM_USERNAME_KEY;
	public static final String REST_FORM_MIMA_KEY = SPRING_SECURITY_FORM_PASSWORD_KEY;

	private String usernameParameter = REST_FORM_USERNAME_KEY;
	private String mimaParameter = REST_FORM_MIMA_KEY;

	private boolean postOnly = true;

	/**
	 * Constructor
	 */
	public RestLoginAuthenticationFilter() {
		this(new AntPathRequestMatcher("/perform_login", "POST"));
	}

	/**
	 * Constructor
	 * 
	 * @param defaultFilterProcessesUrl
	 */
	public RestLoginAuthenticationFilter(String defaultFilterProcessesUrl) {
		this(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}

	/**
	 * Constructor
	 * 
	 * @param requestMatcher
	 */
	public RestLoginAuthenticationFilter(RequestMatcher requestMatcher) {
		setRequiresAuthenticationRequestMatcher(requestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (log.isDebugEnabled()) {
			log.debug("<attemptAuthentication()>");
		}

		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String contentType = request.getContentType();

		if (log.isDebugEnabled()) {
			log.debug("** contentType: " + contentType);
		}

		if (!StringUtils.isEmpty(request.getContentType())) {
			String username = null;
			char[] mima;

			if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
				String stream = HttpRequestUtil.getBodyContent(new WebHttpServletRequestWrapper(request));

				if (log.isDebugEnabled()) {
					log.debug("** [REQUEST] Stream: " + stream);
				}

				@SuppressWarnings("unchecked")
				Map<String, String> body = JsonUtil.convertJsonToClass(stream, Map.class);

				username = obtainUsername(body);
				mima = obtainPassword(body).toCharArray();
			} else {
				username = obtainUsername(request);
				mima = obtainPassword(request).toCharArray();
			}

			if (username == null) {
				username = "";
			}

			if (mima.length == 0) {
				mima = null;
			}

			username = username.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, mima);

			setDetails(request, authRequest);

			if (log.isDebugEnabled()) {
				log.debug("</attemptAuthentication()>");
			}

			return this.getAuthenticationManager().authenticate(authRequest);

		} else {
			if (log.isDebugEnabled()) {
				log.debug("</attemptAuthentication()>");
			}

			return super.attemptAuthentication(request, response);
		}
	}

	/**
	 * Enables subclasses to override the composition of the username, such as by
	 * including additional values and a separator.
	 *
	 * @param map so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	@Nullable
	protected String obtainUsername(Map<String, String> map) {
		return map.get(usernameParameter);
	}

	@Nullable
	protected String obtainPassword(Map<String, String> map) {
		return map.get(mimaParameter);
	}

	@Override
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
		super.setUsernameParameter(usernameParameter);
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the
	 * login request..
	 *
	 * @param mimaParameter the parameter name. Defaults to "password".
	 */
	@Override
	public void setPasswordParameter(String mimaParameter) {
		Assert.hasText(mimaParameter, "Password parameter must not be empty or null");
		this.mimaParameter = mimaParameter;
		super.setPasswordParameter(mimaParameter);
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter. If
	 * set to true, and an authentication request is received which is not a POST
	 * request, an exception will be raised immediately and authentication will not
	 * be attempted. The <tt>unsuccessfulAuthentication()</tt> method will be called
	 * as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
		super.setPostOnly(postOnly);
	}

	/**
	 * @return boolean
	 */
	public boolean getPostOnly() {
		return postOnly;
	}
}
