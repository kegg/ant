package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
  * Creates a directory structure based off of entries in a file or a String of filenames. 
  * Also non-existent parent directories are created, when necessary. Does nothing if the 
  * directory already exist.
  * @author Kyle Eggleston  
  */

public class Mkdirs extends Task {

  private File directoryfile;
  private String directories;
  private String delimiter = ",";

  /**
    * The delimiter used in the dirs attribute.
    * @param delimiter the String used as a separator
    */
  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  /**
    * Get the delimiter.
    * @return String
    */
  public String getDelimiter() {
    return this.delimiter;
  }

  /**
    * A list of directories to create.
    * @param directories the list of directories to be made.
    */
  public void setDirs(String directories) {
    this.directories = directories;
  }

  /**
    * Get the list of directories to create.
    * @return String
    */
  public String getDirs() {
    return this.directories;
  }

  /**
    * The file name from which a directory structure is read.
    * @param filename The file to read in as a directory structure.
    */
  public void setFile(String filename) {
    this.directoryfile = new File(filename);
  }

  /**
    * The file object of the directory structure.
    * @return File
    */
  public File getFile() {
    return this.directoryfile;
  }

  /**
    * Creates a directory structure based off of a file layout.
    */
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

  /**
    * Create the directory strucutre and all parents.
    * @throws BuildException if file or dirs attribute isn't specified
    * @throws BuildException if file is specified but is null
    */
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