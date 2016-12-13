/*
 * Copyright (C) 2016 NohKumado <nohkumado@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nohkumado.nohsutils.test;

import com.nohkumado.nohsutils.Shell;

/**
 *
 * @author NohKumado <nohkumado@gmail.com>
 */
public class LaunchShell
{
  public static void main(String[] args)
  {
    //java.util.ResourceBundle.getBundle("com/nohkumado/nohsutils/messages").getString("")
    Shell shell = new Shell();
    shell.run();
  }
          
}
