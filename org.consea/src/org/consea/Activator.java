package org.consea;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	
	protected void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null,
				dialogText, MessageDialog.INFORMATION, DIALOG_BUTTON_LABELS, 0);
		dialog.open();
	}
	
	protected Shell getShell() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return shell;
	}

}
