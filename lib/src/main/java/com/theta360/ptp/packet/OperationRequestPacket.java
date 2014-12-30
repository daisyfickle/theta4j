package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class OperationRequestPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE + UINT16.SIZE + UINT32.SIZE + UINT32.SIZE * 5;

    private final UINT32 dataPhaseInfo;
    private final UINT16 operationCode;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID) {
        this(dataPhaseInfo, operationCode, transactionID, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1) {
        this(dataPhaseInfo, operationCode, transactionID, p1, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, p4, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
        super(Type.OPERATION_REQUEST);

        Validators.validateNonNull("dataPhaseInfo", dataPhaseInfo);
        Validators.validateNonNull("operationCode", operationCode);
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("p1", p1);
        Validators.validateNonNull("p2", p2);
        Validators.validateNonNull("p3", p3);
        Validators.validateNonNull("p4", p4);
        Validators.validateNonNull("p5", p5);

        this.dataPhaseInfo = dataPhaseInfo;
        this.operationCode = operationCode;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;

        super.payload = ByteUtils.join(
                dataPhaseInfo.bytes(),
                operationCode.bytes(),
                transactionID.bytes(),
                p1.bytes(),
                p2.bytes(),
                p3.bytes(),
                p4.bytes(),
                p5.bytes()
        );
    }

    public UINT32 getDataPhaseInfo() {
        return dataPhaseInfo;
    }

    public UINT16 getOperationCode() {
        return operationCode;
    }

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT32 getP1() {
        return p1;
    }

    public UINT32 getP2() {
        return p2;
    }

    public UINT32 getP3() {
        return p3;
    }

    public UINT32 getP4() {
        return p4;
    }

    public UINT32 getP5() {
        return p5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationRequestPacket that = (OperationRequestPacket) o;

        if (!dataPhaseInfo.equals(that.dataPhaseInfo)) return false;
        if (!operationCode.equals(that.operationCode)) return false;
        if (!p1.equals(that.p1)) return false;
        if (!p2.equals(that.p2)) return false;
        if (!p3.equals(that.p3)) return false;
        if (!p4.equals(that.p4)) return false;
        if (!p5.equals(that.p5)) return false;
        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataPhaseInfo.hashCode();
        result = 31 * result + operationCode.hashCode();
        result = 31 * result + transactionID.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + p3.hashCode();
        result = 31 * result + p4.hashCode();
        result = 31 * result + p5.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OperationRequestPacket{" +
                "dataPhaseInfo=" + dataPhaseInfo +
                ", operationCode=" + operationCode +
                ", transactionID=" + transactionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                '}';
    }

    public static OperationRequestPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.OPERATION_REQUEST, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Data Phase Info
        byte[] dataPhaseInfoBytes = new byte[UINT32.SIZE];
        buffer.get(dataPhaseInfoBytes);
        UINT32 dataPhaseInfo = new UINT32(dataPhaseInfoBytes);

        // Get Operation Code
        byte[] operationCodeBytes = new byte[UINT16.SIZE];
        buffer.get(operationCodeBytes);
        UINT16 operationCode = new UINT16(operationCodeBytes);

        // Get Transaction ID
        byte[] transactionIDBytes = new byte[UINT32.SIZE];
        buffer.get(transactionIDBytes);
        UINT32 transactionID = new UINT32(transactionIDBytes);

        // Get P1
        byte[] p1Bytes = new byte[UINT32.SIZE];
        buffer.get(p1Bytes);
        UINT32 p1 = new UINT32(p1Bytes);

        // Get P2
        byte[] p2Bytes = new byte[UINT32.SIZE];
        buffer.get(p2Bytes);
        UINT32 p2 = new UINT32(p2Bytes);

        // Get P3
        byte[] p3Bytes = new byte[UINT32.SIZE];
        buffer.get(p3Bytes);
        UINT32 p3 = new UINT32(p3Bytes);

        // Get P4
        byte[] p4Bytes = new byte[UINT32.SIZE];
        buffer.get(p4Bytes);
        UINT32 p4 = new UINT32(p4Bytes);

        // Get P5
        byte[] p5Bytes = new byte[UINT32.SIZE];
        buffer.get(p5Bytes);
        UINT32 p5 = new UINT32(p5Bytes);

        return new OperationRequestPacket(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, p4, p5);
    }
}
