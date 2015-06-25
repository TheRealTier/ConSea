package org.consea.gui;


import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class InputVariableNameToSearchDialog extends TitleAreaDialog {

	  private Text txtValueToSearchFor;

	  private String valueToSearchFor;

	  public InputVariableNameToSearchDialog() {
	    super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	  }

	  @Override
	  public void create() {
	    super.create();
	    setTitle("Input Variable Name");
	    setMessage("Please enter a value and Consea will list you all the constants that hold this value.", IMessageProvider.INFORMATION);
	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    GridLayout layout = new GridLayout(2, false);
	    container.setLayout(layout);

	    createValueToSearchForInput(container);

	    return area;
	  }

	  private void createValueToSearchForInput(Composite container) {
	    Label lbtValueToSearchFor = new Label(container, SWT.NONE);
	    lbtValueToSearchFor.setText("Value to search for");

	    GridData dataValueToSearchFor = new GridData();
	    dataValueToSearchFor.grabExcessHorizontalSpace = true;
	    dataValueToSearchFor.horizontalAlignment = GridData.FILL;

	    txtValueToSearchFor = new Text(container, SWT.BORDER);
	    txtValueToSearchFor.setLayoutData(dataValueToSearchFor);
	  }

	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  // save content of the Text fields because they get disposed
	  // as soon as the Dialog closes
	  private void saveInput() {
	    valueToSearchFor = txtValueToSearchFor.getText();
	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	  public String getValueToSearchFor() {
	    return valueToSearchFor;
	  }
	} 