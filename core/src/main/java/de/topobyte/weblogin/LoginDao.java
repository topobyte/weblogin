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

import java.util.ArrayList;
import java.util.List;

import de.topobyte.jsqltables.dialect.Dialect;
import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.jsqltables.query.Delete;
import de.topobyte.jsqltables.query.Select;
import de.topobyte.jsqltables.query.Update;
import de.topobyte.jsqltables.query.order.OrderDirection;
import de.topobyte.jsqltables.query.order.SingleOrder;
import de.topobyte.jsqltables.query.select.AllColumn;
import de.topobyte.jsqltables.query.where.Comparison;
import de.topobyte.jsqltables.query.where.SingleCondition;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.shiro.AuthInfo;
import de.topobyte.weblogin.db.model.Login;
import de.topobyte.weblogin.db.model.User;

public class LoginDao
{

	private IConnection connection;
	private Dialect dialect = new SqliteDialect();

	public LoginDao(IConnection connection)
	{
		this.connection = connection;
	}

	public Dialect getDialect()
	{
		return dialect;
	}

	private User findUserByStatement(IPreparedStatement stmt)
			throws QueryException
	{
		IResultSet results = stmt.executeQuery();
		try {
			if (!results.next()) {
				return null;
			}
			long id = results.getLong(1);
			String name = results.getString(2);
			String mail = results.getString(3);
			return new User(id, name, mail);
		} finally {
			results.close();
		}
	}

	private IPreparedStatement sUserById;

	public User findUserById(long id) throws QueryException
	{
		if (sUserById == null) {
			String sql = LoginTables.USERS.constructSelectSingleStatement("id");
			sUserById = connection.prepareStatement(sql);
		}
		sUserById.setLong(1, id);
		return findUserByStatement(sUserById);
	}

	private IPreparedStatement sUserByMail;

	public User findUserByEmail(String mail) throws QueryException
	{
		if (sUserByMail == null) {
			String sql = LoginTables.USERS
					.constructSelectSingleStatement("mail");
			sUserByMail = connection.prepareStatement(sql);
		}
		sUserByMail.setString(1, mail);
		return findUserByStatement(sUserByMail);
	}

	private IPreparedStatement sUserByName;

	public User findUserByName(String name) throws QueryException
	{
		if (sUserByName == null) {
			String sql = LoginTables.USERS
					.constructSelectSingleStatement("name");
			sUserByName = connection.prepareStatement(sql);
		}
		sUserByName.setString(1, name);
		return findUserByStatement(sUserByName);
	}

	private Login findLoginByStatement(IPreparedStatement stmt)
			throws QueryException
	{
		IResultSet results = stmt.executeQuery();
		try {
			if (!results.next()) {
				return null;
			}
			long id = results.getLong(1);
			String salt = results.getString(2);
			String pass = results.getString(3);
			return new Login(id, pass, salt);
		} finally {
			results.close();
		}
	}

	private IPreparedStatement sLoginById;

	public Login findLoginById(long id) throws QueryException
	{
		if (sLoginById == null) {
			String sql = LoginTables.LOGIN.constructSelectSingleStatement("id");
			sLoginById = connection.prepareStatement(sql);
		}
		sLoginById.setLong(1, id);
		return findLoginByStatement(sLoginById);
	}

	public long createUser(String name, String mail, AuthInfo authInfo)
			throws QueryException
	{
		QueryBuilder qb = new QueryBuilder(dialect);

		String sqlUsers = qb.insert(LoginTables.USERS);
		IPreparedStatement stmtUsers = connection.prepareStatement(sqlUsers);

		stmtUsers.setString(1, null);
		stmtUsers.setString(2, name);
		stmtUsers.setString(3, mail);

		IResultSet results = stmtUsers.executeQuery();
		int id = results.getInt(1);
		results.close();
		stmtUsers.close();

		String sqlLogin = qb.insert(LoginTables.LOGIN);
		IPreparedStatement stmtLogin = connection.prepareStatement(sqlLogin);

		stmtLogin.setLong(1, id);
		stmtLogin.setString(2, authInfo.getSalt());
		stmtLogin.setString(3, authInfo.getHash());

		stmtLogin.execute();
		stmtLogin.close();

		return id;
	}

	public void updateLogin(long id, AuthInfo authInfo) throws QueryException
	{
		Update update = new Update(LoginTables.LOGIN);
		update.where(new SingleCondition(null, LoginTables.ID_COLUMN_NAME,
				Comparison.EQUAL));
		update.addColum("salt");
		update.addColum("pass");

		String sql = update.sql();

		IPreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, authInfo.getSalt());
		stmt.setString(2, authInfo.getHash());
		stmt.setLong(3, id);

		stmt.execute();
		stmt.close();
	}

	public void deleteUser(long id) throws QueryException
	{
		Delete deleteUsers = new Delete(LoginTables.USERS);
		deleteUsers.where(new SingleCondition(null, LoginTables.ID_COLUMN_NAME,
				Comparison.EQUAL));

		try (IPreparedStatement stmt = connection
				.prepareStatement(deleteUsers.sql())) {
			stmt.setLong(1, id);
			stmt.execute();
		}

		Delete deleteLogin = new Delete(LoginTables.LOGIN);
		deleteLogin.where(new SingleCondition(null, LoginTables.ID_COLUMN_NAME,
				Comparison.EQUAL));

		try (IPreparedStatement stmt = connection
				.prepareStatement(deleteLogin.sql())) {
			stmt.setLong(1, id);
			stmt.execute();
		}
	}

	public List<User> getUsers() throws QueryException
	{
		Select select = new Select(LoginTables.USERS);
		select.order(new SingleOrder(select.getMainTable(),
				LoginTables.ID_COLUMN_NAME, OrderDirection.ASC));
		String sql = select.sql();

		List<User> users = new ArrayList<>();

		IPreparedStatement stmt = connection.prepareStatement(sql);
		IResultSet results = stmt.executeQuery();
		while (results.next()) {
			long id = results.getLong(1);
			String name = results.getString(2);
			String mail = results.getString(3);
			users.add(new User(id, name, mail));
		}
		results.close();
		stmt.close();

		return users;
	}

	public User getUserById(long userId) throws QueryException
	{
		Select select = new Select(LoginTables.USERS);
		select.addSelectColumn(new AllColumn(select.getMainTable()));
		select.where(new SingleCondition(select.getMainTable(),
				LoginTables.ID_COLUMN_NAME, Comparison.EQUAL));
		String sql = select.sql();

		User user = null;

		IPreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, userId);

		IResultSet results = stmt.executeQuery();
		if (results.next()) {
			long id = results.getLong(1);
			String name = results.getString(2);
			String mail = results.getString(3);
			user = new User(id, name, mail);
		}
		results.close();
		stmt.close();

		return user;
	}

}
