package com.myaem65training.core.servlets;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;



@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Fetch User List servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/userlist",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class FetchUserListingServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(FetchUserListingServlet.class);
    private JSONObject userJsonObject = new JSONObject();

    private JSONArray userJsonArray = new JSONArray();
    @Reference
    ResourceResolverFactory resolverFactory;
    @Reference
    private SlingRepository repository;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        log.info("Do get method was invoked .........................");
        userJsonObject = new JSONObject();
        userJsonArray = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try{
            log.info("I am inside the Try catch method ....... ");
            ResourceResolver resourceResolver  = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            final UserManager userManager = resourceResolver.adaptTo(UserManager.class);
            Iterator<Authorizable> userIterator = userManager.findAuthorizables("jcr:primaryType", "rep:User");
            while (userIterator.hasNext()) {
                log.info("I am inside the While Condition for checking that UserIterator has Next Item ....... ");
                Authorizable user = userIterator.next();
                if (!user.isGroup()) {
                    log.info("userId", user.getID());
                    log.info("userPath", user.getPath());
                    log.info("userEmail", user.getProperty("./profile/email")[0].getString());
                    log.info("userPic",user.getPath()+"/profile.profile.image");
                    JSONObject userList = new JSONObject();
                    userList.put("userId", user.getID());
                    userList.put("userPath", user.getPath());
                    userList.put("userEmail", user.getProperty("./profile/email")[0].getString());
                    userList.put("userPic",user.getPath()+"/profile.profile.image");
                    userJsonArray.put(userList);
                }
            }
            userJsonObject.put("userList", userJsonArray);
            //response.getWriter().write(userJsonObject.toString());
            response.getWriter().write("Hi");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
