/*
 * Copyright (c) 2000, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

/**



 * A read-only HeapLongBuffer.  This class extends the corresponding
 * read/write class, overriding the mutation methods to throw a {@link
 * ReadOnlyBufferException} and overriding the view-buffer methods to return an
 * instance of this class rather than of the superclass.

 */

class HeapLongBufferR
    extends HeapLongBuffer
{
    // Cached array base offset
    private static final long ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(long[].class);

    // Cached array base offset
    private static final long ARRAY_INDEX_SCALE = UNSAFE.arrayIndexScale(long[].class);

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*




    */

    HeapLongBufferR(int cap, int lim) {            // package-private








        super(cap, lim);
        this.isReadOnly = true;

    }

    HeapLongBufferR(long[] buf, int off, int len) { // package-private








        super(buf, off, len);
        this.isReadOnly = true;

    }

    protected HeapLongBufferR(long[] buf,
                                   int mark, int pos, int lim, int cap,
                                   int off)
    {








        super(buf, mark, pos, lim, cap, off);
        this.isReadOnly = true;

    }

    public LongBuffer slice() {
        int pos = this.position();
        int lim = this.limit();
        int rem = (pos <= lim ? lim - pos : 0);
        return new HeapLongBufferR(hb,
                                        -1,
                                        0,
                                        rem,
                                        rem,
                                        pos + offset);
    }















    public LongBuffer duplicate() {
        return new HeapLongBufferR(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset);
    }

    public LongBuffer asReadOnlyBuffer() {








        return duplicate();

    }











































    public boolean isReadOnly() {
        return true;
    }

    public LongBuffer put(long x) {




        throw new ReadOnlyBufferException();

    }

    public LongBuffer put(int i, long x) {




        throw new ReadOnlyBufferException();

    }

    public LongBuffer put(long[] src, int offset, int length) {









        throw new ReadOnlyBufferException();

    }

    public LongBuffer put(LongBuffer src) {


























        throw new ReadOnlyBufferException();

    }

    public LongBuffer compact() {









        throw new ReadOnlyBufferException();

    }




















































































































































































































































































































































































    public ByteOrder order() {
        return ByteOrder.nativeOrder();
    }







}
