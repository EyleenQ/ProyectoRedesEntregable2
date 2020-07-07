/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientmodificado;

import java.io.File;
import java.util.TimerTask;

/**
 *
 * @author Eyleen
 */
public abstract class CambiosArchivos extends TimerTask {
  private long timeStamp;
  private File file;

  public CambiosArchivos( File file ) {
    this.file = file;
    this.timeStamp = file.lastModified();
  }

  public final void run() {
    long timeStamp = file.lastModified();

    if( this.timeStamp != timeStamp ) {
      this.timeStamp = timeStamp;
      onChange(file);
    }
  }

  protected abstract void onChange( File file );
}
