package com.pirelli.platform.core.listeners;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.pirelli.platform.core.utils.PirelliUtils;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;

@Component(service = EventHandler.class, immediate = true,
property = {
        EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
}
)
public class TestReplicationHandler implements EventHandler {
	
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference
    private ResourceResolverFactory resolverFactory;

	@Override
	public void handleEvent(Event event) {

		try {
			ResourceResolver resolver = PirelliUtils.getServiceResolver(resolverFactory);
			ReplicationAction replicationAction = ReplicationAction.fromEvent(event);
			if (null != replicationAction) {
			String path=replicationAction.getPath();
			
			if(replicationAction.getType().equals(ReplicationActionType.ACTIVATE)) {
				log.debug("page has been activated");
			} else if(replicationAction.getType().equals(ReplicationActionType.DEACTIVATE)) {
				log.debug("page has been deactivated");
			}
			}
			
		} catch (LoginException e) {
			e.printStackTrace();
		}
	
	}

}
	