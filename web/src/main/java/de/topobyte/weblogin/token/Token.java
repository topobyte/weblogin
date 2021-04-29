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

package de.topobyte.weblogin.token;

public class Token
{

	private long userId;
	private String token;
	private long type;

	public Token(long userId, String token, long type)
	{
		this.userId = userId;
		this.token = token;
		this.type = type;
	}

	public long getUserId()
	{
		return userId;
	}

	public String getToken()
	{
		return token;
	}

	public long getType()
	{
		return type;
	}

}
