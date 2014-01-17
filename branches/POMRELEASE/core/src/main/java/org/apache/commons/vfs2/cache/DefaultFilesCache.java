/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs2.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;

/**
 * A {@link org.apache.commons.vfs2.FilesCache} implementation.<br>
 * This implementation caches every file for the complete lifetime of the used
 * {@link org.apache.commons.vfs2.FileSystemManager}.
 */
public class DefaultFilesCache extends AbstractFilesCache
{

    /** The FileSystem cache */
    private final ConcurrentMap<FileSystem, ConcurrentMap<FileName, FileObject>> filesystemCache =
          new ConcurrentHashMap<FileSystem, ConcurrentMap<FileName, FileObject>>(10);

    @Override
    public void putFile(final FileObject file)
    {
        final Map<FileName, FileObject> files = getOrCreateFilesystemCache(file.getFileSystem());
        files.put(file.getName(), file);
    }

    @Override
    public boolean putFileIfAbsent(final FileObject file)
    {
        final ConcurrentMap<FileName, FileObject> files = getOrCreateFilesystemCache(file.getFileSystem());
        return files.putIfAbsent(file.getName(), file) == null;
    }

    @Override
    public FileObject getFile(final FileSystem filesystem, final FileName name)
    {
        final Map<FileName, FileObject> files = getOrCreateFilesystemCache(filesystem);
        return files.get(name);
    }

    @Override
    public void clear(final FileSystem filesystem)
    {
        final Map<FileName, FileObject> files = getOrCreateFilesystemCache(filesystem);
        files.clear();
    }

    protected ConcurrentMap<FileName, FileObject> getOrCreateFilesystemCache(final FileSystem filesystem)
    {
        ConcurrentMap<FileName, FileObject> files = filesystemCache.get(filesystem);
        if (files == null)
        {
            filesystemCache.putIfAbsent(filesystem, new ConcurrentHashMap<FileName, FileObject>());
            files = filesystemCache.get(filesystem);
        }

        return files;
    }

    @Override
    public void close()
    {
        super.close();

        filesystemCache.clear();
    }

    @Override
    public void removeFile(final FileSystem filesystem, final FileName name)
    {
        final Map<?, ?> files = getOrCreateFilesystemCache(filesystem);
        files.remove(name);
    }

    public void touchFile(final FileObject file)
    {
    }
}
