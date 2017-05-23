package com.nanyou.framework.security;

public class ClientSessionHolder {

	static ThreadLocal clientSessionList = new ThreadLocal();

	public static void setClientSession(ClientSession clientSession) {
		clientSessionList.set(clientSession);
	}

	public static ClientSession getClientSession() {
		return (ClientSession) clientSessionList.get();
	}

}
