package com.wissolsoft.smarthouse.client;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;

public class LightsButton extends ToggleButton {

	public interface LightsValueChangedListener {
		public void onLightsValueChanged(short value);
	}

	private List<LightsValueChangedListener> listeners = new ArrayList<LightsValueChangedListener>();

	public LightsButton() {
        super(new Image("images/lights_off.png"), new Image("images/lights_on.png"));

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


        slider.addBarValueChangedHandler(new BarValueChangedHandler() {
            @Override
            public void onBarValueChanged(BarValueChangedEvent event) {
            	notifyAllListeners((short)event.getValue());
            }
        });

        final Timer lightsButtonPressedTimer = new Timer() {
			@Override
			public void run() {
                dialogBox.setText("Loghts button hold");
                serverResponseLabel.setHTML("OLOLO");
                //dialogBox.center();
                dialogBox.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop());;
                dialogBox.show();
                closeButton.setFocus(true);
			}
		};
        addStyleName("lightsButton");
        addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				short lightValue = event.getValue() ? (short)1023 : 0;
				notifyAllListeners(lightValue);
			}
		});
        addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				lightsButtonPressedTimer.schedule(1000);
			}
		});
        addTouchStartHandler(new TouchStartHandler() {
			@Override
			public void onTouchStart(TouchStartEvent event) {
				lightsButtonPressedTimer.schedule(1000);
			}
		});
        addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				lightsButtonPressedTimer.cancel();
			}
		});
        addTouchEndHandler(new TouchEndHandler() {
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				lightsButtonPressedTimer.cancel();
			}
		});
	}

	public void addLightsValueChangedListener(final LightsValueChangedListener l) {
		listeners.add(l);
	}

	private void notifyAllListeners(short value) {
		for (LightsValueChangedListener l : listeners) {
			l.onLightsValueChanged(value);
		}
	}

}
