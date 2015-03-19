package com.wissolsoft.smarthouse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wissolsoft.smarthouse.shared.LightsLocation;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MainServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;

    void setLight(LightsLocation location, short value, AsyncCallback<Short> callback);
}
