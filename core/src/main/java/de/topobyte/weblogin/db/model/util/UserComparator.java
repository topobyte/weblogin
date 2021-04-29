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

package de.topobyte.weblogin.db.model.util;

import java.util.Comparator;

import org.apache.commons.lang3.ObjectUtils;

import de.topobyte.weblogin.db.model.User;

public class UserComparator implements Comparator<User>
{

	public static enum Field {
		ID,
		NAME,
		MAIL
	}

	private Field field;

	public UserComparator(Field field)
	{
		this.field = field;
	}

	@Override
	public int compare(User o1, User o2)
	{
		switch (field) {
		default:
		case ID:
			return Long.compare(o1.getId(), o2.getId());
		case MAIL:
			return ObjectUtils.compare(o1.getMail(), o2.getMail());
		case NAME:
			return ObjectUtils.compare(o1.getName(), o2.getName());
		}
	}

}
