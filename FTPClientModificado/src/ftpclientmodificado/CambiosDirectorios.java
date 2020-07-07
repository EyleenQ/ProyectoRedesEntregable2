/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientmodificado;

import java.util.*;
import java.io.*;

/**
 *
 * @author Eyleen
 */
public abstract class CambiosDirectorios extends TimerTask{

  private String path;
  private File filesArray [];
  private HashMap dir = new HashMap();

  public CambiosDirectorios(String path) {
    this(path, "");
  }

  public CambiosDirectorios(String path, String filtro) {
    this.path = path;
    File carpeta = new File(path);
    filesArray =  carpeta.listFiles();

    // transfer to the hashmap be used a reference and keep the
    // lastModfied value
    for(int i = 0; i < filesArray.length; i++) {
       dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
    }
  }

  public final void run() {
    HashSet checkedFiles = new HashSet();
    File carpeta = new File(path);
    filesArray =  carpeta.listFiles();

    // scan the files and check for modification/addition
    for(int i = 0; i < filesArray.length; i++) {
      Long current = (Long)dir.get(filesArray[i]);
      checkedFiles.add(filesArray[i]);
      if (current == null) {
        // new file
        dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
        ///llamado porque hay archivo nuevo
        onChange(filesArray[i], "add");
      }
      else if (current.longValue() != filesArray[i].lastModified()){
        // modified file
        dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
         ///llamado porque se modifico archivo
        onChange(filesArray[i], "modify");
      }
    }

    // now check for deleted files
    Set ref = ((HashMap)dir.clone()).keySet();
    ref.removeAll((Set)checkedFiles);
    Iterator it = ref.iterator();
    while (it.hasNext()) {
      File deletedFile = (File)it.next();
      dir.remove(deletedFile);
       ///llamado porque se elimino archivo
      onChange(deletedFile, "delete");
    }
  }

  protected abstract void onChange( File file, String action );
}
