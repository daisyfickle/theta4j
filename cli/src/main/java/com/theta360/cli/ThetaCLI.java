package com.theta360.cli;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpIpInitiator;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.Theta;
import com.theta360.theta.ThetaEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public final class ThetaCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaCLI.class);

    public static final UINT32 SESSION_ID = new UINT32(1);

    private ThetaCLI() {
    }

    private static PtpEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onDevicePropChanged(UINT32 devicePropCode) {
            LOGGER.info("onDevicePropChanged: " + devicePropCode);
        }

        @Override
        public void onStoreFull(UINT32 storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(UINT32 transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    public static void main(String[] args) throws IOException {
        PtpIpInitiator initiator = new PtpIpInitiator(new GUID(UUID.randomUUID()), Theta.IP_ADDRESS, Theta.TCP_PORT);
        initiator.addListener(listener);
        initiator.getDeviceInfo();
        initiator.openSession(SESSION_ID);
        initiator.initiateCapture();
        initiator.closeSession();
        initiator.close();
    }
}