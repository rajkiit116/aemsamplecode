package com.pirelli.platform.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PirelliUtils {

	public static Map<String, String> populateHeaderMap() {
        Map<String, String> headerMap =new HashMap<>();
       headerMap.put("accept", "application/json");
       headerMap.put("Content-Type", "application/json");
       return headerMap;

	}
	
		public static void jsonArrayResponse(JSONArray jsonArray,SlingHttpServletResponse response) throws IOException {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
		       String finalSearchResponse = gson.toJson(jsonArray);
		       response.setContentType("application/json");
		       response.setCharacterEncoding("UTF-8");
		       response.setStatus(HttpServletResponse.SC_OK);
		       response.getWriter().write(finalSearchResponse);

	}
		
		public static void jsonObjectResponse(JSONObject jsonObject,SlingHttpServletResponse response) throws IOException {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
		       String finalSearchResponse = gson.toJson(jsonObject);
		       response.setContentType("application/json");
		       response.setCharacterEncoding("UTF-8");
		       response.setStatus(HttpServletResponse.SC_OK);
		       response.getWriter().write(finalSearchResponse);

	}
		
		public static void populateErrorStatus(SlingHttpServletResponse response) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	
		public static ResourceResolver getServiceResolver(ResourceResolverFactory resourceResolverFactory) throws LoginException {
			HashMap<String,Object> map=new HashMap<>();
			map.put(ResourceResolverFactory.SUBSERVICE, "readService");
			return resourceResolverFactory.getServiceResourceResolver(map);
			
		}
		
		
}
