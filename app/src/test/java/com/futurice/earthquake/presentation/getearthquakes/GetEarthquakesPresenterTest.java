package com.futurice.earthquake.presentation.getearthquakes;

import com.futurice.earthquake.domain.getearthquakes.GetEarthquakesInteractor;
import com.futurice.earthquake.domain.getearthquakes.GetEarthquakesUseCase.GetEarthquakesCallback;
import com.futurice.earthquake.domain.model.Earthquake;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class GetEarthquakesPresenterTest {

    @Mock
    GetEarthquakesMVP.View   view;
    @Mock
    GetEarthquakesInteractor getEarthquakesInteractor;

    GetEarthquakesPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new GetEarthquakesPresenter(getEarthquakesInteractor);
        presenter.setView(view);
    }

    @Test
    public void startsProgressOnPresenterStart() {
        presenter.start();

        verify(view).showLoading();
    }

    @Test
    public void stopsProgressAndShowsListOnSuccess() {
        final List<Earthquake> earthquakeList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakesCallback) invocation.getArguments()[0]).onLoad(earthquakeList);
                return null;
            }
        }).when(getEarthquakesInteractor).execute(any(GetEarthquakesCallback.class));

        presenter.start();

        // Being pragmatic, we should only have one verification per test,
        // so there should only be one reason to fail per test.
        // Grouping both instructions in this test just to avoid having a lot of tests (2x in this case)
        // for the purpose of this coding challenge
        verify(view).hideLoading();
        verify(view).showList(earthquakeList);
    }

    @Test
    public void stopsProgressOnNoInternetError() {
        final UnknownHostException unknownHostException = new UnknownHostException();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakesCallback) invocation.getArguments()[0]).onError(unknownHostException);
                return null;
            }
        }).when(getEarthquakesInteractor).execute(any(GetEarthquakesCallback.class));

        presenter.start();

        verify(view).hideLoading();
        verify(view).showNoInternetError();
    }

    @Test
    public void stopsProgressOnSlowInternetError() {
        final SocketTimeoutException socketTimeoutException = new SocketTimeoutException();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakesCallback) invocation.getArguments()[0]).onError(socketTimeoutException);
                return null;
            }
        }).when(getEarthquakesInteractor).execute(any(GetEarthquakesCallback.class));

        presenter.start();

        verify(view).hideLoading();
        verify(view).showSlowInternetError();
    }

    @Test
    public void stopsProgressOnOtherError() {
        final Throwable anyThrowable = new Throwable();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakesCallback) invocation.getArguments()[0]).onError(anyThrowable);
                return null;
            }
        }).when(getEarthquakesInteractor).execute(any(GetEarthquakesCallback.class));

        presenter.start();

        verify(view).hideLoading();
        verify(view).showGenericError();
    }

    @Test
    public void releaseReferencesOnStop() {
        presenter.stop();

        verify(getEarthquakesInteractor).release();
    }
}