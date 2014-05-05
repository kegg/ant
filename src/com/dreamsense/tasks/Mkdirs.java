package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Mkdirs extends Task {

  private File directoryfile;
  private String directories;
  private String delimiter = ",";

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  public String getDelimiter() {
    return this.delimiter;
  }

  public void setDirs(String directories) {
    this.directories = directories;
  }

  public String getDirs() {
    return this.directories;
  }

  public void setFile(String filename) {
    this.directoryfile = new File(filename);
  }

  public File getFile() {
    return this.directoryfile;
  }

  private void createFromFiles() {
    BufferedReader br = null;

    try {

      String line = null;
      File file = null;

      br = new BufferedReader(new FileReader(getFile()));

      while ((line = br.readLine()) != null) {
        file = new File(line);
        if (!file.exists()) {
          file.mkdirs();
          log("Created dir: " + file.getAbsolutePath());
        }
      }      
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public void execute() {
    if (getFile() == null && getDirs() == null) throw new BuildException("You must specify either the file attribute or the dirs attribute");
    
    if (getFile() != null) {
      if (!getFile().exists()) throw new BuildException("The file you specified: \"" + getFile().getName() + "\" doesn't exist.");
      createFromFiles();
    }

    if (getDirs() != null) {
      File file = null;

      for (String dir : getDirs().split(getDelimiter())) {
        file = new File(dir);
        if (!file.exists()) {
          file.mkdirs();
          log("Created dir: " + file.getAbsolutePath());
        }
      }
    }
  }
}