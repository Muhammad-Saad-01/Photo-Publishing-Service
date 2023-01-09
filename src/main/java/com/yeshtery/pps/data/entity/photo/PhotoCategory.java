package com.yeshtery.pps.data.entity.photo;

import java.util.HashMap;
import java.util.Map;

public enum PhotoCategory {
    LIVING_THING("Living Thing","This category refers to any organism that is alive and exhibits characteristics such as growth, reproduction, and response to stimuli. Examples of living things include animals, plants, and fungi."),
    MACHINE("Machine","This category refers to any device or system that is created or programmed to perform a specific function. Examples of machines include computers, vehicles, and appliances."),
    NATURE("Nature","This category refers to the natural world and all its phenomena, including the physical, chemical, and biological processes that make up the environment. Examples of nature include landscapes, weather patterns, and natural disasters.");

    private final String label;
    private final String description;

    PhotoCategory(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public static final Map<String, PhotoCategory> LABELS = new HashMap<>();

    static {
        for (PhotoCategory e: values()) {
            LABELS.put(e.label, e);
        }
    }

    public static PhotoCategory valueOfLabel(String label) {
        return LABELS.get(label);
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
