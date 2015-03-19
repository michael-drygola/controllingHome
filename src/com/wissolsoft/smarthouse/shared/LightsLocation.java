package com.wissolsoft.smarthouse.shared;

public enum LightsLocation {
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
