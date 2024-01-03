package com.myaem65training.core.servlets;

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
    private static final AppConstants appConstants = new AppConstants();
    @Reference
    private transient SearchHelperService searchHelperService;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Node Created");
        MyNYTimesApiConfigurationFactory config = null;
        String tenantPath = request.getParameter("path");
        if(StringUtils.isNotEmpty(tenantPath)){
            config = getNyTimesApiConfig(tenantPath);
        }
        if(config != null){
            String bookDetailsUrl = config != null ? formRequestUrl(config.getNytimesBooksListEndPoint(), config, request) : StringUtils.EMPTY;
        }

    }
    public MyNYTimesApiConfigurationFactory getNyTimesApiConfig(String path){
        return searchHelperService != null ? searchHelperService.getConfigFactory(path) :  null;
    }
    public String formRequestUrl(String requestURL, MyNYTimesApiConfigurationFactory config, SlingHttpServletRequest request){
        String requestDomain = RequestUtil.getDomain(request, false);
        if(StringUtils.isNotEmpty(requestURL)){

        }
        return "";
    }
}
