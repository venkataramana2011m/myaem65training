package com.myaem65training.core.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myaem65training.core.constants.AppConstants;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component(service = {Servlet.class},
        property = {
                Constants.SERVICE_DESCRIPTION + "=Recipe Filter By Category Servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/recipefilterbycategory",
                ServletResolverConstants.DEFAULT_RESOURCE_TYPE + "=sling/servlet/default",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json"
        })
public class RecipeFilterByCategory extends SlingSafeMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(RecipeFilterByCategory.class);
    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {
        String selectedCategory = request.getParameter("selectedCategory");
        JsonParser jsonParser = new JsonParser();
        BufferedReader bufferedReader = null;
        CloseableHttpClient httpClient =null;
        StringBuilder result = new StringBuilder();
        String line ;
        try{
            int timeout = 5;
            RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(10000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet httpget = new HttpGet(AppConstants.RECIPIE_API_ENDPOINT.concat(AppConstants.RECIPIE_API_FILTER_BY_CATEGORY.concat(selectedCategory)));
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
            bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
            while ((line = bufferedReader.readLine()) != null) { result.append(line); }
            bufferedReader.close();
            jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result.toString()).getAsJsonObject();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } catch (NullPointerException e){
            log.error(e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        } finally{
            httpClient.close();
        }
    }
}
