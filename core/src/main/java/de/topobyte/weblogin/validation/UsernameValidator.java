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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

public class UsernameValidator
{

	final static Logger logger = LoggerFactory
			.getLogger(UsernameValidator.class);

	public static class Status
	{

		@Getter
		private boolean valid = false;
		@Getter
		private String message;

	}

	public static Status check(String name)
	{
		Status status = new Status();
		check(status, name);
		return status;
	}

	private static void check(Status status, String name)
	{
		Pattern pattern = Pattern
				.compile("([A-Za-z])([A-Za-z0-9-_]+)([A-Za-z0-9])");
		Matcher matcher = pattern.matcher(name);

		if (!matcher.matches()) {
			status.message = "Invalid characters";
			return;
		}

		if (name.isEmpty()) {
			status.message = "Name is empty";
			return;
		}
		if (name.length() < 3) {
			status.message = "Name is too short";
			return;
		}

		status.message = "All checks passed";
		status.valid = true;
	}

}
