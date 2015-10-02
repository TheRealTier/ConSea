package org.consea.gui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class DialogWindow {
	
	public void displayError(String messageText) {
		String dialogTitle = "Flight Exception";
		openDialogWindow(messageText, dialogTitle);
	}

	public void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null, dialogText, 
												MessageDialog.INFORMATION, DIALOG_BUTTON_LABELS, 0);
		dialog.open();
	}

	public Shell getShell() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return shell;
	}

	public void setWindow(IWorkbenchWindow window) {		
	}
}
