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

package de.topobyte.weblogin.validation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.topobyte.weblogin.validation.UsernameValidator.Status;

@RunWith(Parameterized.class)
public class TestUsernameValidator
{

	@Parameters
	public static Object[][] params()
	{
		return new Object[][] { //
				new Object[] { "", false }, //
				new Object[] { "a", false }, //
				new Object[] { "ab", false }, //
				new Object[] { " ab", false }, //
				new Object[] { "ab ", false }, //
				new Object[] { "a b", false }, //
				new Object[] { "abc", true }, //
				new Object[] { "a-c", true }, //
				new Object[] { "---", false }, //
				new Object[] { "a@b.com", false }, //
				new Object[] { "a@b.com", false }, //
				new Object[] { "a@b.c.com", false }, //
				new Object[] { "a@foo.bar.com", false }, //
				new Object[] { "a123", true }, //
				new Object[] { "bob1", true }, //
				new Object[] { "1bob", false }, //
				new Object[] { "b1ob", true }, //
		};
	}

	@Parameter(0)
	public String name;

	@Parameter(1)
	public boolean valid;

	@Test
	public void test()
	{
		Status status = UsernameValidator.check(name);
		if (!status.isValid()) {
			System.out.println(status.getMessage());
		}
		Assert.assertEquals(status.isValid(), valid);
	}

}
