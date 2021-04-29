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

import de.topobyte.shiro.AuthInfo;
import lombok.Getter;

public class UserInfo
{

	@Getter
	private SystemUser user;

	@Getter
	private AuthInfo authInfo;

	public UserInfo(SystemUser user, AuthInfo authInfo)
	{
		this.user = user;
		this.authInfo = authInfo;
	}

}
