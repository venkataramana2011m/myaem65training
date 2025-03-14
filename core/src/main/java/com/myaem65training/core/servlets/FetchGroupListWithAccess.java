package com.myaem65training.core.servlets;

import com.day.cq.security.privileges.Privilege;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.JackrabbitAccessControlManager;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Fetch User List servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/groupListWithAccess",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class FetchGroupListWithAccess extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(FetchGroupListWithAccess.class);
    private JSONObject userJsonObject = new JSONObject();

    private JSONArray userJsonArray = new JSONArray();
    private JSONArray groupPermissionsJsonArray = new JSONArray();
    @Reference
    ResourceResolverFactory resolverFactory;
    @Reference
    private SlingRepository repository;
    public enum CRXPATHS { apps, etc, conf, content, libs, home, system, bin, tmp, var; }


    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {
        log.info("Do get method was invoked .........................");
        userJsonObject = new JSONObject();
        userJsonArray = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try{
            log.info("I am inside the Try catch method ....... ");
            /*ResourceResolver resourceResolver  = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            final UserManager groupManager = resourceResolver.adaptTo(UserManager.class);*/
            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            UserManager groupManager = ((JackrabbitSession) session).getUserManager();
            Iterator<Authorizable> groupIterator = groupManager.findAuthorizables("jcr:primaryType", "rep:Group");
            while (groupIterator.hasNext()) {
                log.info("Getting group");
                Authorizable group = groupIterator.next();
                if (group.isGroup()) {
                    log.info("Group found {}", group.getID());
                    log.info("groupId", group.getID());
                    log.info("groupPath", group.getPath());
                    log.info("groupEmail", group.getProperty("./profile/email")[0].getString());
                    log.info("groupPic",group.getPath()+"/profile.profile.image");
                    JSONObject userList = new JSONObject();
                    userList.put("userId", group.getID());
                    userList.put("userPath", group.getPath());
                    groupPermissionsJsonArray = new JSONArray();
                    for (CRXPATHS path : CRXPATHS.values()) {
                        System.out.println(path);
                        JSONObject groupPermissionList = new JSONObject();
                        groupPermissionList.put("path", fetchGroupPermissions(path.toString(), group.getID().toString()));
                        groupPermissionsJsonArray.put(groupPermissionList);
                    }
                    userList.put("Permissions", groupPermissionsJsonArray);
                    userJsonArray.put(userList);
                }
            }
            userJsonObject.put("Groups", userJsonArray);
            response.getWriter().write(userJsonObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject fetchGroupPermissions(String path, String groupName){
        JSONObject retGrpPermissions= new JSONObject();
        int perCount=0;
        try {
            ResourceResolver resourceResolver = null;

                resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);

            Session adminSession = resourceResolver.adaptTo(Session.class);

            JackrabbitAccessControlManager acm = (JackrabbitAccessControlManager) adminSession.getAccessControlManager();
            UserManager userManager = resourceResolver.adaptTo(UserManager.class);
            Authorizable auth  = userManager.getAuthorizable(groupName);

            Set<Principal> principals = new HashSet<>();
            principals.add(auth.getPrincipal());

            Privilege[] privileges = (Privilege[]) acm.getPrivileges(path, principals);


            for(Privilege p : privileges){
                retGrpPermissions.put("group:", auth.getID().toString());
                retGrpPermissions.put("privilege:", p.getID().toString());
                log.info("**** Here are the permissions: group: "+auth.getID()+" privilege: "+p.getID());
                perCount++;
            }
        } catch (LoginException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedRepositoryOperationException e) {
            throw new RuntimeException(e);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return retGrpPermissions;
    }
}
