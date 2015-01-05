package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class InitEventRequestPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE;

    private final UINT32 connectionNumber;

    public InitEventRequestPacket(UINT32 connectionNumber) {
        super(Type.INIT_EVENT_REQUEST);

        Validators.validateNonNull("connectionNumber", connectionNumber);

        this.connectionNumber = connectionNumber;
        super.payload = connectionNumber.bytes();
    }

    public UINT32 getConnectionNumber() {
        return connectionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitEventRequestPacket that = (InitEventRequestPacket) o;

        if (!connectionNumber.equals(that.connectionNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return connectionNumber.hashCode();
    }

    @Override
    public String toString() {
        return "InitEventRequestPacket{" +
                "connectionNumber=" + connectionNumber +
                '}';
    }

    public static InitEventRequestPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.INIT_EVENT_REQUEST, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Session ID
        byte[] connectionNumberBytes = new byte[UINT32.SIZE];
        buffer.get(connectionNumberBytes);
        UINT32 connectionNumber = new UINT32(connectionNumberBytes);

        return new InitEventRequestPacket(connectionNumber);
    }
}