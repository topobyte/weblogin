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

package de.topobyte.weblogin.resolving;

import javax.servlet.http.HttpServletRequest;

import de.topobyte.jsoup.ContentGeneratable;
import de.topobyte.webgun.resolving.pathspec.PathSpec;
import de.topobyte.webgun.resolving.pathspec.PathSpecOutput;
import de.topobyte.webgun.resolving.smart.SmartPathSpecResolver;
import de.topobyte.weblogin.WebloginContentGeneratableFactory;
import de.topobyte.weblogin.links.LoginLinkDefs;
import de.topobyte.weblogin.pages.login.LoginFailureGenerator;
import de.topobyte.weblogin.pages.login.LoginGenerator;
import de.topobyte.webpaths.WebPath;

public class LoginPathResolver
		extends SmartPathSpecResolver<ContentGeneratable, Void>
{

	private WebloginContentGeneratableFactory factory;

	public LoginPathResolver(WebloginContentGeneratableFactory factory)
	{
		this.factory = factory;
	}

	{
		map(LoginLinkDefs.LOGIN, (path, output, request, data) -> factory
				.get(path, new LoginGenerator()));
	}

	@Override
	public ContentGeneratable getGenerator(WebPath path,
			HttpServletRequest request, Void data)
	{
		ContentGeneratable generator = null;

		PathSpec pathSpecLogin = LoginLinkDefs.LOGIN.getPathSpec();
		PathSpecOutput output = new PathSpecOutput();
		if (pathSpecLogin.matches(path, output)) {
			Object loginFailure = request.getAttribute("shiroLoginFailure");
			if (loginFailure != null) {
				if (loginFailure.equals(
						"org.apache.shiro.authc.AuthenticationException")) {
					generator = factory.get(path, new LoginFailureGenerator());
				} else if (loginFailure.equals(
						"org.apache.shiro.authc.UnknownAccountException")) {
					generator = factory.get(path, new LoginFailureGenerator());
				} else if (loginFailure.equals(
						"org.apache.shiro.authc.IncorrectCredentialsException")) {
					generator = factory.get(path, new LoginFailureGenerator());
				}
			}
		}

		if (generator != null) {
			return generator;
		}

		return super.getGenerator(path, request, data);
	}

}
