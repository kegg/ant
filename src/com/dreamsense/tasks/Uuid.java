package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;

import java.util.UUID;

public class Uuid extends Task {

  private Project project;
  private String propertyName;

  public void setProperty(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getProperty() {
    return this.propertyName;
  }

  public void setProject(Project proj) {
    project = proj;
  }

  public void execute() {
    if (getProperty() == null) throw new BuildException("A property name needs to be specified for your uuid.");

    project.setProperty(getProperty(), UUID.randomUUID().toString());
  }
}