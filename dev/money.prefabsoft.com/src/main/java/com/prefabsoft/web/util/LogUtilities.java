package com.prefabsoft.web.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;

import com.prefabsoft.log.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import flexjson.JSONSerializer;

/**
 * Some simple time savers. Part of tutorial on servlets and JSP that appears at
 * http://www.apl.jhu.edu/~hall/java/Servlet-Tutorial/ 1999 Marty Hall; may be
 * freely used or adapted.
 */

public class LogUtilities {

	private static final Logger log = LoggerFactory.getLogger(LogUtilities.class);
	private static final int LOG_MESSAGE_MAX_LENGTH = 2048;

	public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";
	private static boolean closeRequestHeadersAsHtmlString = true;

	public static String headWithTitle(String title) {
		return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
	}

	/**
	 * Read a parameter with the specified name, convert it to an int, and
	 * return it. Return the designated default value if the parameter doesn't
	 * exist or if it is an illegal integer format.
	 */

	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
		String paramString = request.getParameter(paramName);
		int paramValue;
		try {
			paramValue = Integer.parseInt(paramString);
		} catch (NumberFormatException nfe) { // Handles null and bad format
			paramValue = defaultValue;
		}
		return (paramValue);
	}

	public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return (defaultValue);
	}

	// Approximate values are fine.

	public static final int SECONDS_PER_MONTH = 60 * 60 * 24 * 30;
	public static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;

	public static String getRequestDataAsHtmlString(HttpServletRequest request,  HttpEntity<String> requestEntity) {
		StringBuilder stringBuilder = new StringBuilder();
		closeRequestHeadersAsHtmlString = false;
		stringBuilder.append(getRequestHeadersAsHtmlString(request));
		closeRequestHeadersAsHtmlString = true;
		stringBuilder.append("</TABLE>\n<BR>\n");
		stringBuilder.append("<H1>requestInfo:</H1>\n<BR>\n");
		stringBuilder.append("'<PRE>"+getRequestInfo(request)+"</PRE>'\n<BR>\n");
		stringBuilder.append("<H1>requestParameters:</H1>\n<BR>\n");
		stringBuilder.append("'<PRE>"+getRequestParameters(request)+"</PRE>'\n<BR>\n");
		stringBuilder.append("<H1>httpEntityBody:</H1>\n<BR>\n");
		stringBuilder.append("'<PRE>"+requestEntity.getBody()+"</PRE>'\n<BR>\n");
		stringBuilder.append("</BODY></HTML>");
		
		return stringBuilder.toString();
	}
	
	public static String getRequestHeadersAsHtmlString(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();

		String title = "Servlet Example: Showing Request Headers";
		stringBuilder.append(LogUtilities.headWithTitle(title) + "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H1 ALIGN=CENTER>"
				+ title + "</H1>\n" + "<B>Request Method: </B>" + request.getMethod() + "<BR>\n"
				+ "<B>Request URI: </B>" + request.getRequestURI() + "<BR>\n" + "<B>Request Protocol: </B>"
				+ request.getProtocol() + "<BR><BR>\n" + "<TABLE BORDER=1 ALIGN=CENTER>\n"
				+ "<TR BGCOLOR=\"#FFAD00\">\n" + "<TH>Header Name<TH>Header Value");
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			stringBuilder.append("<TR><TD>" + headerName);
			stringBuilder.append("    <TD>" + request.getHeader(headerName));
		}
		if(closeRequestHeadersAsHtmlString)
			stringBuilder.append("</TABLE>\n</BODY></HTML>");

		return stringBuilder.toString();
	}
	
	/**
	 * THE GOOD METHOD
	 * @param request
	 * @param requestEntity
	 * @return
	 */
	
	public static String getRequestDataAsJsonString(HttpServletRequest request, HttpEntity<String> requestEntity){
		Map<String, Object> requestInfo = getRequestInfo(request); //info mss ook uit headers te halen?
		log.debug("requestInfo: {}", requestInfo);
//		HttpHeaders httpHeaders = requestEntity.getHeaders();
		Map<String, Object> httpHeaders = getRequestHeaders(request);
		log.debug("httpHeaders: {}", httpHeaders);
//		byte[] requestBody = requestEntity.getBody();
		Map<String, Object> requestParameters = getRequestParameters(request);
		log.debug("requestParameters: {}", requestParameters);
		String requestBody = requestEntity.getBody();
		log.debug("requestBody: {}", requestBody);
		
		Map<String, Object> requestDataMap = new TreeMap<String, Object>();
		requestDataMap.put("requestInfo", requestInfo);//info mss ook uit headers te halen? of extra nog?
		requestDataMap.put("requestHeaders", httpHeaders);
		requestDataMap.put("requestParameters", requestParameters);
		requestDataMap.put("httpEntityBody", new String(requestBody));
		
		String requestDataAsJsonString = toJson(requestDataMap);
		
		return requestDataAsJsonString;
	}
	
	public static String logRequestDataAsJsonString(HttpServletRequest request, HttpEntity<String> requestEntity) {
		String prettyRequestDataAsJsonString = prettyJson(getRequestDataAsJsonString(request, requestEntity));
		logToDb("REQUEST", prettyRequestDataAsJsonString);
		return prettyRequestDataAsJsonString;
	}
	
	public static boolean logToDb(String level, String message){
		new Log(level, message).persist();
		log.debug("logged {} message to db: {}", level, message);
		return true;
	}
	
	public static String toJson(Object object){
		// convert to json string
		String result = new JSONSerializer().exclude("*.class").serialize(object);
		log.debug("toJson input Object: {} output string: {}", object, result);
		return result;
	}
	
	public static String prettyJson(String uglyJSONString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJsonString = gson.toJson(je);

		if (prettyJsonString.length() > LOG_MESSAGE_MAX_LENGTH)
			prettyJsonString = prettyJsonString.substring(0, LOG_MESSAGE_MAX_LENGTH - 3).concat("...");

		log.debug("prettyJson input request: {} output string: {}", uglyJSONString, prettyJsonString);

		return prettyJsonString;
	}
	
	public static Map<String, Object> getRequestInfo(HttpServletRequest request) {
		Map<String, Object> requestInfoMap = new TreeMap<String, Object>();

		requestInfoMap.put("methodString", request.getMethod());
		requestInfoMap.put("requestURIString", request.getRequestURI());
		requestInfoMap.put("protocolString", request.getProtocol());
		requestInfoMap.put("remoteHost", request.getRemoteHost());
		requestInfoMap.put("remotePort", request.getRemotePort());
		
		log.debug("getRequestInfo input request: {} output string: {}", request, requestInfoMap);

		return requestInfoMap;
	}

	public static Map<String, Object> getRequestHeaders(HttpServletRequest request) {
		Map<String, Object> headerMap = new TreeMap<String, Object>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			headerMap.put(headerName, request.getHeader(headerName));
		}
		log.debug("getRequestHeaders input request: {} output string: {}", request, headerMap);

		return headerMap;
	}
	
	public static Map<String, Object> getRequestParameters(HttpServletRequest request) {
		Map<String, Object> parameterMap = new TreeMap<String, Object>();
		
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = (String) parameterNames.nextElement();
			parameterMap.put(parameterName, request.getParameter(parameterName));
		}
		log.debug("getRequestParameters input request: {} output string: {}", request, parameterMap);
		
		return parameterMap;
	}
}
