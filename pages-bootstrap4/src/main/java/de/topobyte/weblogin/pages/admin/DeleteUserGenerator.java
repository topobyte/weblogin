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

import de.topobyte.jsoup.FormUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.BootstrapForms;
import de.topobyte.jsoup.bootstrap4.components.ContextualType;
import de.topobyte.jsoup.components.Form;
import de.topobyte.jsoup.components.Form.Method;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.webgun.util.ParameterUtil;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.WebsiteInfo;
import de.topobyte.weblogin.db.model.User;
import de.topobyte.weblogin.links.AdminLinkDefs;

public class DeleteUserGenerator implements WebloginContentGenerator
{

	private Map<String, String[]> parameters;

	public DeleteUserGenerator(Map<String, String[]> parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public void content(WebsiteInfo website, LinkResolver resolver,
			Element<?> content, Database db, LoginDao loginDao)
			throws QueryException, SQLException
	{
		content.ac(HTML.h1("Confirm user deletion"));

		String sUserId = ParameterUtil.get(parameters, "user");
		long userId = Long.parseLong(sUserId);
		User user = loginDao.getUserById(userId);

		P intro = content.ac(HTML.p());
		intro.at(String.format("Delete user %s (%s)", user.getName(),
				user.getMail()));

		Form form = content.ac(HTML.form());
		form.setAction(AdminLinkDefs.DELETE_USER_SUBMIT.getLink());
		form.setMethod(Method.POST);

		FormUtil.addHidden(form, "user", sUserId);

		BootstrapForms forms = new BootstrapForms();
		forms.addSubmit(form, ContextualType.DANGER, false, "Delete");
	}

}
