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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.P;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.weblogin.realm.SystemUser;
import de.topobyte.weblogin.testing.pages.base.DatabaseBaseGenerator;
import de.topobyte.webpaths.WebPath;

public class SomethingGenerator extends DatabaseBaseGenerator
{

	public SomethingGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content() throws QueryException
	{
		content.ac(HTML.h1("Something"));

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			SystemUser user = (SystemUser) subject.getPrincipal();
			P p = content.ac(HTML.p());
			p.at("Hello, " + user.getUsername() + "!");
		}

		P p = content.ac(HTML.p());
		p.at("Some restricted content...");
	}

}
