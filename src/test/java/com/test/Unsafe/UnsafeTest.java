package com.test.Unsafe;


import jdk.internal.misc.Unsafe;
import org.openjdk.jol.vm.VM;

import java.lang.reflect.Field;

/**
 * @author ryan
 * @since 2021/01/20
 */
public class UnsafeTest {
    public static void main(String[] args) throws Exception {
        reflectUnsafe();
        Unsafe unsafe = Unsafe.getUnsafe();
        _getInt(unsafe);
        _putInt(unsafe);
        _putAndGetObject(unsafe);
        _putAndGetAddress(unsafe);

    }

    private static void _getInt(Unsafe unsafe) throws NoSuchFieldException {
        Demo o = new Demo();
        // 第一种情况
        int i1 = unsafe.getInt(o, unsafe.objectFieldOffset(Demo.class.getDeclaredField("n2")));
        System.out.println("第一种情况: " + i1);
        // 第二种情况
        Field n1 = Demo.class.getDeclaredField("n1");
        int i2 = unsafe.getInt(unsafe.staticFieldBase(n1), unsafe.staticFieldOffset(n1));
        System.out.println("第二种情况: " + i2);
        // 第三种
        int[] ns = {9, 8, 7, 6};
        int i3 = unsafe.getInt(ns, unsafe.arrayBaseOffset(ns.getClass()) + 3 * unsafe.arrayIndexScale(ns.getClass()));
        System.out.println("第三种情况: " + i3);
        // 第四种:直接是内存地址
        int i4 = unsafe.getInt(VM.current().addressOf(o) + unsafe.objectFieldOffset(Demo.class.getDeclaredField("n2")));
        System.out.println("第四种情况: " + i4);
    }

    private static void _putInt(Unsafe unsafe) throws NoSuchFieldException {
        Demo o = new Demo();
        // 第一种情况
        unsafe.putInt(o, unsafe.objectFieldOffset(Demo.class.getDeclaredField("n2")), o.n2 + 1);
        System.out.println("第一种情况: " + o.n2);
        // 第二种情况
        Field n1 = Demo.class.getDeclaredField("n1");
        unsafe.putInt(unsafe.staticFieldBase(n1), unsafe.staticFieldOffset(n1), o.n1 + 1);
        System.out.println("第二种情况: " + o.n1);
        // 第三种
        int[] ns = {9, 8, 7, 6};
        unsafe.putInt(ns, unsafe.arrayBaseOffset(ns.getClass()) + 3 * unsafe.arrayIndexScale(ns.getClass()), ns[3] - 1);
        System.out.println("第三种情况: " + ns[3]);
        // 第四种:直接是内存地址
        unsafe.putInt(null, VM.current().addressOf(o) + unsafe.objectFieldOffset(Demo.class.getDeclaredField("n2")), o.n2 + 1);
        System.out.println("第四种情况: " + o.n2);
    }

    private static void _putAndGetObject(Unsafe unsafe) throws NoSuchFieldException {
        Demo o = new Demo();
        // 第一种情况
        Object i1 = unsafe.getObject(o, unsafe.objectFieldOffset(Demo.class.getDeclaredField("n3")));
        System.out.println("before put: " + i1);
        unsafe.putObject(o, unsafe.objectFieldOffset(Demo.class.getDeclaredField("n3")), Integer.valueOf(5));
        Object i2 = unsafe.getObject(o, unsafe.objectFieldOffset(Demo.class.getDeclaredField("n3")));
        System.out.println("after put: " + i2);
    }

    private static void _putAndGetAddress(Unsafe unsafe) throws NoSuchFieldException {
        long start = unsafe.allocateMemory(4);
        int i = unsafe.getInt(start);
        System.out.println("before put:" + i);
        unsafe.putAddress(start, 1000);
        i = unsafe.getInt(start);
        System.out.println("after put1:" + i);
        System.out.println("after put2:" + unsafe.getAddress(start));
    }

    public static sun.misc.Unsafe reflectUnsafe() throws Exception {
        Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (sun.misc.Unsafe) f.get(null);
    }
}
