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

package de.topobyte.weblogin.db.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class User implements Serializable
{

	// Implement Serializable for Shiro
	private static final long serialVersionUID = 7215344672518918315L;

	@Getter
	@Setter
	private long id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String mail;

	public User(long id, String name, String mail)
	{
		this.id = id;
		this.name = name;
		this.mail = mail;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id != other.id) {
			return false;
		}
		if (!Objects.equals(mail, other.mail)) {
			return false;
		}
		if (!Objects.equals(name, other.name)) {
			return false;
		}
		return true;
	}

}
