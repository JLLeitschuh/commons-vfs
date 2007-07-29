/*
 * Copyright 2002, 2003,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.util;

/**
 * An enumerated type representing the modes of a random access content.
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision$ $Date$
 */
public class RandomAccessMode
{
    /**
     * read
     */
    public static final RandomAccessMode READ = new RandomAccessMode(true, false);

    /**
     * read/write
     */
    public static final RandomAccessMode READWRITE = new RandomAccessMode(true, true);


    private final boolean read;
    private final boolean write;

    private RandomAccessMode(final boolean read, final boolean write)
    {
        this.read = read;
        this.write = write;
    }

    public boolean requestRead()
    {
        return read;
    }

    public boolean requestWrite()
    {
        return write;
    }
}
