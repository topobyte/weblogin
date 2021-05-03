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
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.components.Alert;
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
import de.topobyte.weblogin.db.model.Login;
import de.topobyte.weblogin.db.model.User;
import de.topobyte.weblogin.realm.DbUser;
import de.topobyte.weblogin.realm.SystemUser;

public class ChangePasswordSubmitGenerator implements WebloginContentGenerator
{

	final static Logger logger = LoggerFactory
			.getLogger(ChangePasswordSubmitGenerator.class);

	private Map<String, String[]> parameters;

	public ChangePasswordSubmitGenerator(Map<String, String[]> parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Change your password"));

		String oldPassword = ParameterUtil.get(parameters, "oldPassword");
		String password = ParameterUtil.get(parameters, "password");
		String passwordRepeat = ParameterUtil.get(parameters, "passwordRepeat");

		if (!password.equals(passwordRepeat)) {
			content.ac(new Alert(ContextualType.DANGER,
					"Passwords do not match!"));
			return;
		}
		if (password.isEmpty()) {
			content.ac(new Alert(ContextualType.DANGER,
					"Passwords must not be empty!"));
			return;
		}

		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			content.ac(
					new Alert(ContextualType.DANGER, "You're not logged in!"));
			return;
		}

		SystemUser systemUser = (SystemUser) subject.getPrincipal();
		if (!(systemUser instanceof DbUser)) {
			content.ac(new Alert(ContextualType.DANGER,
					"This user cannot change his password!"));
			return;
		}

		DbUser dbUser = (DbUser) systemUser;
		User user = dbUser.getUser();
		Login login;
		try {
			login = loginDao.findLoginById(user.getId());
		} catch (SQLException | QueryException e) {
			content.ac(new Alert(ContextualType.DANGER,
					"Something went wrong while looking for login data!"));
			logger.error(String.format(
					"Error while getting login data for user id '%d'",
					user.getId()), e);
			return;
		}

		AuthInfo authInfoOld = AuthInfoGenerator.generate(oldPassword,
				login.getSalt());

		if (!authInfoOld.getHash().equals(login.getHash())) {
			content.ac(new Alert(ContextualType.DANGER,
					"The old password is not correct!"));
			return;
		}

		RandomNumberGenerator numberGenerator = new SecureRandomNumberGenerator();
		AuthInfo authInfoNew = AuthInfoGenerator.generate(numberGenerator,
				password);

		try {
			loginDao.updateLogin(user.getId(), authInfoNew);
			db.getJdbcConnection().commit();
		} catch (QueryException | SQLException e) {
			content.ac(new Alert(ContextualType.DANGER,
					"Unable to update the password!"));
			logger.error("Error while changing the password", e);
			return;
		}

		content.ac(new Alert(ContextualType.SUCCESS,
				"Changed password successfully!"));
	}

}
