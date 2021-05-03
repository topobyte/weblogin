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

package de.topobyte.weblogin.pages.user;

import java.sql.SQLException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ListGroupDiv;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;
import de.topobyte.weblogin.links.UserLinkDefs;
import de.topobyte.weblogin.realm.DbUser;
import de.topobyte.weblogin.realm.SystemUser;

public class PreferencesGenerator implements WebloginContentGenerator
{

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Preferences"));

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			SystemUser user = (SystemUser) subject.getPrincipal();
			if (user.getUsername().equals("root")) {
				rootContent(content);
			} else {
				userContent(content, (DbUser) user);
			}
		}
	}

	private void rootContent(Element<?> content)
	{
		content.appendText("You cannot configure anything");
	}

	private void userContent(Element<?> content, DbUser dbUser)
	{
		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());
		list.addA(UserLinkDefs.CHANGE_PASSWORD.getLink(), "Change password");
	}
}
