package com.futurice.earthquake.domain.getearthquakebyid;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.repository.Callback;
import com.futurice.earthquake.data.repository.Repository;
import com.futurice.earthquake.domain.executor.Executor;
import com.futurice.earthquake.domain.executor.Interactor;
import com.futurice.earthquake.domain.executor.MainThread;
import com.futurice.earthquake.domain.getearthquakebyid.GetEarthquakeByIdUseCase.GetEarthquakeByIdCallback;
import com.futurice.earthquake.domain.mapper.DomainMapper;
import com.futurice.earthquake.domain.model.Earthquake;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetEarthquakeByIdInteractorTest {

    @Mock
    Executor                  executor;
    @Mock
    MainThread                mainThread;
    @Mock
    Repository                repository;
    @Mock
    DomainMapper              domainMapper;
    @Mock
    GetEarthquakeByIdCallback callback;

    GetEarthquakeByIdInteractor getEarthquakeByIdInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        getEarthquakeByIdInteractor = new GetEarthquakeByIdInteractor(executor, mainThread, repository, domainMapper);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Interactor) invocation.getArguments()[0]).run();
                return null;
            }
        }).when(executor).run(any(Interactor.class));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable) invocation.getArguments()[0]).run();
                return null;
            }
        }).when(mainThread).post(any(Runnable.class));
    }

    @Test
    public void firesLoadCallbackWhenEarthquakeIsPresentOnRepositorySuccess() {
        String fakeId = "abcd1234";
        final FeatureEntity featureEntity = new FeatureEntity.Builder().build();
        final Earthquake fakeEarthquake = new Earthquake.Builder().build();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<FeatureEntity>) invocation.getArguments()[1]).onSuccess(featureEntity);
                return null;
            }
        }).when(repository).getEarthquakeById(eq(fakeId), any(Callback.class));
        when(domainMapper.transform(featureEntity)).thenReturn(fakeEarthquake);

        getEarthquakeByIdInteractor.execute(fakeId, callback);

        verify(callback).onLoad(fakeEarthquake);
    }

    @Test
    public void firesNotFoundCallbackWhenEarthquakeIsNotPresentOnRepositorySuccess() {
        String fakeId = "abcd1234";
        final FeatureEntity featureEntity = new FeatureEntity.Builder().build();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<FeatureEntity>) invocation.getArguments()[1]).onSuccess(featureEntity);
                return null;
            }
        }).when(repository).getEarthquakeById(eq(fakeId), any(Callback.class));
        when(domainMapper.transform(featureEntity)).thenReturn(null);

        getEarthquakeByIdInteractor.execute(fakeId, callback);

        verify(callback).onEarthquakeNotFoundError();
    }

    @Test
    public void firesFailureCallbackOnRepositoryFailure() {
        String fakeId = "abcd1234";
        final Throwable fakeThrowable = new Throwable();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<FeatureEntity>) invocation.getArguments()[1]).onFailure(fakeThrowable);
                return null;
            }
        }).when(repository).getEarthquakeById(eq(fakeId), any(Callback.class));

        getEarthquakeByIdInteractor.execute(fakeId, callback);

        verify(callback).onError(fakeThrowable);
    }

}