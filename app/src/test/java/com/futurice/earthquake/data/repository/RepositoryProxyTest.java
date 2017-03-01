package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryProxyTest {

    @Mock
    MemoryDataSource                       memoryDataSource;
    @Mock
    NetworkDataSource                      networkDataSource;
    @Mock
    Callback<GetEarthquakesResponseEntity> callback;

    RepositoryProxy repositoryProxy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repositoryProxy = new RepositoryProxy(memoryDataSource, networkDataSource);
    }

    @Test
    public void returnsCachedDataWhenCacheIsValid() {
        GetEarthquakesResponseEntity getEarthquakesResponseEntity = new GetEarthquakesResponseEntity.Builder().build();
        when(memoryDataSource.isDirty()).thenReturn(false);
        when(memoryDataSource.getEarthquakes()).thenReturn(getEarthquakesResponseEntity);

        repositoryProxy.getEarthquakes(callback);

        verify(callback).onSuccess(getEarthquakesResponseEntity);
    }

    @Test
    public void returnsNetworkDataWhenCacheIsDirty() {
        when(memoryDataSource.isDirty()).thenReturn(true);

        repositoryProxy.getEarthquakes(callback);

        verify(networkDataSource).getEarthquakes(any(Callback.class));
    }

    @Test
    public void savesNetworkDataIntoCacheOnNetworkSuccess() {
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

}