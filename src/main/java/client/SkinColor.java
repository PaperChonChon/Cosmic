/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package client;

public enum SkinColor {
    LIGHT(0),
    TAN(1),
    DARK(2),
    PALE(3),
    BLUE_GRAY(4),
    GREEN(5),
    GHOSTLY(9),
    FAIR(10),
    CLAY(11),
    ALABASTER(12),
    PALE_GRAY(13),
    ROSY(15),
    FLUSHED(16),
    SOFT_LAVENDER(18),
    BLUSHING_LAVENDER(19),
    COWHIDE_BLACK(20),
    COWHIDE_PINK(21),
    COWHIDE_BROWN(22),
    COWHIDE_BEIGE(23),
    JADE_MARBLE(24),
    GOLD_MARBLE(25),
    SILVER_MARBLE(26),
    BRONZE_MARBLE(27),
    SPINEL(28),
    AMETHYST(29),
    ATHLETIC(30),
    APATITE(32);


    final int id;

    SkinColor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SkinColor getById(int id) {
        for (SkinColor l : SkinColor.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }
}
