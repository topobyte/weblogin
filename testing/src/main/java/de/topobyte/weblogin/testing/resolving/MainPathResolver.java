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

package de.topobyte.weblogin.testing.resolving;

import javax.servlet.http.HttpServletRequest;

import de.topobyte.jsoup.ContentGeneratable;
import de.topobyte.webgun.resolving.smart.SmartPathSpecResolver;
import de.topobyte.weblogin.testing.links.LinkDefs;
import de.topobyte.weblogin.testing.pages.main.AboutGenerator;
import de.topobyte.weblogin.testing.pages.main.IndexGenerator;
import de.topobyte.weblogin.testing.pages.main.SomethingGenerator;
import de.topobyte.weblogin.testing.pages.markdown.MarkdownResourceGenerator;
import de.topobyte.webpaths.WebPath;

public class MainPathResolver
		extends SmartPathSpecResolver<ContentGeneratable, Void>
{

	@Override
	public ContentGeneratable getGenerator(WebPath path,
			HttpServletRequest request, Void data)
	{
		if (path.getNameCount() == 0) {
			return new IndexGenerator(path);
		}
		return super.getGenerator(path, request, data);
	}

	{
		map(LinkDefs.ABOUT,
				(path, output, request, data) -> new AboutGenerator(path));
		map(LinkDefs.IMPRINT,
				(path, output, request, data) -> new MarkdownResourceGenerator(
						path, "markdown/imprint.md"));
		map(LinkDefs.PRIVACY_POLICY,
				(path, output, request, data) -> new MarkdownResourceGenerator(
						path, "markdown/privacy-policy.md"));

		map(LinkDefs.SOMETHING,
				(path, output, request, data) -> new SomethingGenerator(path));
	}

}
