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
import de.topobyte.jsoup.bootstrap4.BootstrapFormsHorizontal;
import de.topobyte.jsoup.bootstrap4.forms.InputGroup;
import de.topobyte.jsoup.components.Form;
import de.topobyte.jsoup.components.Form.Method;
import de.topobyte.jsoup.components.Input.Type;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.jquery.JQuery;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;

public class LoginGenerator implements WebloginContentGenerator
{

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(
				HTML.h1(String.format("Bei %s einloggen", website.getName())));

		content.ac(JQuery.focusById("username"));

		Form form = content.ac(HTML.form());
		form.setMethod(Method.POST);
		form.addClass("form-horizontal");

		BootstrapFormsHorizontal forms = new BootstrapFormsHorizontal();

		InputGroup inputUsername = forms.addInput(form, "user", "Nutzername");
		inputUsername.getInput().setPlaceholder("Nutzername");
		inputUsername.getInput().setId("username");

		InputGroup inputPassword = forms.addInput(form, "pass", "Passwort");
		inputPassword.getInput().setPlaceholder("Passwort");
		inputPassword.getInput().setType(Type.PASSWORD);

		forms.addSubmit(form, "einloggen");

		content.ac(HTML.h2("Neu hier?"));
		P p = content.ac(HTML.p());
		p.at("Du hast noch keinen Account? Jetzt ");
		p.ac(HTML.a("/signup", "registrieren"));
		p.at(".");
	}

}
