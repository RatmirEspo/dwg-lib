package com.onespatial.dwglib.objects;

import com.onespatial.dwglib.FileVersion;
import com.onespatial.dwglib.bitstreams.BitBuffer;
import com.onespatial.dwglib.bitstreams.Extrusion;
import com.onespatial.dwglib.bitstreams.Handle;
import com.onespatial.dwglib.bitstreams.Point2D;

public class Attrib extends EntityObject {

    public double elevation;

    public Point2D insertionPoint;

    public Point2D alignPoint;

    public Extrusion extrusion;

    public double thickness;

    public double obliqueAngle;

    public double rotationAngle;

    public double height;

    public double widthFactor;

    public String value;

    public int generation;

    public int horizontalAlignment;

    public int verticalAlignment;

    public String tag;

    public int fieldLength;

    public int flags;

    public boolean lockPosition;

    public int version;

    private Handle styleHandle;

    public Attrib(ObjectMap objectMap) {
        super(objectMap);
    }

    @Override
    public void readObjectTypeSpecificData(BitBuffer dataStream, BitBuffer stringStream, BitBuffer handleStream, FileVersion fileVersion) {
        // 19.4.4 ATTRIB (2) page 107

        int dataFlags = dataStream.getUnsignedRC();
        if ((dataFlags & 0x01) == 0) {
            elevation = dataStream.getRD();
        }
        insertionPoint = dataStream.get2RD();
        if ((dataFlags & 0x02) == 0) {
            alignPoint = dataStream.get2DD(insertionPoint);
        }
        extrusion = dataStream.getBE();
        thickness = dataStream.getBT();
        if ((dataFlags & 0x04) == 0) {
            obliqueAngle = dataStream.getRD();
        }
        if ((dataFlags & 0x08) == 0) {
            rotationAngle = dataStream.getRD();
        }
        height = dataStream.getRD();
        if ((dataFlags & 0x10) == 0) {
            widthFactor = dataStream.getRD();
        }
        value = stringStream.getTU();
        if ((dataFlags & 0x20) == 0) {
            generation = dataStream.getBS();
        }
        if ((dataFlags & 0x40) == 0) {
            horizontalAlignment = dataStream.getBS();
        }
        if ((dataFlags & 0x80) == 0) {
            verticalAlignment = dataStream.getBS();
        }
        version = dataStream.getRC();
        tag = stringStream.getTU();
        fieldLength = dataStream.getBS();
        flags = dataStream.getRC();
        lockPosition = dataStream.getB();

        styleHandle = handleStream.getHandle(handleOfThisObject);

        handleStream.advanceToByteBoundary();

        dataStream.assertEndOfStream();
        stringStream.assertEndOfStream();
        handleStream.assertEndOfStream();
    }

    @Override
    public String toString() {
        return "ATTRIB";
    }

    public CadObject getStyle() {
        CadObject result = objectMap.parseObject(styleHandle);
        return result;
    }

}
