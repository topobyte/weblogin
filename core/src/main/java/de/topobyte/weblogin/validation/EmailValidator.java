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

public class EmailValidator
{

	final static Logger logger = LoggerFactory.getLogger(EmailValidator.class);

	public static class Status
	{

		@Getter
		private boolean valid = false;
		@Getter
		private String message;

	}

	public static Status check(String email)
	{
		Status status = new Status();
		check(status, email);
		return status;
	}

	private static void check(Status status, String email)
	{
		Pattern pattern = Pattern.compile("(.+)@(.+?)\\.([^.]+)");
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			status.message = "Pattern x@y.z not matched";
			return;
		}

		String name = matcher.group(1);
		String domain = matcher.group(2);
		String tld = matcher.group(3);
		logger.debug(String.format("name: '%s', domain: '%s', tld: '%s'", name,
				domain, tld));

		if (name.isEmpty()) {
			status.message = "Name is empty";
			return;
		}
		if (domain.isEmpty()) {
			status.message = "Domain is empty";
			return;
		}
		if (tld.length() < 2) {
			status.message = "TLD part is shorter than two";
			return;
		}

		status.message = "All checks passed";
		status.valid = true;
	}

}
