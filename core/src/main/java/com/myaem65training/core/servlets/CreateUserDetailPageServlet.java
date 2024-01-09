package com.myaem65training.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
@Component(service= Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Create User Detail Page servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/myaem65training/createuserdetailpage",
                ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json" })
public class CreateUserDetailPageServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(CreateUserDetailPageServlet.class);
    private static final String PAGE_TEMPLATE = "/conf/myaem65training/settings/wcm/templates/page-content";
    private static final String RENDERER = "myaem65training/components/page";
    @Reference
    private ResourceResolverFactory resolverFactory;
    private ResourceResolver resourceResolver;
    private String pageName = StringUtils.EMPTY;
    private String pageTitle = StringUtils.EMPTY;
    private String path = "/content/myaem65training/us/en/leadership";
    private Page prodPage = null;
    private Session session;

    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws ServletException, IOException {
        try{
            log.info("----------< Executing Query Builder Servlet >----------");
            resourceResolver = request.getResourceResolver();
            session = resourceResolver.adaptTo(Session.class);
            if(request.getParameter("userID").toString() != null){
                String userId = request.getParameter("userID").toString();
                pageName = userId.toLowerCase().replace(".","-");
                pageTitle = userId.toUpperCase().replace("."," ");
                log.info("Page Name :: {} ", pageName);
                log.info("Page Title :: {} ", pageTitle);
                createPages(pageName, pageTitle, resourceResolver);
            }
            session.save();
            session.refresh(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    public void createPages(String pageName, String pageTitle, ResourceResolver resourceResolver) {
        try {
            log.error("With in the Create Page method ...... !!!! ");
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            log.error("With in the Create Page method ...... !!!! ");
            prodPage = pageManager.create(path, pageName, PAGE_TEMPLATE, pageTitle, true);
            Resource resource = resourceResolver.getResource(prodPage.getPath());
            Node pageNode = resource.adaptTo(Node.class);
            Node jcrNode = null;
            if (prodPage.hasContent()) {
                jcrNode = prodPage.getContentResource().adaptTo(Node.class);
            } else {
                jcrNode = pageNode.addNode("jcr:content", "cq:PageContent");
            }
            jcrNode.setProperty("sling:resourceType", RENDERER);
            Node root = session.getNode(prodPage.getPath().toString() + "/jcr:content/root/container/container");
            Node day = root.addNode("biographycomponent", "nt:unstructured");
            day.setProperty("sling:resourceType", "myaem65training/components/biographycomponent");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
