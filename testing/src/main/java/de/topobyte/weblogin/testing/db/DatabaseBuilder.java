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

package de.topobyte.weblogin.testing.db;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.luqe.jdbc.database.Database;
import de.topobyte.luqe.jdbc.database.SqliteDatabase;
import de.topobyte.shiro.AuthInfoGenerator;
import de.topobyte.weblogin.LoginDao;
import de.topobyte.weblogin.LoginTables;
import de.topobyte.weblogin.testing.TestData;
import de.topobyte.weblogin.testing.TestUser;

public class DatabaseBuilder
{

	final static Logger logger = LoggerFactory.getLogger(DatabaseBuilder.class);

	private Path pathDatabase;

	private SqliteDatabase database;
	private LoginDao dao;

	public DatabaseBuilder(Path pathDatabase)
	{
		this.pathDatabase = pathDatabase;
	}

	public void build() throws DatabaseBuildingException, SQLException,
			QueryException, IOException
	{
		logger.info("loading driver");
		loadDriver();
		logger.info("initializing connection");
		database = new SqliteDatabase(pathDatabase);
		logger.info("initializing schema");
		initSchema();
		logger.info("initializing DAO");
		initDao();
		logger.info("inserting data");
		insertData();
		logger.info("closing connection");
		database.closeConnection(true);
		logger.info("done");
	}

	private void loadDriver() throws DatabaseBuildingException
	{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new DatabaseBuildingException("sqlite driver not found", e);
		}
	}

	private void initSchema() throws QueryException
	{
		createTables(database);
	}

	private void createTables(Database database)
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());

		List<Table> tables = Arrays.asList(LoginTables.USERS,
				LoginTables.LOGIN);

		List<String> createStatements = new ArrayList<>();

		for (Table table : tables) {
			createStatements.add(qb.create(table, true));
		}

		for (String statement : createStatements) {
			logger.info(statement);
		}

		try {
			for (String statement : createStatements) {
				database.getConnection().execute(statement);
			}

			database.getJdbcConnection().commit();
		} catch (QueryException | SQLException e) {
			logger.error("error while creating tables", e);
		}
	}

	private void initDao() throws QueryException
	{
		dao = new LoginDao(database.getConnection());
	}

	private void insertData() throws IOException, QueryException, SQLException
	{
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		for (TestUser user : TestData.getUsers()) {
			dao.createUser(user.getUser(), user.getEmail(),
					AuthInfoGenerator.generate(rng, user.getPassword()));
		}
	}

}
