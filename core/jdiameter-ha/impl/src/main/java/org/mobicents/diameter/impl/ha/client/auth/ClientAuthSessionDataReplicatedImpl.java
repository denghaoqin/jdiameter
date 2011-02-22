/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.diameter.impl.ha.client.auth;

import java.io.Serializable;

import org.jboss.cache.Fqn;
import org.jdiameter.api.auth.ClientAuthSession;
import org.jdiameter.client.impl.app.auth.IClientAuthSessionData;
import org.jdiameter.common.api.app.auth.ClientAuthSessionState;
import org.mobicents.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientAuthSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IClientAuthSessionData {

  private static final String STATE = "STATE";
  private static final String DESTINATION_HOST = "DESTINATION_HOST";
  private static final String DESTINATION_REALM = "DESTINATION_REALM";
  private static final String STATELESS = "STATELESS";
  private static final String TS_TIMERID = "TS_TIMERID";

  /**
   * @param nodeFqn
   * @param mobicentsCluster
   * @param iface
   */
  public ClientAuthSessionDataReplicatedImpl(Fqn<?> nodeFqn, MobicentsCluster mobicentsCluster) {
    super(nodeFqn, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ClientAuthSession.class);
      setClientAuthSessionState(ClientAuthSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param iface
   */
  public ClientAuthSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(Fqn.fromRelativeElements(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setClientAuthSessionState(org.jdiameter.common.api.app.auth.
   * ClientAuthSessionState)
   */
  @Override
  public void setClientAuthSessionState(ClientAuthSessionState state) {
    if (exists()) {
      getNode().put(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getClientAuthSessionState()
   */
  @Override
  public ClientAuthSessionState getClientAuthSessionState() {
    if (exists()) {
      return (ClientAuthSessionState) getNode().get(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#isStateless()
   */
  @Override
  public boolean isStateless() {
    if (exists()) {
      return (Boolean) getNode().get(STATELESS);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setStateless(boolean)
   */
  @Override
  public void setStateless(boolean b) {
    if (exists()) {
      getNode().put(STATELESS, b);
    }
    else {
      throw new IllegalStateException();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    if (exists()) {
      return (String) getNode().get(DESTINATION_HOST);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String host) {
    if (exists()) {
      getNode().put(DESTINATION_HOST, host);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    if (exists()) {
      return (String) getNode().get(DESTINATION_REALM);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String realm) {
    if (exists()) {
      getNode().put(DESTINATION_REALM, realm);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNode().get(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tid) {
    if (exists()) {
      getNode().put(TS_TIMERID, tid);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
