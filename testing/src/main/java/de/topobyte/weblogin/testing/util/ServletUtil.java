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

package de.topobyte.weblogin.testing.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;

import de.topobyte.jsoup.nodes.Element;
import de.topobyte.weblogin.testing.pages.main.ErrorGenerator;
import de.topobyte.webpaths.WebPath;

public class ServletUtil
{

	private static Consumer<Element<?>> generator(int code)
	{
		if (code == 404) {
			return content -> {
				ErrorUtil.write404(content);
			};
		}
		return content -> {
			ErrorUtil.writeError(content);
		};
	}

	public static void respond(int code, WebPath output,
			HttpServletResponse response, Void data) throws IOException
	{
		Consumer<Element<?>> contentGenerator = generator(code);
		respond(code, output, response, contentGenerator, data);
	}

	public static void respond(int code, WebPath output,
			HttpServletResponse response, Consumer<Element<?>> contentGenerator,
			Void data) throws IOException
	{
		response.setStatus(code);

		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		ErrorGenerator generator = new ErrorGenerator(output);
		generator.generate();
		Element<?> content = generator.getContent();

		contentGenerator.accept(content);

		Document document = generator.getBuilder().getDocument();
		writer.write(document.toString());

		writer.close();
	}

}
