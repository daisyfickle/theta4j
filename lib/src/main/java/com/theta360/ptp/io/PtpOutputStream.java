package com.theta360.ptp.io;

import com.theta360.ptp.type.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class PtpOutputStream extends OutputStream {
    private static Logger LOGGER = LoggerFactory.getLogger(PtpOutputStream.class);

    private final OutputStream os;

    public PtpOutputStream(OutputStream os) {
        Validators.validateNonNull("os", os);

        this.os = os;
    }

    // PTP Generic Type

    public void write(UINT16 uint16) throws IOException {
        write(uint16.bytes());
    }

    public void write(UINT32 uint32) throws IOException {
        write(uint32.bytes());
    }

    public void write(UINT64 uint64) throws IOException {
        write(uint64.bytes());
    }

    public void write(String str) throws IOException {
        write(STR.toBytes(str));
    }

    public void writePtpIpString(String str) throws IOException {
        write(PtpIpString.toBytes(str));
    }

    // OutputStream

    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        os.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        os.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}