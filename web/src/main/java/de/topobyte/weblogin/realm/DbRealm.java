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

package de.topobyte.weblogin.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.shiro.AuthInfo;
import de.topobyte.weblogin.DatabaseFactory;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.db.model.Login;
import de.topobyte.weblogin.db.model.User;
import de.topobyte.weblogin.token.TokenAuthenticationInfo;
import de.topobyte.weblogin.token.TokenToken;

public class DbRealm extends AuthorizingRealm
{

	static final Logger logger = LoggerFactory.getLogger(DbRealm.class);

	public static DatabaseFactory DATABASE_FACTORY = null;
	public static UserInfo ROOT_USER_INFO = null;

	private static final String REALM_NAME = "database";

	public DbRealm()
	{
		logger.info("Initalizing db realm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals)
	{
		SystemUser user = (SystemUser) getAvailablePrincipal(principals);
		Set<String> roles = new HashSet<>();
		assignRoles(roles, user);
		return new SimpleAuthorizationInfo(roles);
	}

	protected void assignRoles(Set<String> roles, SystemUser user)
	{
		roles.add("user");
		if (user.getUsername().equals("root")) {
			roles.add("admin");
		}
	}

	@Override
	public void assertCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) throws AuthenticationException
	{
		if (token instanceof TokenToken) {
			logger.info("AUTH: Authenticating through token: "
					+ token.getPrincipal());
			return;
		}
		logger.info("AUTH: Authenticating through password: "
				+ token.getPrincipal());
		try {
			if (token.getCredentials() == null) {
				throw new AuthenticationException(
						"AUTH: No credentials supplied");
			}
			super.assertCredentialsMatch(token, info);
		} catch (AuthenticationException e) {
			logger.info("AUTH: Password authentication failed: "
					+ token.getPrincipal());
			throw e;
		}
		logger.info("AUTH: Password authentication succeeded: "
				+ token.getPrincipal());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException
	{
		if (token instanceof UsernamePasswordToken) {
			UsernamePasswordToken upToken = (UsernamePasswordToken) token;
			String username = upToken.getUsername();

			if (username == null) {
				throw new UnknownAccountException("No username specified");
			}

			UserInfo userInfo = getInfo(username);
			AuthInfo info = userInfo.getAuthInfo();

			byte[] bytes = Base64.decode(info.getSalt());
			SimpleByteSource salt = new SimpleByteSource(bytes);

			return new SimpleAuthenticationInfo(userInfo.getUser(),
					info.getHash(), salt, REALM_NAME);
		} else if (token instanceof TokenToken) {
			TokenToken tokenToken = (TokenToken) token;

			return new TokenAuthenticationInfo(tokenToken.getUsername());
		}
		throw new AuthenticationException("token type not supported");
	}

	private UserInfo getInfo(String username)
	{
		if (username.equals("root") && ROOT_USER_INFO != null) {
			return ROOT_USER_INFO;
		}

		Database db = null;
		User user = null;
		Login login = null;
		try {
			db = DATABASE_FACTORY.getDatabase();
			LoginDao dao = new LoginDao(db.getConnection());

			user = dao.findUserByEmail(username);
			if (user == null) {
				user = dao.findUserByName(username);
			}

			if (user == null) {
				logger.info("AUTH: Username not found: '" + username + "'");
				throw new UnknownAccountException(
						"No account found for user [" + username + "]");
			}

			login = dao.findLoginById(user.getId());
		} catch (UnknownAccountException e) {
			logger.warn(
					"Login attempt with unknown account: " + e.getMessage());
			throw new AuthenticationException();
		} catch (Throwable e) {
			logger.warn("Error during authentication lookup", e);
			throw new AuthenticationException();
		} finally {
			db.closeConnection(false);
		}

		return new UserInfo(new DbUser(user),
				new AuthInfo(login.getHash(), login.getSalt()));
	}

	@Override
	public boolean supports(AuthenticationToken token)
	{
		if (token instanceof TokenToken) {
			return true;
		}
		return super.supports(token);
	}

}
