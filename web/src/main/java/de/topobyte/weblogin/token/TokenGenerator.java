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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TokenGenerator
{

	private static List<Character> chars = new ArrayList<>();
	static {
		for (char c = 'a'; c <= 'z'; c++) {
			chars.add(c);
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			chars.add(c);
		}
		for (char c = '0'; c <= '9'; c++) {
			chars.add(c);
		}
	}

	private Random random = new Random();

	public String generate()
	{
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			int n = random.nextInt(chars.size());
			buffer.append(chars.get(n));
		}
		return buffer.toString();
	}

}
