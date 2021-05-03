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

import de.topobyte.weblogin.LinkResolverContentGeneratable;
import de.topobyte.weblogin.WebloginContentGeneratableFactory;
import de.topobyte.weblogin.WebloginContentGenerator;
import de.topobyte.webpaths.WebPath;

public class WebloginContentGeneratorFactory implements WebloginContentGeneratableFactory
{

	@Override
	public LinkResolverContentGeneratable get(WebPath path, WebloginContentGenerator generator)
	{
		return new WebloginContentGeneratorImpl(path, generator);
	}

}