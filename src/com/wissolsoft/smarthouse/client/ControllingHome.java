package com.wissolsoft.smarthouse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissolsoft.smarthouse.client.LightsButton.LightsValueChangedListener;
import com.wissolsoft.smarthouse.shared.LightsLocation;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ControllingHome implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final MainServiceAsync service = GWT.create(MainService.class);

    private static final String ROOM_MAIN = "livingroom";
    private static final String ROOM_KITCHEN = "kitchen";


    final Label errorLabel = new Label();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Send");
        // We can add style names to widgets
        sendButton.addStyleName("sendButton");


        final LightsButton lightsButtonMainRoom = new LightsButton();
        //TODO use this labda when GWT supports it:
        //lightsButtonMainRoom.addLightsValueChangedListener((value) -> {changeLights(LightsLocation.LIVING_ROOM_MAIN, value, ROOM_MAIN);} );
        lightsButtonMainRoom.addLightsValueChangedListener(new LightsValueChangedListener() {
            @Override
            public void onLightsValueChanged(short value) {
                changeLights(LightsLocation.LIVING_ROOM_MAIN, value, ROOM_MAIN);
            }
        });


        RootPanel.get(ROOM_MAIN).add(lightsButtonMainRoom);

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

    }

    private void changeLights(LightsLocation location, short value, String roomName) {
        service.setLight(LightsLocation.LIVING_ROOM_MAIN, value, new AsyncCallback<Short>() {
            @Override
            public void onSuccess(Short result) {
                errorLabel.setText(result.toString());
            }
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(caught.toString());
            }
        });
        //FIXME this should be in callback
        processLightsChanged(ROOM_MAIN, value);
    }

    private void processLightsChanged(String roomName, short value) {
        //TODO add support of many rooms
        if(value > 0) {
            RootPanel.get(roomName).addStyleName("room-shiny");
        } else {
            RootPanel.get(roomName).removeStyleName("room-shiny");
        }
    }
}
