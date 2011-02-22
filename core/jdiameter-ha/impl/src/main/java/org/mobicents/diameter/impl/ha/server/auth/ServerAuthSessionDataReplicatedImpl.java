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
package org.mobicents.diameter.impl.ha.server.auth;

import java.io.Serializable;

import org.jboss.cache.Fqn;
import org.jdiameter.api.auth.ServerAuthSession;
import org.jdiameter.common.api.app.auth.ServerAuthSessionState;
import org.jdiameter.server.impl.app.auth.IServerAuthSessionData;
import org.mobicents.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerAuthSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IServerAuthSessionData {

  private static final String STATELESS = "STATELESS";
  private static final String STATE = "STATE";
  private static final String TS_TIMEOUT = "TS_TIMEOUT";
  private static final String TS_TIMERID = "TS_TIMERID";

  /**
   * @param nodeFqn
   * @param mobicentsCluster
   * @param iface
   */
  public ServerAuthSessionDataReplicatedImpl(Fqn<?> nodeFqn, MobicentsCluster mobicentsCluster) {
    super(nodeFqn, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerAuthSession.class);
      setServerAuthSessionState(ServerAuthSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param iface
   */
  public ServerAuthSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(Fqn.fromRelativeElements(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#isStateless()
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
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#setStateless( boolean)
   */
  @Override
  public void setStateless(boolean stateless) {
    if (exists()) {
      getNode().put(STATELESS, stateless);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData# getServerCCASessionState()
   */
  @Override
  public ServerAuthSessionState getServerAuthSessionState() {
    if (exists()) {
      return (ServerAuthSessionState) getNode().get(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData# setServerCCASessionState
   * (org.jdiameter.common.api.app.cca.ServerCCASessionState)
   */
  @Override
  public void setServerAuthSessionState(ServerAuthSessionState state) {
    if (exists()) {
      getNode().put(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setTsTimeout(long value) {
    if (exists()) {
      getNode().put(TS_TIMEOUT, value);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public long getTsTimeout() {
    if (exists()) {
      return (Long) getNode().get(TS_TIMEOUT);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setTsTimerId(Serializable value) {
    if (exists()) {
      getNode().put(TS_TIMERID, value);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNode().get(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
