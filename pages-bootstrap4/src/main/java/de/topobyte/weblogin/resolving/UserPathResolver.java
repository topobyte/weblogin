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

import de.topobyte.jsoup.ContentGeneratable;
import de.topobyte.webgun.resolving.smart.SmartPathSpecResolver;
import de.topobyte.weblogin.WebloginContentGeneratableFactory;
import de.topobyte.weblogin.links.UserLinkDefs;
import de.topobyte.weblogin.pages.user.ChangePasswordGenerator;
import de.topobyte.weblogin.pages.user.ChangePasswordSubmitGenerator;
import de.topobyte.weblogin.pages.user.PreferencesGenerator;

public class UserPathResolver
		extends SmartPathSpecResolver<ContentGeneratable, Void>
{

	private WebloginContentGeneratableFactory factory;

	public UserPathResolver(WebloginContentGeneratableFactory factory)
	{
		this.factory = factory;
	}

	{
		map(UserLinkDefs.CHANGE_PASSWORD, (path, output, request,
				data) -> factory.get(path, new ChangePasswordGenerator()));

		map(UserLinkDefs.CHANGE_PASSWORD_SUBMIT,
				(path, output, request, data) -> factory.get(path,
						new ChangePasswordSubmitGenerator(
								request.getParameterMap())));

		map(UserLinkDefs.PREFERENCES, (path, output, request, data) -> factory
				.get(path, new PreferencesGenerator()));
	}

}
