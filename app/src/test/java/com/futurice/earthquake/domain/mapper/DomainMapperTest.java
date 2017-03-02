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
    public void listHasSameSizeAfterMapping() {
        ArrayList<FeatureEntity> featureList = new ArrayList<>();
        FeatureEntity featureEntity = getFeatureEntity();
        featureList.add(featureEntity);
        GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().withFeatures(
                featureList).build();

        List<Earthquake> transformation = domainMapper.transform(getEarthquakesResponseEntity);

        assertEquals(featureList.size(), transformation.size());
    }

    @Test
    public void appliesTransformation() {
        FeatureEntity featureEntity = getFeatureEntity();

        Earthquake transformation = domainMapper.transform(featureEntity);

        assertEquals(featureEntity.getId(), transformation.getId());
        assertEquals(featureEntity.getGeometry().getLatitude(), transformation.getLatitude());
        assertEquals(featureEntity.getGeometry().getLongitude(), transformation.getLongitude());
        assertEquals(featureEntity.getGeometry().getDepth(), transformation.getDepth());
        assertEquals(featureEntity.getProperties().getMagnitude(), transformation.getMagnitude());
        assertEquals(featureEntity.getProperties().getMagnitudeType(), transformation.getMagnitudeType());
        assertEquals(featureEntity.getProperties().getPlace(), transformation.getPlace());
        assertEquals(featureEntity.getProperties().getUrl(), transformation.getUrl());
        assertEquals(featureEntity.getProperties().getAlert(), transformation.getAlert());
    }

    private FeatureEntity getFeatureEntity() {
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
        return new FeatureEntity.Builder().withId(fakeId)
                                          .withGeometry(fakeGeometry)
                                          .withProperties(fakeProperties)
                                          .build();
    }
}