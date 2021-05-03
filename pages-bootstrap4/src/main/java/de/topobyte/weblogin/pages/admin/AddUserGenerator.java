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

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.BootstrapForms;
import de.topobyte.jsoup.bootstrap4.forms.InputGroup;
import de.topobyte.jsoup.components.Form;
import de.topobyte.jsoup.components.Form.Method;
import de.topobyte.jsoup.components.Input.Type;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;
import de.topobyte.weblogin.links.AdminLinkDefs;

public class AddUserGenerator implements WebloginContentGenerator
{

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Add user"));

		P intro = content.ac(HTML.p());
		intro.at("Enter details:");

		Form form = content.ac(HTML.form());
		form.setAction(AdminLinkDefs.ADD_USER_SUBMIT.getLink());
		form.setMethod(Method.POST);

		BootstrapForms forms = new BootstrapForms();

		InputGroup inputUsername = forms.addInput(form, "username", "Username");
		InputGroup inputEmail = forms.addInput(form, "email", "Email");
		InputGroup inputPassword = forms.addInput(form, "password", "Password");
		InputGroup inputPasswordRepeat = forms.addInput(form, "passwordRepeat",
				"Repeat password");
		forms.addSubmit(form, "Add user");

		inputUsername.getInput().setPlaceholder("username");
		inputEmail.getInput().setPlaceholder("user@example.com");
		inputPassword.getInput().setPlaceholder("password");
		inputPasswordRepeat.getInput().setPlaceholder("password");

		inputPassword.getInput().setType(Type.PASSWORD);
		inputPasswordRepeat.getInput().setType(Type.PASSWORD);
	}

}
