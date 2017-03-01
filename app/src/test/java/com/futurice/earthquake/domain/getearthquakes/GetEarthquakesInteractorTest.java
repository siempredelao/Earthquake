package com.futurice.earthquake.domain.getearthquakes;

import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;
import com.futurice.earthquake.data.repository.Callback;
import com.futurice.earthquake.data.repository.Repository;
import com.futurice.earthquake.domain.executor.Executor;
import com.futurice.earthquake.domain.executor.Interactor;
import com.futurice.earthquake.domain.executor.MainThread;
import com.futurice.earthquake.domain.getearthquakes.GetEarthquakesUseCase.GetEarthquakesCallback;
import com.futurice.earthquake.domain.mapper.DomainMapper;
import com.futurice.earthquake.domain.model.Earthquake;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetEarthquakesInteractorTest {

    @Mock
    private Executor     executor;
    @Mock
    private MainThread   mainThread;
    @Mock
    private Repository   repository;
    @Mock
    private DomainMapper domainMapper;
    @Mock
    GetEarthquakesCallback callback;

    GetEarthquakesInteractor getEarthquakesInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        getEarthquakesInteractor = new GetEarthquakesInteractor(executor, mainThread, repository, domainMapper);

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
    public void firesSuccessCallbackOnRepositorySuccess() {
        List<Earthquake> fakeEarthquakeList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<GetEarthquakesResponseEntity>) invocation.getArguments()[0]).onSuccess(any(
                        GetEarthquakesResponseEntity.class));
                return null;
            }
        }).when(repository).getEarthquakes(any(Callback.class));
        when(domainMapper.transform(any(GetEarthquakesResponseEntity.class))).thenReturn(fakeEarthquakeList);

        getEarthquakesInteractor.execute(callback);

        verify(callback).onLoad(fakeEarthquakeList);
    }

    @Test
    public void firesFailureCallbackOnRepositoryFailure() {
        final Throwable fakeThrowable = new Throwable();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<GetEarthquakesResponseEntity>) invocation.getArguments()[0]).onFailure(fakeThrowable);
                return null;
            }
        }).when(repository).getEarthquakes(any(Callback.class));

        getEarthquakesInteractor.execute(callback);

        verify(callback).onError(fakeThrowable);
    }
}