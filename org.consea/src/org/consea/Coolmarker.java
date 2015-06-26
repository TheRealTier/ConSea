package org.consea;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;

public class Coolmarker {

	public void newMarker() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IResource file = ResourceUtil.getResource(page.getActiveEditor().getEditorInput());

		createCoolMarker(file, 3);
		createCoolMarker(file, 5);
		createCoolMarker(file, 9);
		createCoolMarker(file, 13);
		createCoolMarker(file, 34);
	}

	private IMarker createCoolMarker(IResource resource, int lineNumber) {
		try {
			IMarker marker = resource.createMarker("org.consea.coolmarker");
			marker.setAttribute("coolFactor", "ULTRA");
			marker.setAttribute("description", "this is one of my markers");
			marker.setAttribute(IMarker.MESSAGE, "My Marker");
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.TEXT, "Asdf");

			return marker;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}
}
