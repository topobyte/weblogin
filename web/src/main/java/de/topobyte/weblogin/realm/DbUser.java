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

import java.io.Serializable;

import de.topobyte.weblogin.db.model.User;
import lombok.Getter;

public class DbUser implements SystemUser, Serializable
{

	// Implement Serializable for Shiro
	private static final long serialVersionUID = -5807751935575411601L;

	@Getter
	private User user;

	public DbUser(User user)
	{
		this.user = user;
	}

	@Override
	public String getUsername()
	{
		return user.getName();
	}

}
