/*
 * Copyright 2002-2005 The Apache Software Foundation.
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
package org.apache.commons.vfs.provider.compressed;

import org.apache.commons.vfs.Capability;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.provider.AbstractFileObject;

/**
 * A compressed file.<br>
 * Such a file do only have one child (the compressed filename with stripped last extension)
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision$ $Date$
 */
public abstract class CompressedFileFileObject
    extends AbstractFileObject
    implements FileObject
{
    private final FileObject container;
    private String[] children;

    protected CompressedFileFileObject(FileName name, FileObject container, CompressedFileFileSystem fs)
    {
        super(name, fs);
        this.container = container;

        // todo, add getBaseName(String) to FileName
        String basename = container.getName().getBaseName();
        int pos = basename.lastIndexOf('.');
        basename = basename.substring(0, pos);

        children = new String[]
        {
            basename
        };
    }

    /**
     * Returns true if this file is read-only.
     */
    public boolean isWriteable()
    {
        return getFileSystem().hasCapability(Capability.WRITE_CONTENT);
    }

    /**
     * Returns the file's type.
     */
    protected FileType doGetType() throws FileSystemException
    {
        return this.container.getType();
    }

    /**
     * Lists the children of the file.
     */
    protected String[] doListChildren()
    {
        return children;
    }

    /**
     * Returns the size of the file content (in bytes).  Is only called if
     * {@link #doGetType} returns {@link FileType#FILE}.
     */
    protected long doGetContentSize()
    {
        return -1;
    }

    /**
     * Returns the last modified time of this file.
     */
    protected long doGetLastModifiedTime() throws Exception
    {
        return container.getContent().getLastModifiedTime();
    }

    protected FileObject getContainer()
    {
        return container;
    }

    public void createFile() throws FileSystemException
    {
        container.createFile();
        injectType(FileType.FILE);
    }
}
