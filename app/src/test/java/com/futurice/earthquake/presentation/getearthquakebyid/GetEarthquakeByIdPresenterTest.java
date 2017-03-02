package com.futurice.earthquake.presentation.getearthquakebyid;

import com.futurice.earthquake.domain.getearthquakebyid.GetEarthquakeByIdInteractor;
import com.futurice.earthquake.domain.getearthquakebyid.GetEarthquakeByIdUseCase.GetEarthquakeByIdCallback;
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

public class GetEarthquakeByIdPresenterTest {

    @Mock
    GetEarthquakeByIdMVP.View   view;
    @Mock
    GetEarthquakeByIdInteractor getEarthquakeByIdInteractor;

    GetEarthquakeByIdPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new GetEarthquakeByIdPresenter(getEarthquakeByIdInteractor);
        presenter.setView(view);
    }

    @Test
    public void startsProgressOnPresenterStart() {
        String fakeEarthquakeId = "1234abcd";

        presenter.start(fakeEarthquakeId);

        verify(view).showLoading();
    }

    @Test
    public void stopsProgressAndShowsEarthquakeOnLoad() {
        String fakeEarthquakeId = "1234abcd";
        final Earthquake earthquake = new Earthquake.Builder().build();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakeByIdCallback) invocation.getArguments()[1]).onLoad(earthquake);
                return null;
            }
        }).when(getEarthquakeByIdInteractor).execute(eq(fakeEarthquakeId), any(GetEarthquakeByIdCallback.class));

        presenter.start(fakeEarthquakeId);

        // Being pragmatic, we should only have one verification per test,
        // so there should only be one reason to fail per test.
        // Grouping both instructions in this test just to avoid having a lot of tests (2x in this case)
        // for the purpose of this coding challenge
        verify(view).hideLoading();
        verify(view).showEarthquake(earthquake);
    }

    @Test
    public void stopsProgressAndShowsEarthquakeNotFoundErrorOnNotFound() {
        String fakeEarthquakeId = "1234abcd";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakeByIdCallback) invocation.getArguments()[1]).onEarthquakeNotFoundError();
                return null;
            }
        }).when(getEarthquakeByIdInteractor).execute(eq(fakeEarthquakeId), any(GetEarthquakeByIdCallback.class));

        presenter.start(fakeEarthquakeId);

        verify(view).hideLoading();
        verify(view).showEarthquakeNotFoundError();
    }

    @Test
    public void stopsProgressAndShowsErrorOnError() {
        String fakeEarthquakeId = "1234abcd";
        final Throwable throwable = new Throwable();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetEarthquakeByIdCallback) invocation.getArguments()[1]).onError(throwable);
                return null;
            }
        }).when(getEarthquakeByIdInteractor).execute(eq(fakeEarthquakeId), any(GetEarthquakeByIdCallback.class));

        presenter.start(fakeEarthquakeId);

        verify(view).hideLoading();
        verify(view).showGenericError();
    }

    @Test
    public void releaseReferencesOnStop() {
        presenter.stop();

        verify(getEarthquakeByIdInteractor).release();
    }

}