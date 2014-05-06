package com.dreamsense.tasks;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import com.dreamsense.resources.randomText.BibleIpsum;
import com.dreamsense.resources.randomText.LoremIpsum;

/**
  * Generates random text and stores it in the specified property.
  * @author Kyle Eggleston
  */
public class RandomText extends Task {

  private static final String PLAIN = "plain";
  private static final String HTML = "html";
  private static final String LOREMIPSUM = "loremipsum";
  private static final String BIBLEIPSUM = "bible";

  private String formatType = PLAIN;
  private String methodType = LOREMIPSUM;
  private int numOfParagraphs = 5;
  private Project project;
  private String propertyName;

  /**
    * Set the number of paragraphs to generate.
    * @param numOfParagraphs the number of paragraphs you want.
    */
  public void setParagraphs(int numOfParagraphs) {
    if (numOfParagraphs > 49) {
      numOfParagraphs = 49;
    }
    this.numOfParagraphs = numOfParagraphs;
  }

  /**
    * Get the number of paragarphs.
    * @return numOfParagraphs the number of paragraphs.
    */
  public int getParagraphs() {
    return this.numOfParagraphs;
  }

  /**
    * Set the format of the output.
    * @param formatType the type of string to pass back.
    */
  public void setFormat(String formatType) {
    this.formatType = formatType;
  }

  /**
    * Get the format type for the output.
    * @return formatType
    */
  public String getFormat() {
    return this.formatType;
  }

  /**
    * Set the method of the output.
    * @param methodType the type of string to pass back.
    */
  public void setMethod(String methodType) {
    this.methodType = methodType;
  }

  /**
    * Get the method type for the output.
    * @return methodType
    */
  public String getMethod() {
    return this.methodType;
  }

  /**
   * Set the name of the property into which the generated text will be placed.
   * @param propertyName the property name.
   */
  public void setProperty(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
    * Get the property name that holds the generated text.
    * @return propertyName
    */
  public String getProperty() {
    return this.propertyName;
  }

  public void setProject(Project proj) {
    project = proj;
  }  

  /**
    * Get the desired number of paragraphs.
    * @param numOfParagraphs The number of paragraphs wanted returned.
    * @param text The specified text from the resources.randomText package
    * @return the requested number of paragraphs in a String.
    */
  public String getText(int numOfParagraphs, String[] text) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < numOfParagraphs; i++) {
      if (i > 0) sb.append("\n").append("\n");
      if (getFormat().equals(PLAIN)) {
        sb.append(text[i]);
      } else if (getFormat().equals(HTML)) {
        sb.append("<p>").append(text[i]).append("</p>");
      }
    }

    return sb.toString();
  }

  /**
    * Generates the specified random text and set it in the requested property.
    * @throws BuildException if the property isn't specified
    * @throws BuildException if the format isn't plain or html
    * @throws BuildException if the method isn't a known method
    */
  public void execute() throws BuildException {
    if (getProperty() == null) throw new BuildException("A property name needs to be specified for your text.");

    if (!getFormat().equals(PLAIN) && 
        !getFormat().equals(HTML)) throw new BuildException("You must specify either plain or html as the format.");

    if (!getMethod().equals(LOREMIPSUM) &&
        !getMethod().equals(BIBLEIPSUM)) throw new BuildException("You must specify a valid method type.");

    if (getMethod().equals(LOREMIPSUM)) {
      project.setProperty(getProperty(), getText(getParagraphs(), LoremIpsum.getText()));
    } else if (getMethod().equals(BIBLEIPSUM)) {
      project.setProperty(getProperty(), getText(getParagraphs(), BibleIpsum.getText()));
    }
  }
}