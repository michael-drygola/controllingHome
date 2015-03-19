package com.wissolsoft.smarthouse.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wissolsoft.smarthouse.shared.LightsLocation;

public class HomeControllerImpl implements HomeController {

    private static final Log LOGGER = LogFactory.getLog(HomeControllerImpl.class);

    private static final String ARDUINO_URL = "http://192.168.8.177/";

    @Override
    public void setLights(LightsLocation lights, short value) throws Exception {
        // TODO Auto-generated method stub
    	String request = ARDUINO_URL + "lights/" + lights.getId() + "/" + value;
        LOGGER.debug(request);
        System.out.println(request);
        try {
			doRequest(request);
		} catch (Exception e) {
			LOGGER.error("Failed to set lights " + lights, e);
			throw new Exception(e);
		}
    }

    @Override
    public short getLights(LightsLocation lights) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getTemperature(ThermometerLocation thermometer) {
        // TODO Auto-generated method stub
        return 0;
    }

    private void doRequest(String url) throws MalformedURLException, IOException {
    	InputStream response = new URL(url).openStream();
    	BufferedReader br = new BufferedReader(new InputStreamReader(response));

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
        	System.out.println(inputLine);
        }
        br.close();
    }
}
