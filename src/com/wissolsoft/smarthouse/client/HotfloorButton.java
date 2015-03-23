package com.wissolsoft.smarthouse.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HotfloorButton extends PushButton {

    public interface HotfloorValueChangedListener {
        public void onHotfloorValueChanged(short value);
    }

    private short value = 42; //Celsius degrees
    private final Label valueLabel = new Label(String.valueOf(value) + "°");

    private List<HotfloorValueChangedListener> listeners = new ArrayList<HotfloorValueChangedListener>();

    public HotfloorButton(String name) {
        super(new Image("images/hotfloor.png"));
        valueLabel.addStyleName("hotfloor-dialog-label");

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText(name);
        dialogBox.setAnimationEnabled(true);
        dialogBox.setGlassStyleName("glass");
        dialogBox.setGlassEnabled(true);
        dialogBox.setAutoHideEnabled(true);

        final Button closeButton = new Button("Set");
        closeButton.addStyleName("closeButton");
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                notifyAllListeners(value);
                dialogBox.hide();
            }
        });

        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Adjust hot floor temperature</b>"));
        dialogVPanel.add(new PushButton("Up!", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setValue((short)(value + 1));
            }
        }));
        dialogVPanel.add(valueLabel);
        dialogVPanel.add(new PushButton("Down!", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setValue((short)(value - 1));
            }
        }));
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        addStyleName("lightsButton");
        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //dialogBox.center();
                dialogBox.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop());;
                dialogBox.show();
                closeButton.setFocus(true);
            }
        });
    }

    public void setValue(short value) {
        this.value = value;
        valueLabel.setText(String.valueOf(value) + "°");
    }

    public void addHotfloorValueChangedListener(final HotfloorValueChangedListener l) {
        listeners.add(l);
    }

    private void notifyAllListeners(short value) {
        for (HotfloorValueChangedListener l : listeners) {
            l.onHotfloorValueChanged(value);
        }
    }

}
