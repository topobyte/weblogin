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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.topobyte.cachebusting.CacheBusting;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.components.Expand;
import de.topobyte.jsoup.bootstrap4.components.Menu;
import de.topobyte.jsoup.bootstrap4.components.MenuBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.Div;
import de.topobyte.jsoup.components.Img;
import de.topobyte.jsoup.components.UnorderedList;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.pagegen.core.LinkResolver;
import de.topobyte.weblogin.links.AdminLinkDefs;
import de.topobyte.weblogin.links.LoginLinkDefs;
import de.topobyte.weblogin.links.UserLinkDefs;
import de.topobyte.weblogin.realm.SystemUser;
import de.topobyte.weblogin.testing.Website;
import de.topobyte.weblogin.testing.links.LinkDefs;
import de.topobyte.webpaths.WebPaths;

public class MainMenu
{

	public static Menu create(LinkResolver resolver)
	{
		MenuBuilder builder = new MenuBuilder();
		builder.setExpand(Expand.MD).setFixed(true);
		Menu menu = builder.create();

		menu.addClass("shadow-sm");

		A brand = a("/");

		Img image = HTML.img(
				"/" + WebPaths.get(CacheBusting.resolve("images/icon.svg")));
		image.attr("height", "40px");
		image.attr("style", "padding-right:15px");
		brand.ap(image);

		brand.appendText(Website.TITLE);

		menu.addBrand(brand);
		menu.addToggleButton();

		Div collapse = menu.addCollapsible();
		UnorderedList main = menu.addSection(collapse);
		UnorderedList right = menu.addSectionRight(collapse);

		menu.addLink(main, LinkDefs.SOMETHING.getLink(), "Something", false);
		addLogin(menu, right, resolver);

		menu.addLink(right, LinkDefs.ABOUT.getLink(), "About", false);

		return menu;
	}

	private static void addLogin(Menu menu, Element<?> section,
			LinkResolver resolver)
	{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			if (subject.hasRole("admin")) {
				menu.addLink(section, AdminLinkDefs.INDEX.getLink(),
						"Admin-Area", false);
			}
			SystemUser user = (SystemUser) subject.getPrincipal();
			if (user != null && user.getUsername().equals("root")) {
				menu.addLink(section, "/admin/root", "Root-Area", false);
			}

			menu.addLink(section, UserLinkDefs.PREFERENCES.getLink(),
					"Preferences", false);
			menu.addLink(section, LoginLinkDefs.LOGOUT.getLink(), "Logout",
					false);
		} else {
			menu.addLink(section, LoginLinkDefs.LOGIN.getLink(), "Login",
					false);
		}
	}

}
