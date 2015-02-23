package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.data.*;
import org.theta4j.ptp.type.UINT32;

import java.io.IOException;
import java.util.Date;

public class SetDevicePropTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetDevicePropTest.class);
    private static final UINT32 SESSION_ID = new UINT32(1);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onCaptureStatusChanged() {
            LOGGER.info("onCaptureStatusChanged");
        }

        @Override
        public void onRecordingTimeChanged() {
            LOGGER.info("onRecordingTimeChanged");
        }

        @Override
        public void onRemainingRecordingTimeChanged() {
            LOGGER.info("onRemainingRecordingTimeChanged");
        }

        @Override
        public void onStoreFull(long storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(long transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
        theta.addListener(listener);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Test

    @Test
    public void setWhiteBalance() throws IOException, ThetaException {
        theta.setWhiteBalance(WhiteBalance.COOL_WHITE_FLUORESCENT_LAMP);
    }

    @Test
    public void setExposureIndex() throws IOException, ThetaException {
        theta.setExposureIndex(ISOSpeed.ISO_100);
    }

    @Test
    public void setExposureBiasCompensation() throws IOException, ThetaException {
        theta.setExposureBiasCompensation(300);
    }

    @Test
    public void setDateTime() throws IOException, ThetaException {
        theta.setDateTime(new Date());
    }

    @Test
    public void setStillCaptureMode() throws IOException, ThetaException {
        theta.setStillCaptureMode(StillCaptureMode.SINGLE_SHOT);
    }

    @Test
    public void setTimelapseNumber() throws IOException, ThetaException {
        theta.setTimelapseNumber(0);
    }

    @Test
    public void setTimelapseInterval() throws IOException, ThetaException {
        theta.setTimelapseInterval(5000);
    }

    @Test
    public void setAudioVolume() throws IOException, ThetaException {
        theta.setAudioVolume(0);
    }

    @Test
    public void setShutterSpeed() throws IOException, ThetaException {
        theta.setShutterSpeed(ShutterSpeed.SS_1_10);
    }

    @Test
    public void setGPSInfo() throws IOException, ThetaException {
        theta.setGPSInfo("35.671190,139.764642+000.00m@19630103T000000+0900,WGS84");
    }

    @Test
    public void setAutoPowerOffDelay() throws IOException, ThetaException {
        theta.setAutoPowerOffDelay(0);
    }

    @Test
    public void setSleepDelay() throws IOException, ThetaException {
        theta.setSleepDelay(0);
    }

    @Test
    public void setChannelNumber() throws IOException, ThetaException {
        theta.setChannelNumber(ChannelNumber.RANDOM);
    }
}