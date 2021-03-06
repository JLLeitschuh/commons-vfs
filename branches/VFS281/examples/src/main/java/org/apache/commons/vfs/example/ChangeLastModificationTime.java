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
package org.apache.commons.vfs.example;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.VFS;

/**
 * Simply changed the last modification time of the given file
 *
 * @author imario@apache.org
 */
public class ChangeLastModificationTime
{
    public static void main(String[] args) throws Exception
	{
        if (args.length == 0)
        {
            System.err.println("Please pass the name of a file as parameter.");
            return;
        }

		FileObject fo = VFS.getManager().resolveFile(args[0]);
		long setTo = System.currentTimeMillis();
		System.err.println("set to: " + setTo);
		fo.getContent().setLastModifiedTime(setTo);
		System.err.println("after set: " + fo.getContent().getLastModifiedTime());
    }
}
