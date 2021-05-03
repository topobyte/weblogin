// Copyright 2021 Sebastian Kuerten
//
// This file is part of weblogin.
//
// weblogin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// weblogin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with weblogin. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.weblogin.testing.links;

import de.topobyte.webgun.resolving.pathspec.PathSpec;
import de.topobyte.webgun.resolving.smart.defs.PathDef0;

public class LinkDefs
{

	public static PathDef0 ABOUT = new PathDef0(new PathSpec("about"));
	public static PathDef0 IMPRINT = new PathDef0(new PathSpec("imprint"));
	public static PathDef0 PRIVACY_POLICY = new PathDef0(
			new PathSpec("privacy-policy"));

	public static PathDef0 SOMETHING = new PathDef0(new PathSpec("something"));

}
