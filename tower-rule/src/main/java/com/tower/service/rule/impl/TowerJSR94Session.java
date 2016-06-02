package com.tower.service.rule.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.rules.Handle;
import javax.rules.InvalidHandleException;
import javax.rules.InvalidRuleSessionException;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.StatefulRuleSession;

import com.tower.service.rule.ISession;

public class TowerJSR94Session implements ISession,StatefulRuleSession {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1622962438476239211L;
	private StatefulRuleSession delegate;
	public TowerJSR94Session(StatefulRuleSession delegate){
		if(delegate==null){
			throw new RuntimeException("StatefulRuleSession 不能为空！");
		}
		this.delegate = delegate;
	}
	@Override
	public RuleExecutionSetMetadata getRuleExecutionSetMetadata()
			throws InvalidRuleSessionException, RemoteException {
		// TODO Auto-generated method stub
		return delegate.getRuleExecutionSetMetadata();
	}
	@Override
	public int getType() throws RemoteException, InvalidRuleSessionException {
		return delegate.getType();
	}
	@Override
	public void release() throws RemoteException, InvalidRuleSessionException {
		delegate.release();
	}
	@Override
	public Handle addObject(Object arg0) throws RemoteException,
			InvalidRuleSessionException {
		return delegate.addObject(arg0);
	}
	@Override
	public List addObjects(List arg0) throws RemoteException,
			InvalidRuleSessionException {
		return delegate.addObjects(arg0);
	}
	@Override
	public boolean containsObject(Handle arg0) throws RemoteException,
			InvalidRuleSessionException, InvalidHandleException {
		return delegate.containsObject(arg0);
	}
	@Override
	public void executeRules() throws RemoteException,
			InvalidRuleSessionException {
		delegate.executeRules();
	}
	@Override
	public List getHandles() throws RemoteException,
			InvalidRuleSessionException {
		return delegate.getHandles();
	}
	@Override
	public Object getObject(Handle arg0) throws RemoteException,
			InvalidHandleException, InvalidRuleSessionException {
		return delegate.getObject(arg0);
	}
	@Override
	public List getObjects() throws RemoteException,
			InvalidRuleSessionException {
		return delegate.getObjects();
	}
	@Override
	public List getObjects(ObjectFilter arg0) throws RemoteException,
			InvalidRuleSessionException {
		return delegate.getObjects(arg0);
	}
	@Override
	public void removeObject(Handle arg0) throws RemoteException,
			InvalidHandleException, InvalidRuleSessionException {
		delegate.removeObject(arg0);
	}
	@Override
	public void reset() throws RemoteException, InvalidRuleSessionException {
		delegate.reset();
	}
	@Override
	public void updateObject(Handle arg0, Object arg1) throws RemoteException,
			InvalidRuleSessionException, InvalidHandleException {
		delegate.updateObject(arg0, arg1);
	}
	
}
