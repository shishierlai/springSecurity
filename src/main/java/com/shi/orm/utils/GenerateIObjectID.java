package com.shi.orm.utils;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
// 生成一种有序列的ID 类似于自增的情况
//


public class GenerateIObjectID implements Comparable<GenerateIObjectID>, Serializable {
    private final int _time = (int)(System.currentTimeMillis() / 1000L);
    private final int _machine;
    private final int _inc;
    private boolean _new;
    private static final int _genmachine;
    private static AtomicInteger _nextInc = new AtomicInteger((new Random()).nextInt());
    private static final long serialVersionUID = -4415279469780082174L;
    private static final Logger LOGGER = Logger.getLogger("org.bson.GenerateIObjectID");

    public GenerateIObjectID() {
        this._machine = _genmachine;
        this._inc = _nextInc.getAndIncrement();
        this._new = true;
    }

    public static String id() {
        return get().toHexString();
    }

    public static GenerateIObjectID get() {
        return new GenerateIObjectID();
    }


    public String toHexString() {
        //设置ID的长度为24
        StringBuilder buf = new StringBuilder(24);
        byte[] var2 = this.toByteArray();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            buf.append(String.format("%02x", b & 255));
        }

        return buf.toString();
    }

    public byte[] toByteArray() {
        byte[] b = new byte[12];
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.putInt(this._time);
        bb.putInt(this._machine);
        bb.putInt(this._inc);
        return b;
    }

    private int _compareUnsigned(int i, int j) {
        long li = 4294967295L;
        li &= (long)i;
        long lj = 4294967295L;
        lj &= (long)j;
        long diff = li - lj;
        if (diff < -2147483648L) {
            return -2147483648;
        } else {
            return diff > 2147483647L ? 2147483647 : (int)diff;
        }
    }

    public int compareTo(GenerateIObjectID id) {
        if (id == null) {
            return -1;
        } else {
            int x = this._compareUnsigned(this._time, id._time);
            if (x != 0) {
                return x;
            } else {
                x = this._compareUnsigned(this._machine, id._machine);
                return x != 0 ? x : this._compareUnsigned(this._inc, id._inc);
            }
        }
    }

    static {
        try {
            int machinePiece;
            try {
                StringBuilder sb = new StringBuilder();
                Enumeration e = NetworkInterface.getNetworkInterfaces();

                while(e.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface)e.nextElement();
                    sb.append(ni.toString());
                }

                machinePiece = sb.toString().hashCode() << 16;
            } catch (Throwable var7) {
                LOGGER.log(Level.WARNING, var7.getMessage(), var7);
                machinePiece = (new Random()).nextInt() << 16;
            }

            LOGGER.fine("machine piece post: " + Integer.toHexString(machinePiece));
            int processId = (new Random()).nextInt();

            try {
                processId = ManagementFactory.getRuntimeMXBean().getName().hashCode();
            } catch (Throwable var6) {
                throw new Exception(var6);
            }

            ClassLoader loader = GenerateIObjectID.class.getClassLoader();
            int loaderId = loader != null ? System.identityHashCode(loader) : 0;
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(processId));
            sb.append(Integer.toHexString(loaderId));
            int processPiece = sb.toString().hashCode() & '\uffff';
            LOGGER.fine("process piece: " + Integer.toHexString(processPiece));
            _genmachine = machinePiece | processPiece;
            LOGGER.fine("machine : " + Integer.toHexString(_genmachine));
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }
}
