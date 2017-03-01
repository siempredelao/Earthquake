package com.futurice.earthquake.domain.mapper;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GeometryEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;
import com.futurice.earthquake.data.model.PropertiesEntity;
import com.futurice.earthquake.domain.model.Earthquake;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class DomainMapper {

    @Inject
    public DomainMapper() {
    }

    public List<Earthquake> transform(final GetEarthquakesResponseEntity getEarthquakesResponseEntity) {
        final List<Earthquake> earthquakeList = new ArrayList<>();
        for (final FeatureEntity featureEntity : getEarthquakesResponseEntity.getFeatures()) {
            final GeometryEntity geometry = featureEntity.getGeometry();
            final PropertiesEntity properties = featureEntity.getProperties();

            final Earthquake earthquake = new Earthquake.Builder().withId(featureEntity.getId())
                                                                  .withLatitude(geometry.getLatitude())
                                                                  .withLongitude(geometry.getLongitude())
                                                                  .withDepth(geometry.getDepth())
                                                                  .withTimestamp(properties.getTime())
                                                                  .withMagnitude(properties.getMagnitude())
                                                                  .withMagnitudeType(properties.getMagnitudeType())
                                                                  .withPlace(properties.getPlace())
                                                                  .withUrl(properties.getUrl())
                                                                  .withAlert(properties.getAlert())
                                                                  .build();
            earthquakeList.add(earthquake);
        }
        return earthquakeList;
    }
}
