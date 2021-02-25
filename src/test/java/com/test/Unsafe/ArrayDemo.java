package com.test.Unsafe;

import jdk.internal.misc.Unsafe;

/**
 * @author ryan
 * @since 2021/02/24
 */
public class ArrayDemo {

    public static void main(String[] args) throws Exception {
        _unsafeInstance();
        _newInstance();
    }

    private static void _newInstance() {
        int[] is = new int[10];
        for (int i : is) {
            System.out.println(i + ", ");
        }
    }

    private static void _unsafeInstance() throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        int[] is = (int[]) unsafe.allocateUninitializedArray(int.class, 10);

        for (int i : is) {
            System.out.println(i + ", ");
        }
    }
}
