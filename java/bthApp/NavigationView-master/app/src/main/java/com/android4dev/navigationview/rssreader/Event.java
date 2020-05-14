/*
 *  This file is distributed under the GPL3 license.
 *  Please see the file LICENSE that should be present in this distribution.
 *
 *  Copyright 2011
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  
 *  Maintained by
 *   Jonas Hellström <jonas@if-then-else.se>
 *   Emma Ekberg <emma.ekberg@gmail.com>
 *  since 2011
 */

package com.android4dev.navigationview.rssreader;

import com.android4dev.navigationview.BSKLOG;

public class Event extends RssItem implements BSKLOG {
	@Override
	public int compareTo(RssItem item) {
		return getPubDate().compareTo(item.getPubDate());
	}
}