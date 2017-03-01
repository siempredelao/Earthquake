package com.futurice.earthquake.data.model;

import java.util.List;

public class GetEarthquakesResponseEntity {

    private String              type;
    private MetadataEntity      metadata;
    private List<FeatureEntity> features;
    private List<Float>         bbox;

}
