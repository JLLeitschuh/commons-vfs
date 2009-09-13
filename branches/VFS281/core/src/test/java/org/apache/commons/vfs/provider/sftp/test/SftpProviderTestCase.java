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
package org.apache.commons.vfs.provider.sftp.test;

import junit.framework.Test;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs.provider.sftp.TrustEveryoneUserInfo;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemOptions;
import org.apache.commons.vfs.test.AbstractProviderTestConfig;
import org.apache.commons.vfs.test.ProviderTestSuite;

/**
 * Test cases for the SFTP provider.
 *
 * @author <a href="mailto:adammurdoch@apache.org">Adam Murdoch</a>
 * @version $Revision$ $Date$
 */
public class SftpProviderTestCase
    extends AbstractProviderTestConfig
{
    private static final String TEST_URI = "test.sftp.uri";
    public static Test suite() throws Exception
    {
        if (System.getProperty(TEST_URI) != null)
        {
            return new ProviderTestSuite(new SftpProviderTestCase());
        }
        else
        {
            return notConfigured(SftpProviderTestCase.class);
        }
    }

    /**
     * Prepares the file system manager.
     */
    public void prepare(final DefaultFileSystemManager manager)
        throws Exception
    {
        manager.addProvider("sftp", new SftpFileProvider());
    }

    /**
     * Returns the base folder for tests.
     */
    public FileObject getBaseTestFolder(final FileSystemManager manager) throws Exception
    {
        final String uri = System.getProperty(TEST_URI);

        SftpFileSystemOptions fileSystemOptions = new SftpFileSystemOptions();
        fileSystemOptions.setStrictHostKeyChecking("no");
        fileSystemOptions.setUserInfo(new TrustEveryoneUserInfo());

        return manager.resolveFile(uri, fileSystemOptions);
    }
}
