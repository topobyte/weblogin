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

package de.topobyte.weblogin.util;

import java.sql.SQLException;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.db.model.User;

public class AccountsUtil
{

	public static boolean isUsernameReserved(String username)
	{
		return username.equals("root") || username.equals("admin");
	}

	public static boolean isUsernameTaken(LoginDao dao, String username)
			throws SQLException, QueryException
	{
		User user = dao.findUserByName(username);
		return user != null;
	}

	public static boolean isEmailTaken(LoginDao dao, String email)
			throws SQLException, QueryException
	{
		User user = dao.findUserByEmail(email);
		return user != null;
	}

}
