package org.consea.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IMarkerResolution;

public class KappaQuickFix implements IMarkerResolution {

	String label;

	public KappaQuickFix(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void run(IMarker marker) {
		MessageDialog.openInformation(null, "QuickFix Demo", "This quick-fix is not yet implemented");
	}
}
