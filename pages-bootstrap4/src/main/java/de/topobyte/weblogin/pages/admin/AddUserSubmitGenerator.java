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
import java.util.Map;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.components.Alert;
import de.topobyte.jsoup.bootstrap4.components.ContextualType;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.shiro.AuthInfo;
import de.topobyte.shiro.AuthInfoGenerator;
import de.topobyte.webgun.util.ParameterUtil;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;
import de.topobyte.weblogin.util.AccountsUtil;
import de.topobyte.weblogin.validation.EmailValidator;
import de.topobyte.weblogin.validation.UsernameValidator;

public class AddUserSubmitGenerator implements WebloginContentGenerator
{

	private Map<String, String[]> parameters;

	public AddUserSubmitGenerator(Map<String, String[]> parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Admin area"));

		String username = ParameterUtil.get(parameters, "username");
		String email = ParameterUtil.get(parameters, "email");
		String password = ParameterUtil.get(parameters, "password");
		String passwordRepeat = ParameterUtil.get(parameters, "passwordRepeat");

		P p = content.ac(HTML.p());
		p.at(String.format("Trying to add user '%s'", username));

		// Some checks on the password

		if (!password.equals(passwordRepeat)) {
			p = content.ac(HTML.p());
			p.at("Passwords do not match");
			return;
		}
		if (password.isEmpty()) {
			p = content.ac(HTML.p());
			p.at("Passwords must not be empty");
			return;
		}

		// Some checks on the email

		EmailValidator.Status emailStatus = EmailValidator.check(email);
		if (!emailStatus.isValid()) {
			content.ac(new Alert(ContextualType.DANGER,
					"Please enter a valid email address!"));
			return;
		}

		// Some checks on the username

		if (username.length() < 3) {
			content.ac(new Alert(ContextualType.DANGER,
					"Username too short, please make it at least 3 characters!"));
			return;
		}
		UsernameValidator.Status check = UsernameValidator.check(username);
		if (!check.isValid()) {
			content.ac(new Alert(ContextualType.DANGER,
					"Username is invalid, please choose a different one!"));
			return;
		}
		if (AccountsUtil.isUsernameReserved(username)) {
			content.ac(new Alert(ContextualType.DANGER,
					"Username is invalid, please choose a different one!"));
			return;
		}

		// Check username and email are not taken yet

		if (AccountsUtil.isUsernameTaken(loginDao, username)) {
			content.ac(new Alert(ContextualType.DANGER,
					"Username is already taken, please choose a different one!"));
			return;
		}
		if (AccountsUtil.isEmailTaken(loginDao, email)) {
			content.ac(new Alert(ContextualType.DANGER,
					"Email is already taken, please choose a different one!"));
			return;
		}

		// Okay, add the user

		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		AuthInfo authInfo = AuthInfoGenerator.generate(rng, password);

		long id = loginDao.createUser(username, email, authInfo);
		db.getJdbcConnection().commit();

		p = content.ac(HTML.p());
		p.at(String.format("Created user with id '%d'", id));
	}

}
