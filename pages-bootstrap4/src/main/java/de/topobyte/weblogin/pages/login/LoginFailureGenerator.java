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

package de.topobyte.weblogin.pages.login;

import java.sql.SQLException;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;

public class LoginFailureGenerator implements WebloginContentGenerator
{

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Einloggen fehlgeschlagen"));

		P p = content.ac(HTML.p());
		p.at("Irgendwas ist schiefgelaufen. Überprüfe Nutzername und Passwort.");

		p = content.ac(HTML.p());
		p.at("Falls du dein Passwort vergessen hast, schreibe bitte eine Email an ");
		p.ac(HTML.a("mailto:" + website.getAdminContactEmail(),
				website.getAdminContactEmail()));
		p.at(" und wir werden es für dich zurücksetzen.");
	}

}
