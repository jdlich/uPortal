/*
 * Copyright 2006 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.portal.events.support;

import org.jasig.portal.events.EventType;
import org.jasig.portal.events.PortalEvent;
import org.jasig.portal.security.IPerson;

/**
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 2.6
 * 
 */
public final class UserSessionDestroyedPortalEvent extends PortalEvent {
    private static final long serialVersionUID = 1L;
    
	public UserSessionDestroyedPortalEvent(final Object source, final IPerson person) {
		super(source, person, EventType.getEventType("SESSION_DESTROYED"));
	}

    /* (non-Javadoc)
     * @see java.util.EventObject#toString()
     */
	@Override
	public String toString() {
		return "Session destroyed for " + getDisplayName() + " at " + getTimestampAsDate();
	}
}
