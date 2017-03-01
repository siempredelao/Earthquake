package com.futurice.earthquake.domain.mapper;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GeometryEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;
import com.futurice.earthquake.data.model.PropertiesEntity;
import com.futurice.earthquake.domain.model.Earthquake;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DomainMapperTest {

    DomainMapper domainMapper;

    @Before
    public void setUp() {
        domainMapper = new DomainMapper();
    }

    @Test
    public void earthquakeListIsEmtpyWhenNoFeaturesAvailable() {
        ArrayList<FeatureEntity> emptyFeatures = new ArrayList<>();
        GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().withFeatures(
                emptyFeatures).build();

        List<Earthquake> transformation = domainMapper.transform(getEarthquakesResponseEntity);

        assertEquals(emptyFeatures.size(), transformation.size());
    }

    @Test
    public void appliesTransformation() {
        ArrayList<FeatureEntity> featureList = new ArrayList<>();
        String fakeId = "abcd1234";
        List<Float> coordinates = Arrays.asList(1F, 2F, 3F);
        GeometryEntity fakeGeometry = new GeometryEntity.Builder().withCoordinates(coordinates).build();
        long fakeTime = 1L;
        float fakeMagnitude = 2F;
        String fakeMagnitudeType = "fakeMag";
        String fakePlace = "wonderland";
        String fakeUrl = "http://futurice.test.de";
        String fakeAlert = "kernel panic";
        PropertiesEntity fakeProperties = new PropertiesEntity.Builder().withTime(fakeTime)
                                                                        .withMag(fakeMagnitude)
                                                                        .withMagType(fakeMagnitudeType)
                                                                        .withPlace(fakePlace)
                                                                        .withUrl(fakeUrl)
                                                                        .withAlert(fakeAlert)
                                                                        .build();
        FeatureEntity featureEntity = new FeatureEntity.Builder().withId(fakeId)
                                                                 .withGeometry(fakeGeometry)
                                                                 .withProperties(fakeProperties)
                                                                 .build();
        featureList.add(featureEntity);
        GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().withFeatures(
                featureList).build();

        List<Earthquake> transformation = domainMapper.transform(getEarthquakesResponseEntity);

        assertEquals(featureList.size(), transformation.size());
        final Earthquake earthquake = transformation.get(0);
        assertEquals(featureEntity.getId(), earthquake.getId());
        assertEquals(featureEntity.getGeometry().getLatitude(), earthquake.getLatitude());
        assertEquals(featureEntity.getGeometry().getLongitude(), earthquake.getLongitude());
        assertEquals(featureEntity.getGeometry().getDepth(), earthquake.getDepth());
        assertEquals(featureEntity.getProperties().getMagnitude(), earthquake.getMagnitude());
        assertEquals(featureEntity.getProperties().getMagnitudeType(), earthquake.getMagnitudeType());
        assertEquals(featureEntity.getProperties().getPlace(), earthquake.getPlace());
        assertEquals(featureEntity.getProperties().getUrl(), earthquake.getUrl());
        assertEquals(featureEntity.getProperties().getAlert(), earthquake.getAlert());
    }
}