package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;

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
    project.setProperty(getProperty(), UUID.randomUUID().toString());
  }
}