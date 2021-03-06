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
package org.apache.commons.vfs;


/**
 * The fileCache interface
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision$ $Date$
 */
public interface FilesCache
{
    /**
     * add a fileobject to the cache
     *
     * @param file the file
     */
    public void putFile(final FileObject file);

    /**
     * retrieve a file from the cache by its name
     *
     * @param name the name
     * @return the fileobject or null if file is not cached
     */
    public FileObject getFile(final FileSystem filesystem, final FileName name);

    /**
     * purge the entries corresponding to the filesystem
     */
    public void clear(final FileSystem filesystem);

    /**
     * purge the whole cache
     */
    public void close();

    /**
     * removes a file from cache
     *
     * @param filesystem filesystem
     * @param name       filename
     */
    public void removeFile(final FileSystem filesystem, final FileName name);

    /**
     * if the cache uses timestamps it could use this method to handle
     * updates of them
     *
     * @param file filename
     */
    // public void touchFile(final FileObject file);
}
