package com.test.Unsafe;

import sun.misc.Unsafe;

/**
 * @author ryan
 * @since 2021/02/24
 */
public class InstanceDemo {
    Integer n1 = 1;
    Integer n2;
    Integer n3;

    {
        n2 = 2;
    }

    public InstanceDemo(int n3) {
        this.n3 = n3;
    }

    private InstanceDemo() {
        this.n3 = 3;
    }

    public static void main(String[] args) throws Exception {
        _unsafeInstance();
        _newInstance();
    }

    private static void _newInstance() {
        InstanceDemo demo = new InstanceDemo(3);
        System.out.println("n1 : " + demo.n1);
        System.out.println("n2 : " + demo.n2);
        System.out.println("n3 : " + demo.n3);
    }

    private static void _unsafeInstance() throws Exception {
        Unsafe unsafe = UnsafeTest.reflectUnsafe();
        InstanceDemo instance = (InstanceDemo) unsafe.allocateInstance(InstanceDemo.class);
        System.out.println("n1 : " + instance.n1);
        System.out.println("n2 : " + instance.n2);
        System.out.println("n3 : " + instance.n3);
    }
}
