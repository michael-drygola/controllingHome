package com.wissolsoft.smarthouse.server;

public interface HomeController {

    enum LightsLocation {
        BATHROOM(1),
        KITCHEN(2),
        LIVING_ROOM_BACK(3),
        LIVING_ROOM_MAIN(4);

        int id;

        private LightsLocation(int id) {
			this.id = id;
		}

        public int getId() {
			return id;
		}
    }

    enum ThermometerLocation {
        OUTER(1),
        BATHROOM(2);

        int id;

        private ThermometerLocation(int id) {
			this.id = id;
		}

        public int getId() {
			return id;
		}
    }

    public void setLights(final LightsLocation lights, short value) throws Exception;

    public short getLights(final LightsLocation lights);

    public float getTemperature(final ThermometerLocation thermometer);

    //TODO doors
    //TODO other sensors
}
