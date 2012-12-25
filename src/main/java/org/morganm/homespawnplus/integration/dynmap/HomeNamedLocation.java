/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (c) 2012 Mark Morgan.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Contributors:
 *     Mark Morgan - initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package org.morganm.homespawnplus.integration.dynmap;

import org.bukkit.Location;
import org.morganm.homespawnplus.entity.Home;
import org.morganm.homespawnplus.server.api.ConfigurationSection;
import org.morganm.homespawnplus.server.bukkit.BukkitLocation;

/**
 * @author morganm
 *
 */
public class HomeNamedLocation implements NamedLocation {
	private final Home home;
	private String name;
	
	public HomeNamedLocation(final Home home) {
		this.home = home;
	}

	@Override
	public Location getLocation() {
	    org.morganm.homespawnplus.server.api.Location homeLocation = home.getLocation();
	    if( homeLocation instanceof BukkitLocation )
	        return ((BukkitLocation) homeLocation).getBukkitLocation();
	    else
	        return null;
	}

	/** Home "names" on the map are just the player name.
	 * 
	 */
	@Override
	public String getName() {
		if( name == null ) {
			if( home.getName() != null )
				name = home.getPlayerName() + ":" + home.getName();
			else
				name = home.getPlayerName();
		}

		return name;
	}

	@Override
	public String getPlayerName() {
		return home.getPlayerName();
	}
	
	@Override
	public boolean isEnabled(ConfigurationSection section) {
		if( home.isDefaultHome() )
			return true;
		if( home.isBedHome() && section.getBoolean("include-bed-home") )
			return true;
		if( section.getBoolean("include-named-homes") )
			return true;

		// if it hasn't been true yet, then we're not supposed to show it
		return false;
	}
}
