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

package de.topobyte.weblogin.testing.pages.markdown;

import java.io.IOException;

import de.topobyte.jsoup.Markdown;
import de.topobyte.weblogin.testing.pages.base.BaseGenerator;
import de.topobyte.webpaths.WebPath;

public class MarkdownResourceGenerator extends BaseGenerator
{

	private String resource;

	public MarkdownResourceGenerator(WebPath path, String resource)
	{
		super(path);
		this.resource = resource;
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		menu();

		Markdown.renderResource(content, resource);

		footer();
	}

}
