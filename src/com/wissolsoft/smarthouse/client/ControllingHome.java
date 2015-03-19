package com.wissolsoft.smarthouse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;
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

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Send");
        // We can add style names to widgets
        sendButton.addStyleName("sendButton");
        final Label errorLabel = new Label();


        final LightsButton lightsButtonMainRoom = new LightsButton();
        lightsButtonMainRoom.AddLightesValueChangedListener(new LightsValueChangedListener() {
			@Override
			public void onLightsValueChanged(short value) {
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
		});


        RootPanel.get(ROOM_MAIN).add(lightsButtonMainRoom);

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

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
