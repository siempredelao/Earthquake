package com.futurice.earthquake;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.futurice.earthquake.domain.model.Earthquake;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.futurice.earthquake.EarthquakeViewModel.GREEN_ALERT;
import static junit.framework.Assert.assertEquals;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class EarthquakeViewModelTest {

    Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application.getApplicationContext();
    }

    @Test
    public void getPlace() {
        String fakePlace = "wonderland";
        Earthquake earthquake = new Earthquake.Builder().withPlace(fakePlace).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String place = earthquakeViewModel.getPlace();

        assertEquals(fakePlace, place);
    }

    @Test
    public void returnsMagnitudeNotAvailableWhenMagnitudeIsNotPresent() {
        Earthquake earthquake = new Earthquake.Builder().build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String magnitude = earthquakeViewModel.getMagnitude();

        assertEquals(context.getString(R.string.earthquake_magnitude_not_available_message), magnitude);
    }

    @Test
    public void returnsMagnitudeNotAvailableWhenMagnitudeTypeIsNotPresent() {
        float fakeMagnitude = 1F;
        Earthquake earthquake = new Earthquake.Builder().withMagnitude(fakeMagnitude).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String magnitude = earthquakeViewModel.getMagnitude();

        assertEquals(context.getString(R.string.earthquake_magnitude_not_available_message), magnitude);
    }

    @Test
    public void returnsMagnitudeWhenMagnitudeAndTypeArePresent() {
        float fakeMagnitude = 1F;
        String fakeMagnitudeType = "mb";
        Earthquake earthquake = new Earthquake.Builder().withMagnitude(fakeMagnitude)
                                                        .withMagnitudeType(fakeMagnitudeType)
                                                        .build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String magnitude = earthquakeViewModel.getMagnitude();

        assertEquals(context.getString(R.string.earthquake_magnitude_message, fakeMagnitude, fakeMagnitudeType),
                     magnitude);
    }

    @Test
    public void getLocation() {
        float latitude = 1.8F;
        float longitude = 2F;
        Earthquake earthquake = new Earthquake.Builder().withLatitude(latitude).withLongitude(longitude).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String location = earthquakeViewModel.getLocation();

        assertEquals(context.getString(R.string.earthquake_location_message, latitude, longitude), location);
    }

    @Test
    public void getDepth() {
        float depth = 3.4F;
        Earthquake earthquake = new Earthquake.Builder().withDepth(depth).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        String depth1 = earthquakeViewModel.getDepth();

        assertEquals(context.getString(R.string.earthquake_depth_message, depth), depth1);
    }

    @Test
    public void returnsNoAlertStringIdWhenNoAlertSpecified() {
        Earthquake earthquake = new Earthquake.Builder().build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        int alertStringId = earthquakeViewModel.getAlert();

        assertEquals(R.string.alert_none_message, alertStringId);
    }

    @Test
    public void returnsSpecificAlertStringIdWhenAlertIsSpecified() {
        Earthquake earthquake = new Earthquake.Builder().withAlert(GREEN_ALERT).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        int alertStringId = earthquakeViewModel.getAlert();

        assertEquals(R.string.alert_green_message, alertStringId);
    }

    @Test
    public void returnsNoAlertColorWhenNoAlertSpecified() {
        Earthquake earthquake = new Earthquake.Builder().build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        int alertColorId = earthquakeViewModel.getAlertColor();

        assertEquals(ContextCompat.getColor(context, android.R.color.transparent), alertColorId);
    }

    @Test
    public void returnsSpecificAlertColorWhenAlertIsSpecified() {
        Earthquake earthquake = new Earthquake.Builder().withAlert(GREEN_ALERT).build();
        EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, context);

        int alertColorId = earthquakeViewModel.getAlertColor();

        assertEquals(ContextCompat.getColor(context, R.color.alert_green), alertColorId);
    }

}