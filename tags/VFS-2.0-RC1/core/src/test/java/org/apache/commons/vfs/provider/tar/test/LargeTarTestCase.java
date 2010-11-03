package org.apache.commons.vfs.provider.tar.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.vfs.CacheStrategy;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.cache.SoftRefFilesCache;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs.provider.tar.TarFileProvider;

//@SuppressWarnings("nls")
public class LargeTarTestCase extends TestCase {
  private final static String baseDir = "target/test-classes/test-data/";

  private DefaultFileSystemManager manager;
  private final static String largeFilePath = baseDir;
  private final static String largeFileName = "largefile";


  public void setUp() throws Exception {
    manager = new DefaultFileSystemManager();

    manager.setFilesCache(new SoftRefFilesCache());
    manager.setCacheStrategy(CacheStrategy.ON_RESOLVE);

    manager.addProvider("file", new DefaultLocalFileProvider());
    manager.addProvider("tgz", new TarFileProvider());
    manager.addProvider("tar", new TarFileProvider());

    createLargeFile(largeFilePath, largeFileName);
  }

  public void testLargeFile() throws Exception {
    File realFile = new File(largeFilePath + largeFileName + ".tar.gz");

    FileObject file = manager.resolveFile("tgz:file://" + realFile.getCanonicalPath() + "!/");

    assertNotNull(file);
    List files = Arrays.asList(file.getChildren());

    assertNotNull(files);
    assertEquals(1, files.size());
    FileObject f = (FileObject) files.get(0);

    assertTrue("Expected file not found: " + largeFileName + ".txt",
        f.getName().getBaseName().equals(largeFileName + ".txt"));
  }

/*
  public void testFileCheck() throws Exception {
    String[] expectedFiles = {
      "plugins.tsv",
      "languages.tsv",
      "browser_type.tsv",
      "timezones.tsv",
      "color_depth.tsv",
      "resolution.tsv",
      "connection_type.tsv",
      "search_engines.tsv",
      "javascript_version.tsv",
      "operating_systems.tsv",
      "country.tsv",
      "browser.tsv"
    };

    fileCheck(expectedFiles, "tar:file://c:/temp/data/data/data-small.tar");
  } */

  protected void fileCheck(String[] expectedFiles, String tarFile) throws Exception {
    assertNotNull(manager);
    FileObject file = manager.resolveFile(tarFile);

    assertNotNull(file);
    List files = Arrays.asList(file.getChildren());

    assertNotNull(files);
    for(int i=0; i < expectedFiles.length; ++i) {
      String expectedFile = expectedFiles[i];
      assertTrue("Expected file not found: " + expectedFile, fileExists(expectedFile, files));
    }
  }

  /**
   * Search for the expected file in a given list, without using the full path
   * @param expectedFile
   * @param files
   * @return
   */
  protected boolean fileExists(String expectedFile, List files) {
    Iterator iter = files.iterator();
    while (iter.hasNext()) {
      FileObject file = (FileObject) iter.next();
      if(file.getName().getBaseName().equals(expectedFile)) {
        return true;
      }
    }

    return false;
  }

  protected boolean endsWith(String testString, String[] testList) {
    for(int i=0; i < testList.length; ++i) {
      String testItem = testList[i];
      if(testString.endsWith(testItem)) {
        return true;
      }
    }
    return false;
  }

  //@SuppressWarnings("unused")
  protected void createLargeFile(String path, String name) throws Exception {
    long _1K = 1024;
    long _1M = 1024 * _1K;
    long _256M = 256 * _1M;
    long _512M = 512 * _1M;
    long _1G = 1024 * _1M;

    // File size of 3 GB
    long fileSize = 3 * _1G;

    File tarGzFile = new File(path + name + ".tar.gz");

    if(!tarGzFile.exists()) {
      System.out.println("This test is a bit slow. It needs to write a 3GB file to your hard drive");  

      // Create archive
      OutputStream outTarFileStream = new FileOutputStream(path + name + ".tar");

      TarArchiveOutputStream outTarStream = (TarArchiveOutputStream)new ArchiveStreamFactory()
      .createArchiveOutputStream(ArchiveStreamFactory.TAR, outTarFileStream);

      // Create archive contents
      TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(name + ".txt");
      tarArchiveEntry.setSize(fileSize);

      outTarStream.putArchiveEntry(tarArchiveEntry);
      for(long i = 0; i < fileSize; i++) {
        outTarStream.write('a');
      }

      outTarStream.closeArchiveEntry();
      outTarStream.close();

      outTarFileStream.close();

      // Create compressed archive
      OutputStream outGzipFileStream = new FileOutputStream(path + name + ".tar.gz");

      GzipCompressorOutputStream outGzipStream = (GzipCompressorOutputStream)new CompressorStreamFactory()
      .createCompressorOutputStream(CompressorStreamFactory.GZIP, outGzipFileStream);

      // Compress archive
      InputStream inTarFileStream = new FileInputStream(path + name + ".tar");
      // TODO: Change to a Piped Stream to conserve disk space
      IOUtils.copy(inTarFileStream, outGzipStream);
      inTarFileStream.close();

      outGzipStream.close();
      outGzipFileStream.close();

      // Cleanup original tar
      File tarFile = new File(path + name + ".tar");
      if(tarFile.exists()) {
        tarFile.delete();
      }
    }
  }
}
