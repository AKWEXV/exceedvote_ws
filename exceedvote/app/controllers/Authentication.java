package controllers;

import java.util.UUID;

import models.User;

import org.apache.commons.codec.digest.DigestUtils;

import play.libs.F;
import play.mvc.Http;
import play.mvc.SimpleResult;

public class Authentication extends play.mvc.Action.Simple {

	private final static String REALM = "EXCEEDVOTE";

	@Override
	public F.Promise<SimpleResult> call(Http.Context ctx) throws Throwable {
		
		String authHeader = ctx.request().getHeader("Authorization");

		if (authHeader == null) {
			String nonce = UUID.randomUUID().toString().replaceAll("-", "");
			String opaque = UUID.randomUUID().toString().replaceAll("-", "");
			ctx.response().setHeader("WWW-Authenticate", "Digest realm=\"" + REALM +  "\", nonce=\"" + nonce + "\", opaque=\"" + opaque + "\"");
			return F.Promise.pure((SimpleResult) unauthorized());
		}

    	else {	

    		String username = getAuthorizationElement("username", authHeader);
    		String password = getPassword(username);
    		
    		if (password != null) {
    			
    			String a1 = username + ":" + REALM + ":" + password;
    			String nonce = getAuthorizationElement("nonce", authHeader);
    			String uri = ctx.request().uri();
    			String method = ctx.request().method();
    			
	    		String a2 = method + ":" + uri;
	
	    		a1 = DigestUtils.md5Hex(a1);
	    		a2 = DigestUtils.md5Hex(a2);
	    		
	    		String validResponse = DigestUtils.md5Hex(a1 + ":" + nonce + ":" + a2);
	    		int responseIndex = authHeader.indexOf("response");
	    		String response = authHeader.substring(responseIndex+10, responseIndex+42);
	    		
	    		if (validResponse.equals(response))
//	    			return F.Promise.pure((SimpleResult) ok("Demo authentication application by using Digest Authentication\nValid Response : " + validResponse + "\n Your Response : " + response + "\nResponse Header : " + authHeader));
	    			return delegate.call(ctx);
	    		else
//	    			return F.Promise.pure((SimpleResult) ok("Cannot login\nValid Response : " + validResponse + "\n Your Response : " + response + "\nResponse Header : " + authHeader));
	    			return F.Promise.pure((SimpleResult) unauthorized());
	    	}
    		else {
    			return F.Promise.pure((SimpleResult) unauthorized());
    		}

    	}

	}
	
	private String getPassword(String username) {
		return User.getPasswordFromUsername(username);
	}
	
	private String getAuthorizationElement(String element, String requestHeader) {
		int index = requestHeader.indexOf(element);
		StringBuilder output = new StringBuilder();
		while (requestHeader.charAt(index) != '"') {
			index++;
		}
		index++;
		while (requestHeader.charAt(index) != '"') {
			output.append(requestHeader.charAt(index));
			index++;
		}
		return output.toString();
	}
	
}
