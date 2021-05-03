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

package de.topobyte.weblogin.testing.pages.main;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.Table;
import de.topobyte.jsoup.components.TableRow;
import de.topobyte.weblogin.testing.TestData;
import de.topobyte.weblogin.testing.TestUser;
import de.topobyte.weblogin.testing.Website;
import de.topobyte.weblogin.testing.links.LinkDefs;
import de.topobyte.weblogin.testing.pages.base.SimpleBaseGenerator;
import de.topobyte.webpaths.WebPath;

public class IndexGenerator extends SimpleBaseGenerator
{

	public IndexGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1(Website.TITLE));

		P p = content.ac(HTML.p());
		p.at("A testing website for weblogin.");
		p.at(" You need to login to visit ");
		p.ac(HTML.a(LinkDefs.SOMETHING.getLink(), "this page"));
		p.at(".");

		p = content.ac(HTML.p())
				.at("Here are some valid user/password combinations:");

		Table table = content.ac(HTML.table());
		table.addClass("table");
		row(table.head().row(), "user", "password");
		row(table.row(), "root", "asdf1234");
		for (TestUser user : TestData.getUsers()) {
			row(table.row(), user.getUser(), user.getPassword());
		}
	}

	private void row(TableRow row, String user, String password)
	{
		row.cell(user);
		row.cell(password);
	}

}
