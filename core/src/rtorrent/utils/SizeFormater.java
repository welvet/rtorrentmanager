package rtorrent.utils;

import java.text.NumberFormat;

/*
 * Copyright (C) 2001-2003 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


/**
 * General purpose utilities functions.
 *
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
public class SizeFormater {
    public static String formatSize(long longSize, int decimalPos) {
        NumberFormat fmt = NumberFormat.getNumberInstance();
        if (decimalPos >= 0) {
            fmt.setMaximumFractionDigits(decimalPos);
        }
        final double size = longSize;
        double val = size / (1024 * 1024 * 1024);
        if (val > 1)
            return fmt.format(val).concat("Gb");
        val = size / (1024 * 1024);
        if (val > 1) {
            return fmt.format(val).concat("Mb");
        }
        val = size / 1024;
        return fmt.format(val).concat("Kb");
    }
}