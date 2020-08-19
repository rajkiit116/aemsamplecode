package com.pirelli.platform.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.pirelli.platform.core.utils.PirelliUtils;

@Component(service=Servlet.class,
property={
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/custom/createpage"
})
@ServiceDescription("Simple HandleClaim Servlet")
public class CreatePageHandleClaim extends SlingAllMethodsServlet {
     private static final long serialVersionUID = NumberUtils.LONG_ONE;

     private static final Logger log = LoggerFactory.getLogger(CreatePageHandleClaim.class);

     @Reference
     ResourceResolverFactory resolverFactory;
     
     private ResourceResolver resourceResolver;

     @Override
     protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

      try
      {
          log.info("---> THIS IS THE GET METHOD OF slingSevletApp/components/page/slingTemplate");
          PrintWriter out = response.getWriter();
          out.println("<html><body>");
          out.println("<h1>This value was returned by an AEM Sling Servlet bound by using a Sling ResourceTypes property</h1>");
          out.println("</html></body>");
          
          createPage();

          String path="/content/pirelli-project/language-masters/en/home/jcr:content";
          ResourceResolver resolver= PirelliUtils.getServiceResolver(resolverFactory);
         
          Resource resource=resolver.getResource(path);
          Node node=resource.adaptTo(Node.class);
          NodeIterator iterator=node.getNodes();
          while(iterator.hasNext()) {
        	  Node node1=iterator.nextNode();
          }
         
         
      }
      catch(Exception e)
      {
          log.info(e.getMessage(),e);
      }
    }
     
     private void createPage() throws Exception {
    	 String path="/content/pirelli-project/language-masters/en/home";
    	 String pageName="samplePage";
    	 String pageTitle="Sample Page";
    	 String template="/conf/pirelli-project/pirelli-internet-site/settings/wcm/templates/home-page/structure";
    	 String renderer="/apps/pirelli-project/pirelli-internet-site/components/content/header";

    	 this.resourceResolver = PirelliUtils.getServiceResolver(resolverFactory);

    	    Page prodPage = null;
    	    Session session = this.resourceResolver.adaptTo(Session.class);
    	    try { 
    	    if (session != null) {
    	 
    	    // Create Page      
    	    PageManager pageManager = this.resourceResolver.adaptTo(PageManager.class);
    	    prodPage = pageManager.create(path, pageName, template, pageTitle);
    	    Node pageNode = prodPage.adaptTo(Node.class);
    	 
    	 Node jcrNode = null;
    	 if (prodPage.hasContent()) {
    	 jcrNode = prodPage.getContentResource().adaptTo(Node.class);
    	 } else {                   
    	 jcrNode = pageNode.addNode("jcr:content", "cq:PageContent");
    	 } 
    	           jcrNode.setProperty("sling:resourceType", renderer);
    	     
    	     
    	         Node parNode = jcrNode.addNode("par");
    	         parNode.setProperty("sling:resourceType", "foundation/components/parsys");
    	 
    	  Node textNode = parNode.addNode("text");
    	  textNode.setProperty("sling:resourceType", "foundation/components/text");
    	  textNode.setProperty("textIsRich", "true");
    	  textNode.setProperty("text", "Test page"); 
    	 
    	 session.save();
    	        session.refresh(true);
    	  }
    	           
    	 } catch (Exception e) {
    	 throw e; 
    	 }  
    	   } 
}
