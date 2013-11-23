package controllers;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	private final static String REALM = "EXCEEDVOTE";
	
    public static Result index() {
    	
    	String authHeader = request().getHeader("Authorization");
    	
    	if (authHeader == null) {
			String nonce = UUID.randomUUID().toString().replaceAll("-", "");
			String opaque = UUID.randomUUID().toString().replaceAll("-", "");
			response().setHeader("WWW-Authenticate", "Digest realm=\"" + REALM +  "\", nonce=\"" + nonce + "\", opaque=\"" + opaque + "\"");
			return unauthorized();
		}
    	else {	
    		String a1 = "username:" + REALM + ":password";

            int nonceindex = authHeader.indexOf("nonce");
            String nonce = authHeader.substring(nonceindex+7, nonceindex+39);
            int uriIndex = authHeader.indexOf("uri");
            String uri = authHeader.substring(uriIndex+5, uriIndex+6);

            String a2 = "GET:" + uri;

            a1 = DigestUtils.md5Hex(a1);
            a2 = DigestUtils.md5Hex(a2);
            
            String validResponse = DigestUtils.md5Hex(a1 + ":" + nonce + ":" + a2);
            int responseIndex = authHeader.indexOf("response");
            String response = authHeader.substring(responseIndex+10, responseIndex+42);
    		
    		if (validResponse.equals(response))
    			return ok("Demo authentication application by using Digest Authentication\nValid Response : " + validResponse + "\n Your Response : " + response + "\nResponse Header : " + authHeader);
    		else
    			return ok("Cannot login\nValid Response : " + validResponse + "\n Your Response : " + response + "\nResponse Header : " + authHeader);
    	}
    	
    }

}
