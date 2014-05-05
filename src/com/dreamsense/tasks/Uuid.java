package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;

import java.util.UUID;

/**
  * Generates a unique id and stores it in the specified property.
  */

public class Uuid extends Task {

  private Project project;
  private String propertyName;

  /**
   * Set the name of the property into which the generated UUID will be placed.
   * @param propertyName the property name.
   */
  public void setProperty(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
    * Get the property name that holds the generated UUID.
    * @return propertyName
    */
  public String getProperty() {
    return this.propertyName;
  }

  public void setProject(Project proj) {
    project = proj;
  }

  /**
    * Generate a UUID and set it in the requested property.
    * @throws BuildException if a property isn't specified.
    */
  public void execute() {
    if (getProperty() == null) throw new BuildException("A property name needs to be specified for your uuid.");

    project.setProperty(getProperty(), UUID.randomUUID().toString());
  }
}