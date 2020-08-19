package com.pirelli.platform.core.workflkow;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.exec.WorkflowData;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;



@Component(service = WorkflowProcess.class,property= {Constants.SERVICE_DESCRIPTION + "=Pirelli Mail Trigger Processing",
        Constants.SERVICE_VENDOR + "=SARB",
        "process.label" + "=Pirelli Mail Trigger Processing"})
public class PirelliMailTriggerProcess implements WorkflowProcess {

	/** Default log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Reference
    private ResourceResolverFactory resolverFactory;	
    
    @Reference
    private MessageGatewayService messageGatewayService;
    
    @Reference
    WorkflowService workflowService;
	
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {

        
try
{
    log.info("Here in execute method");    
    
    final Map<String, Object> map = new HashMap<>();
    map.put("user.jcr.session", workflowSession.getSession());
    ResourceResolver resourceResolver = resolverFactory.getResourceResolver(map);
    
    final com.day.cq.workflow.exec.WorkflowData workflowData = workItem.getWorkflowData();
    final String type = workflowData.getPayloadType();
    if (!StringUtils.equals(type, "JCR_PATH")) {
        return;
    }
    Session session = workflowSession.getSession();
    final String path = workflowData.getPayload().toString();
    try {
        Node node = session.getNode(path);
        /* Write your custom code here.  */
    } catch (Exception ex) {
        /* Use this block for Exception Handling.  */
    } 
    
    //Declare a MessageGateway service
    MessageGateway<Email> messageGateway; 
          
    //Set up the Email message
    Email email = new SimpleEmail();
          
    //Set the mail values
    String emailToRecipients = "rajmishra.kiit@gmail.com"; 
    String emailCcRecipients = "rajmishra.kumar1@gmail.com"; 
      
    email.addTo(emailToRecipients);
    email.addCc(emailCcRecipients);
    email.setSubject("AEM Custom Step");
    email.setFrom("rajkiit116@adobe.com"); 
    email.setMsg("This message is to inform you that the CQ content has been deleted");
      
    //Inject a MessageGateway Service and send the message
    messageGateway = messageGatewayService.getGateway(Email.class);
  
    // Check the logs to see that messageGateway is not null
    messageGateway.send((Email) email);
}
  
    catch (Exception e)
    {
    e.printStackTrace()  ; 
    }
 
	}


}
