/*
 * Copyright (C) 2010 Copyright 2010 Google Inc.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package jake2.gwt.server;

import jake2.buf.DataReader;
import jake2.qcommon.Com;
import jake2.qcommon.Defines;
import jake2.qcommon.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ResourceLoaderImpl implements ResourceLoader.Impl {

  protected static final Object LOAD_LOCK = new Object();

  class Pending {
    String path;
    ResourceLoader.Callback callback;
    byte[] bytes;
  }

  ArrayList<Pending> pending = new ArrayList<Pending>();

  public void loadResourceAsync(String path,
      final ResourceLoader.Callback callback) {
    final byte[] bytes = loadResource(path);
    Pending p = new Pending();
    p.bytes = bytes;
    p.callback = callback;
    p.path = path;
    pending.add(p);
  }

  public boolean pump() {
    if (pending.size() == 0)
      return false;

    int i = (int) Math.random() * pending.size();
    if (i < pending.size()) {
      Pending p = pending.get(i);
      pending.remove(i);
      if (p.bytes != null) {
        p.callback.onSuccess(DataReader.wrap(p.bytes));
      } else {
        ResourceLoader.fail(new FileNotFoundException(p.path));
      }
    }
    return true;
  }

  public byte[] loadResource(String path) {
    RandomAccessFile file;
    byte[] buf = null;
    int len = 0;

    // TODO hack for bad strings (fuck \0)
    int index = path.indexOf('\0');
    if (index != -1)
      path = path.substring(0, index);

    // look for it in the filesystem
    len = FileLength(path);

    if (len < 1)
      return null;

    try {
      file = FOpenFile(path);
      // Read(buf = new byte[len], len, h);
      buf = new byte[len];
      file.readFully(buf);
      file.close();
    } catch (IOException e) {
      Com.Error(Defines.ERR_FATAL, e.toString());
    }
    return buf;
  }

  private static int FileLength(String filename) {
    String netpath;

    // check a file in the directory tree
    netpath = Defines.BASEDIRNAME + '/' + filename;

    File file = new File(netpath);
    if (!file.canRead())
      return -1;

    Com.DPrintf("FindFile: " + netpath + '\n');

    return (int) file.length();
  }

  private static RandomAccessFile FOpenFile(String filename) throws IOException {
    String netpath;
    File file = null;

    netpath = Defines.BASEDIRNAME + '/' + filename;

    file = new File(netpath);
    if (!file.canRead())
      return null;

    return new RandomAccessFile(file, "r");
  }

  public void reset() {
    // Only needed for deferred stuff
  }
}
