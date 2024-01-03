package com.myaem65training.core.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.myaem65training.core.constants.AppConstants;
import com.myaem65training.core.models.BookDetailResult;
import com.myaem65training.core.models.MyNYTimesAPIModel;
import com.myaem65training.core.service.MyNYTimesApiConfigurationFactory;
import com.myaem65training.core.service.SearchHelperService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import com.myaem65training.core.utils.RequestUtil;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = Servlet.class,
        property = {
        Constants.SERVICE_DESCRIPTION + "=Book Detail Servlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/bookdetail",
        ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json"  })
public class BookDetailServlet extends SlingSafeMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(BookDetailServlet.class);
    @Reference
    private transient SearchHelperService searchHelperService;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        JsonObject jsonResponse = new JsonObject();
        JsonParser parser = new JsonParser();
        MyNYTimesApiConfigurationFactory config = null;
        String tenantPath = request.getParameter("path");
        String bookDetailResponse = StringUtils.EMPTY;
        log.info("Getting payload from the servlet request .....");
        String payload = StringUtils.isNotEmpty(request.getParameter("payload")) ? request.getParameter("payload") : null;
        log.info("Request Payload is {} ", payload);
        JsonObject payloadObject = (JsonObject) parser.parse(payload);
        try{
            if(payloadObject.isJsonNull() || StringUtils.isEmpty(tenantPath)){
                throw new IllegalArgumentException("Bad request payload or pagepath is null");
            }
            if(StringUtils.isNotEmpty(tenantPath)){
                config = getNyTimesApiConfig(tenantPath);
            }
            if(config != null){
                String bookDetailsUrl = config != null ? formRequestUrl(config.getNytimesBooksListEndPoint(), config) : StringUtils.EMPTY;
                bookDetailResponse = searchHelperService.makeApiCall(config.getNytimesBooksListEndPoint(), config, payloadObject);
            }
            if(bookDetailResponse != null && !bookDetailResponse.contains("error")){
                response.setStatus(HttpServletResponse.SC_OK);
                JsonObject jsonBookDetailResponse = (JsonObject) parser.parse(bookDetailResponse);
                jsonResponse.add("book_detail", jsonBookDetailResponse);
            }else{
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JsonObject jsonBookDetailResponse = (JsonObject) parser.parse(bookDetailResponse);
                jsonResponse.addProperty(AppConstants.MESSAGE, jsonBookDetailResponse.getAsJsonObject("error").get("errorDescription").getAsString());
            }
        } catch(JsonParseException e){
            log.error("Could not formulate the JSON response", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            jsonResponse.addProperty(AppConstants.MESSAGE, e.getMessage());
        } catch(IllegalArgumentException | UnsupportedOperationException e){
            log.error("IllegalArgumentException | UnsupportedOperationException :: ", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse.addProperty(AppConstants.MESSAGE, e.getMessage());
        } finally{
            response.getWriter().write(jsonResponse.toString());
            response.getWriter().flush();
        }


    }
    public MyNYTimesApiConfigurationFactory getNyTimesApiConfig(String path){
        return searchHelperService != null ? searchHelperService.getConfigFactory(path) :  null;
    }
    public String formRequestUrl(String requestURL, MyNYTimesApiConfigurationFactory config){
        log.info("Request URL : {}", requestURL);
        if(StringUtils.isNotEmpty(requestURL)){
            requestURL = config.getNytimesEndPoint() + config.getNytimesBooksListEndPoint() ;
        } else{
            requestURL = StringUtils.EMPTY;
        }
        return requestURL;
    }
}
