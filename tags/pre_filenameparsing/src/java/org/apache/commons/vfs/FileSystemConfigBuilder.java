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
package org.apache.commons.vfs;


/**
 * Abstract class which has the right to fill FileSystemOptions
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision$ $Date$
 */
public abstract class FileSystemConfigBuilder
{
    protected void setParam(FileSystemOptions opts, String name, Object value)
    {
        opts.setOption(getConfigClass(), name, value);
    }

    protected Object getParam(FileSystemOptions opts, String name)
    {
        if (opts == null)
        {
            return null;
        }

        return opts.getOption(getConfigClass(), name);
    }

    protected boolean hasParam(FileSystemOptions opts, String name)
    {
        if (opts == null)
        {
            return false;
        }

        return opts.hasOption(getConfigClass(), name);
    }

    protected abstract Class getConfigClass();
}
