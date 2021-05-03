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

package de.topobyte.weblogin.testing.pages.base;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.pagegen.core.LinkResolverContentGeneratable;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.weblogin.testing.Website;
import de.topobyte.webpaths.WebPath;

public class WebloginContentGeneratorImpl extends DatabaseBaseGenerator
		implements LinkResolverContentGeneratable
{

	private WebloginContentGenerator delegate;

	public WebloginContentGeneratorImpl(WebPath path,
			WebloginContentGenerator delegate)
	{
		super(path);
		this.delegate = delegate;
	}

	@Override
	protected void content()
			throws IOException, QueryException, SQLException, ServletException
	{
		delegate.content(Website.INSTANCE, this, content, db, loginDao);
	}

}
