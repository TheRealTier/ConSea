package org.consea;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class CoolmarkerResolution implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		System.out.println("BLAAAAAAAAAAAAAaaaaaaaaaaa");
		return new IMarkerResolution[] {
				new QuickFix("foo"),
				new QuickFix("bar")
		};
	}

}
