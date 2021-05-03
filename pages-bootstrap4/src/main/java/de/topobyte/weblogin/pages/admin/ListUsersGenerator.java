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

package de.topobyte.weblogin.pages.admin;

import java.sql.SQLException;
import java.util.List;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.Table;
import de.topobyte.jsoup.components.TableCell;
import de.topobyte.jsoup.components.TableHead;
import de.topobyte.jsoup.components.TableRow;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;
import de.topobyte.weblogin.db.model.User;
import de.topobyte.weblogin.links.AdminLinkDefs;

public class ListUsersGenerator implements WebloginContentGenerator
{

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("User list"));

		List<User> users = loginDao.getUsers();

		Table table = content.ac(HTML.table());
		table.addClass("table");

		TableHead head = table.head();
		TableRow headrow = head.row();
		headrow.cell("User");
		headrow.cell("Actions");

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			String oddness = (i % 2) == 0 ? "even" : "odd";
			TableRow row = table.row();
			row.addClass(oddness);

			TableCell cell = row.cell();
			cell.at(String.format("%s (%s)", user.getName(), user.getMail()));

			TableCell cellPassword = row.cell();
			cellPassword.ac(HTML.a(
					String.format("%s?user=%d",
							AdminLinkDefs.SET_PASSWORD.getLink(), user.getId()),
					"change password"));
		}
	}

}
