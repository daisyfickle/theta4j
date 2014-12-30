package com.theta360.ptp.packet;

import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_ACK;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InitCommandRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[GUID.SIZE + STR.MIN_SIZE + UINT32.SIZE];
    private static final GUID GUID_ = new GUID(
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    );
    private static final UINT32 PROTOCOL_VERSION = new UINT32(0x00, 0x01, 0x02, 0x03);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullGUID() {
        // act
        new InitCommandRequestPacket(null, "", PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullName() {
        // act
        new InitCommandRequestPacket(GUID_, null, PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullProtocolVersion() {
        // act
        new InitCommandRequestPacket(GUID_, "", null);
    }

    @Test
    public void constructAndGetWithEmptyName() {
        // given
        String givenName = "";

        // expected
        byte[] expectedPayload = ByteUtils.join(GUID_.bytes(), STR.toBytes(givenName), PROTOCOL_VERSION.bytes());

        // arrange
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, givenName, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(givenName));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // given
        String givenName = "test";

        // expected
        byte[] expectedPayload = ByteUtils.join(GUID_.bytes(), STR.toBytes(givenName), PROTOCOL_VERSION.bytes());

        // arrange
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, givenName, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(givenName));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        InitCommandRequestPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_ACK;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);

        // act
        InitCommandRequestPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfTooShortPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_REQUEST, givenPayload);

        // act
        InitCommandRequestPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidName() throws PacketException {
        // given
        byte[] invalidNameBytes = new byte[]{0x01};  // Not end with 0x00
        byte[] givenPayload = ByteUtils.join(GUID_.bytes(), invalidNameBytes, PROTOCOL_VERSION.bytes());

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_REQUEST, givenPayload);

        // act
        InitCommandRequestPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        String givenName = "test";
        byte[] givenPayload = ByteUtils.join(GUID_.bytes(), STR.toBytes(givenName), PROTOCOL_VERSION.bytes());

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_REQUEST, givenPayload);

        // act
        InitCommandRequestPacket actual = InitCommandRequestPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(actual.getGUID(), is(GUID_));
        assertThat(actual.getName(), is(givenName));
        assertThat(actual.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
