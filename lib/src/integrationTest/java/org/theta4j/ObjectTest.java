/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.theta4j.ptp.type.UINT32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class ObjectTest extends BaseThetaTest {
    private static UINT32 objectHandle;

    private File tempFile;

    @BeforeClass
    public static void capture() throws IOException, InterruptedException {
        objectHandle = theta.initiateCapture();
    }

    @AfterClass
    public static void delete() throws IOException, InterruptedException {
        if (theta != null && objectHandle != null) {
            theta.deleteObject(objectHandle);
        }
    }

    @Before
    public void createTempFile() throws IOException {
        tempFile = File.createTempFile("theta4j-", ".jpg");
    }

    @Test
    public void getObjectInfo() throws IOException {
        theta.getObjectInfo(objectHandle);
    }

    @Test
    public void getObjectHandles() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        assertThat(objectHandles, hasItems(objectHandle));
    }

    @Test
    public void getObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(tempFile)) {
            theta.getObject(objectHandles.get(2), file);
            TestUtils.isValidJPEG(tempFile);
        }
    }

    @Test
    public void getThumb() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(tempFile)) {
            theta.getThumb(objectHandles.get(2), file);
            TestUtils.isValidJPEG(tempFile);
        }
    }

    @Test
    public void getResizedImageObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(tempFile)) {
            theta.getResizedImageObject(objectHandles.get(2), file);
            TestUtils.isValidJPEG(tempFile);
        }
    }
}
