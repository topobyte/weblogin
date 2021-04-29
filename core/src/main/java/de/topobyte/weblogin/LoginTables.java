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

package de.topobyte.weblogin;

import de.topobyte.jsqltables.table.ColumnClass;
import de.topobyte.jsqltables.table.ColumnExtension;
import de.topobyte.jsqltables.table.Table;

public class LoginTables
{

	public static Table USERS = new Table("users");
	public static Table LOGIN = new Table("login");

	public static String ID_COLUMN_NAME = "id";

	static {
		USERS.addColumn(ColumnClass.LONG, ID_COLUMN_NAME,
				ColumnExtension.PRIMARY_AUTO_INCREMENT);
		USERS.addColumn(ColumnClass.VARCHAR, "name");
		USERS.addColumn(ColumnClass.VARCHAR, "mail");

		LOGIN.addColumn(ColumnClass.LONG, ID_COLUMN_NAME);
		LOGIN.addColumn(ColumnClass.VARCHAR, "salt");
		LOGIN.addColumn(ColumnClass.VARCHAR, "pass");
	}

}
