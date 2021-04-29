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

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class TokenAuthenticationInfo implements AuthenticationInfo
{

	private static final long serialVersionUID = 1L;

	private String principal;

	public TokenAuthenticationInfo(String principal)
	{
		this.principal = principal;
	}

	@Override
	public Object getCredentials()
	{
		return null;
	}

	@Override
	public PrincipalCollection getPrincipals()
	{
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(principal, "token");
		return pc;
	}

}
