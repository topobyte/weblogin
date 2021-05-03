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
import de.topobyte.weblogin.links.AdminLinkDefs;
import de.topobyte.weblogin.pages.admin.AddUserGenerator;
import de.topobyte.weblogin.pages.admin.AddUserSubmitGenerator;
import de.topobyte.weblogin.pages.admin.AdminGenerator;
import de.topobyte.weblogin.pages.admin.ListUsersGenerator;
import de.topobyte.weblogin.pages.admin.SetUserPasswordGenerator;
import de.topobyte.weblogin.pages.admin.SetUserPasswordSubmitGenerator;

public class AdminPathResolver
		extends SmartPathSpecResolver<ContentGeneratable, Void>
{

	private WebloginContentGeneratableFactory factory;

	public AdminPathResolver(WebloginContentGeneratableFactory factory)
	{
		this.factory = factory;
	}

	{
		map(AdminLinkDefs.INDEX, (path, output, request, data) -> factory
				.get(path, new AdminGenerator()));

		map(AdminLinkDefs.ADD_USER, (path, output, request, data) -> factory
				.get(path, new AddUserGenerator()));

		map(AdminLinkDefs.ADD_USER_SUBMIT,
				(path, output, request, data) -> factory.get(path,
						new AddUserSubmitGenerator(request.getParameterMap())));

		map(AdminLinkDefs.LIST_USERS, (path, output, request, data) -> factory
				.get(path, new ListUsersGenerator()));

		map(AdminLinkDefs.SET_PASSWORD,
				(path, output, request, data) -> factory.get(path,
						new SetUserPasswordGenerator(
								request.getParameterMap())));

		map(AdminLinkDefs.SET_PASSWORD_SUBMIT,
				(path, output, request, data) -> factory.get(path,
						new SetUserPasswordSubmitGenerator(
								request.getParameterMap())));
	}

}
