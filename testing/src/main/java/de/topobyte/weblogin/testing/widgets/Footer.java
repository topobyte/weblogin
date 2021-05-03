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

package de.topobyte.weblogin.testing.widgets;

import static de.topobyte.jsoup.HTML.a;
import static de.topobyte.jsoup.HTML.p;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.Container;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.UnorderedList;
import de.topobyte.jsoup.feather.Feather;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.testing.links.LinkDefs;

public class Footer extends Element<Footer>
{

	public Footer(LinkResolver resolver)
	{
		super("footer");
		attr("class", "footer");

		Container div = ac(Bootstrap.container());

		UnorderedList links = div.ac(HTML.ul());

		links.addItem(a(LinkDefs.IMPRINT.getLink(), "Imprint"));
		links.addItem(a(LinkDefs.PRIVACY_POLICY.getLink(), "Privacy Policy"));

		P p = div.ac(p().addClass("text-muted"));

		p.appendText("Made with ");
		p.ac(Feather.heart("1em"));
		p.appendText(" in Berlin");
	}

}
