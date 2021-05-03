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
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ContextualType;
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

public class SetUserPasswordSubmitGenerator implements WebloginContentGenerator
{

	private Map<String, String[]> parameters;

	public SetUserPasswordSubmitGenerator(Map<String, String[]> parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Admin area"));

		String sUserId = ParameterUtil.get(parameters, "user");
		long userId = Long.parseLong(sUserId);

		String password = ParameterUtil.get(parameters, "password");
		String passwordRepeat = ParameterUtil.get(parameters, "passwordRepeat");

		if (!password.equals(passwordRepeat)) {
			content.ac(Bootstrap.alert(ContextualType.DANGER))
					.at("Passwords do not match");
			return;
		}
		if (password.isEmpty()) {
			content.ac(Bootstrap.alert(ContextualType.DANGER))
					.at("Passwords must not be empty");
			return;
		}

		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		AuthInfo authInfo = AuthInfoGenerator.generate(rng, password);

		loginDao.updateLogin(userId, authInfo);
		content.ac(Bootstrap.alert(ContextualType.SUCCESS)).at("Password set");

		db.getJdbcConnection().commit();
	}

}
