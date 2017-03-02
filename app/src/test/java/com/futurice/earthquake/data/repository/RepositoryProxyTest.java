package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryProxyTest {

    @Mock
    MemoryDataSource                       memoryDataSource;
    @Mock
    NetworkDataSource                      networkDataSource;

    RepositoryProxy repositoryProxy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repositoryProxy = new RepositoryProxy(memoryDataSource, networkDataSource);
    }

    @Test
    public void returnsCachedDataWhenCacheIsValid() {
        Callback<GetEarthquakesResponseEntity> callback = mock(Callback.class);
        GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().build();
        when(memoryDataSource.isDirty()).thenReturn(false);
        when(memoryDataSource.getEarthquakes()).thenReturn(getEarthquakesResponseEntity);

        repositoryProxy.getEarthquakes(callback);

        verify(callback).onSuccess(getEarthquakesResponseEntity);
    }

    @Test
    public void returnsNetworkDataWhenCacheIsDirty() {
        Callback<GetEarthquakesResponseEntity> callback = mock(Callback.class);
        when(memoryDataSource.isDirty()).thenReturn(true);

        repositoryProxy.getEarthquakes(callback);

        verify(networkDataSource).getEarthquakes(any(Callback.class));
    }

    @Test
    public void savesNetworkDataIntoCacheOnNetworkSuccess() {
        Callback<GetEarthquakesResponseEntity> callback = mock(Callback.class);
        final GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().build();
        when(memoryDataSource.isDirty()).thenReturn(true);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<GetEarthquakesResponseEntity>) invocation.getArguments()[0]).onSuccess(
                        getEarthquakesResponseEntity);
                return null;
            }
        }).when(networkDataSource).getEarthquakes(any(Callback.class));

        repositoryProxy.getEarthquakes(callback);

        verify(memoryDataSource).persist(getEarthquakesResponseEntity);
        // This should be in a separate test
        verify(callback).onSuccess(getEarthquakesResponseEntity);
    }

    @Test
    public void returnsFailureOnNetworkFailure() {
        Callback<GetEarthquakesResponseEntity> callback = mock(Callback.class);
        final Throwable fakeThrowable = new Throwable();
        when(memoryDataSource.isDirty()).thenReturn(true);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<GetEarthquakesResponseEntity>) invocation.getArguments()[0]).onFailure(fakeThrowable);
                return null;
            }
        }).when(networkDataSource).getEarthquakes(any(Callback.class));

        repositoryProxy.getEarthquakes(callback);

        verify(callback).onFailure(fakeThrowable);
    }

    @Test
    public void takesCachedEarthquakeWhenCacheIsValid() {
        Callback<FeatureEntity> callback = mock(Callback.class);
        String fakeId = "1234abcd";
        FeatureEntity featureEntity = new FeatureEntity.Builder().build();
        when(memoryDataSource.isDirty()).thenReturn(false);
        when(memoryDataSource.getEarthquakeById(fakeId)).thenReturn(featureEntity);

        repositoryProxy.getEarthquakeById(fakeId, callback);

        verify(callback).onSuccess(featureEntity);
    }
}