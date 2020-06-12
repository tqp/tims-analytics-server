package com.timsanalytics.auth.authGoogle.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.AuthService;
import com.timsanalytics.auth.authCommon.services.TokenService;
import com.timsanalytics.auth.authGoogle.beans.GoogleAuthConfig;
import com.timsanalytics.auth.authGoogle.beans.GoogleToken;
import com.timsanalytics.auth.authGoogle.beans.GoogleUser;
import com.timsanalytics.auth.authGoogle.dao.GoogleAuthDao;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.api.client.googleapis.util.Utils.getDefaultJsonFactory;

@Service
public class GoogleAuthService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final Environment environment;
    private final AuthService authService;
    private final TokenService tokenService;
    private final TokenExchangeService tokenExchangeService;
    private final GoogleAuthDao googleAuthDao;

    public GoogleAuthService(AuthService authService,
                             TokenService tokenService,
                             Environment environment,
                             TokenExchangeService tokenExchangeService,
                             GoogleAuthDao googleAuthDao) {
        this.environment = environment;
        this.authService = authService;
        this.tokenService = tokenService;
        this.tokenExchangeService = tokenExchangeService;
        this.googleAuthDao = googleAuthDao;
    }

    public GoogleAuthConfig getGoogleAuthConfig() {
        GoogleAuthConfig googleAuthConfig = new GoogleAuthConfig();
        googleAuthConfig.setClientId(this.environment.getProperty("google.client-id"));
        googleAuthConfig.setRedirectUri(this.environment.getProperty("google.redirect-uri"));
        return googleAuthConfig;
    }

    public void receiveGoogleAuthorizationCode(String code, HttpServletResponse response) throws Exception {
        GoogleUser googleUser = this.authenticate(code);
        this.logger.debug("GoogleAuthController -> receiveGoogleAuthorizationCode: username=" + googleUser.getEmail());

        // Get AppUser
        User appUser = this.authService.getUserAndRolesForAuthentication(googleUser.getEmail());

        if (appUser != null) {

            // Update User Record
            this.googleAuthDao.updateUserRecordFromGoogleAuth(appUser, googleUser);

            // Generate Token
            String token = this.tokenService.generateTokenFromUser(appUser);
            this.logger.debug("Token=" + token);

            // Generate short-lived token to pass securely to client.
            String shortLivedToken = generateShortLivedToken(token);

            // Redirect to short-lived token exchange page
            this.logger.debug("Redirecting SLT to " + environment.getProperty("application.client.url") + "/open-pages/token-exchange?slt=" + shortLivedToken);
            response.sendRedirect(environment.getProperty("application.client.url") + "/open-pages/token-exchange?slt=" + shortLivedToken);
        } else {
            this.logger.debug("User not found");
            response.sendRedirect(environment.getProperty("application.client.url") + "/open-pages/login?error=UsernameNotFoundException");
        }
    }

    private GoogleUser authenticate(String googleAuthorizationCode) throws Exception {
        // Construct POST request to Google for id_token
        String googleTokenUrl = environment.getProperty("google.token-url");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("code", googleAuthorizationCode));
        urlParameters.add(new BasicNameValuePair("client_id", environment.getProperty("google.client-id")));
        urlParameters.add(new BasicNameValuePair("client_secret", environment.getProperty("google.client-secret")));
        urlParameters.add(new BasicNameValuePair("grant_type", environment.getProperty("google.grant-type")));
        urlParameters.add(new BasicNameValuePair("redirect_uri", environment.getProperty("google.redirect-uri")));

        Gson gson = new Gson();
        String googleTokenJson = this.HttpClientPost(googleTokenUrl, urlParameters);
        GoogleToken googleTokenObject = gson.fromJson(googleTokenJson, GoogleToken.class);

        // Construct GET request to Google for User info
        String googleTokenInfoUrl = environment.getProperty("google.token-info-url");
        String googleUserJson = HttpClientGet(googleTokenInfoUrl + "?id_token=" + googleTokenObject.getId_token());
        return gson.fromJson(googleUserJson, GoogleUser.class);
    }

    private String generateShortLivedToken(String token) {
        String shortLivedToken = tokenExchangeService.generateToken("token1", "IM", "exchangeToken", 1000000); // Get from yml in future
//        this.logger.debug("shortLivedToken: " + tokenExchangeService.shortId(shortLivedToken));
//        this.logger.debug("[S] Google Provider -> Adding tokens to map...");
//        this.logger.debug("*** Auth Token = " + tokenExchangeService.shortId(token));
//        this.logger.debug("*** SLT Token  = " + tokenExchangeService.shortId(shortLivedToken));
        tokenExchangeService.addItem(shortLivedToken, token);
        return shortLivedToken;
    }

    private String HttpClientGet(String url) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0");

        HttpResponse response = client.execute(get);
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private String HttpClientPost(String url, List<NameValuePair> urlParameters) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", "Mozilla/5.0");

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters, "UTF-8");
        post.setEntity(urlEncodedFormEntity);

        HttpResponse response = client.execute(post);
        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + post.getEntity());
        //System.out.println("URL parameters : " + urlParameters);
        //System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private GoogleIdToken.Payload GoogleTokenVerifier(String idTokenString) throws Exception {
        // This is one method of getting user info from the token.
        // It's a bit more obfuscated than the Http get method.
        // There's also not a lot of documentation on it.

        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, getDefaultJsonFactory())
                .setAudience(Collections.singletonList(environment.getProperty("google.client-id")))
                .setIssuer("https://accounts.google.com")
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Get profile information from payload
            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            return payload;
        } else {
            this.logger.error("Invalid ID token.");
        }

        return null;
    }
}
