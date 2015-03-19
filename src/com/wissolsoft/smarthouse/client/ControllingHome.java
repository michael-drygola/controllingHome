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

        final SliderBarSimpleHorizontal slider = new SliderBarSimpleHorizontal(100, "300px", true);
        slider.setStylePrimaryName("slider");

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        dialogBox.setGlassStyleName("glass");
        dialogBox.setGlassEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Adjust lights in the main room</b><br>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(slider);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });


        final ToggleButton lightsButton = new ToggleButton(new Image("images/lights_off.png"), new Image("images/lights_on.png"));
        final Timer lightsButtonPressedTimer = new Timer() {
			@Override
			public void run() {
                dialogBox.setText("Loghts button hold");
                serverResponseLabel.setHTML("OLOLO");
                dialogBox.center();
                dialogBox.setPopupPosition(lightsButton.getAbsoluteLeft(), lightsButton.getAbsoluteTop());;
                dialogBox.show();
                closeButton.setFocus(true);
			}
		};
        lightsButton.addStyleName("lightsButton");
        lightsButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				short lightValue = event.getValue() ? (short)1023 : 0;
				service.setLight(LightsLocation.LIVING_ROOM_MAIN, lightValue, null);
				//FIXME this should be in callback
				processLightsChanged(ROOM_MAIN, lightValue);
			}
		});
        lightsButton.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				lightsButtonPressedTimer.schedule(1000);
			}
		});
        lightsButton.addTouchStartHandler(new TouchStartHandler() {
			@Override
			public void onTouchStart(TouchStartEvent event) {
				lightsButtonPressedTimer.schedule(1000);
			}
		});
        lightsButton.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				lightsButtonPressedTimer.cancel();
			}
		});
        lightsButton.addTouchEndHandler(new TouchEndHandler() {
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				lightsButtonPressedTimer.cancel();
			}
		});
        RootPanel.get(ROOM_MAIN).add(lightsButton);

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        slider.addBarValueChangedHandler(new BarValueChangedHandler() {
            @Override
            public void onBarValueChanged(BarValueChangedEvent event) {
                service.setLight(LightsLocation.LIVING_ROOM_MAIN, (short)event.getValue(), new AsyncCallback<Short>() {
                    @Override
                    public void onSuccess(Short result) {
                    	errorLabel.setText(result.toString());
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                    	errorLabel.setText(caught.toString());
                    }
                });
            }
        });
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
