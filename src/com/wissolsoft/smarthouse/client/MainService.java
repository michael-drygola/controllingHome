package com.wissolsoft.smarthouse.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wissolsoft.smarthouse.shared.LightsLocation;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface MainService extends RemoteService {
    String greetServer(String name) throws IllegalArgumentException;

    Short setLight(LightsLocation location, short value) throws Exception;
}
