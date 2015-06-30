package org.consea.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class KappamarkerResolution implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		System.out.println("BLAAAAAAAAAAAAAaaaaaaaaaaa");
		return new IMarkerResolution[] {
				new KappaQuickFix("foo"),
				new KappaQuickFix("bar")
		};
	}

}
