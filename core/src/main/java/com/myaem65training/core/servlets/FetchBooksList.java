package com.myaem65training.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.JsonParser;
import com.myaem65training.core.constants.AppConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Nonnull;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
/*import java.net.http.HttpClient;
import java.net.http.HttpRequest;*/
import java.util.HashSet;
import java.util.Iterator;

/**
 * Fetiching the List of the Books from the NYtimes API
 */
@SuppressWarnings("unused")
@Component(service=Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Book Listing servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" +HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/fetchbooks",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class FetchBooksList extends SlingAllMethodsServlet {
    private static final long serialVersionUID = -1464983465750830035L;
    private static final Logger log = LoggerFactory.getLogger(FetchBooksList.class);
    private static final AppConstants appConstants = new AppConstants();  
    private HashSet<String> uniqueBookList;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        StringBuffer jsonResponseData = new StringBuffer();
        BufferedReader readLine = null;
        BufferedReader bufferedReader = null;
        CloseableHttpClient httpClient =null;
        try {
            uniqueBookList = new HashSet<String>();
            String newPath = appConstants.TNYTIMES_ENDPOINT.toString() + appConstants.TNYTIMES_BOOKS_CATEGORIES_API.toString()
                    + appConstants.TNYTIMES_QUERYPARAMETER.toString()
                    + appConstants.TNYTIMES_API_KEY_PARAMETER_KEY.toString()
                    + appConstants.TNYTIMES_API_KEY_PARAMETER_VALUE.toString();

            int timeout = 5;
            RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(10000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet httpget = new HttpGet(newPath);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
            bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line ;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            com.google.gson.JsonParser jsonParser = new JsonParser();
            com.google.gson.JsonObject jsonObject = jsonParser.parse(result.toString()).getAsJsonObject();
            //mapper.writer().writeValue(response.getWriter(), jsonObject);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } catch (NullPointerException e){
            log.error(e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        }finally{
            httpClient.close();
        }

    }


}
