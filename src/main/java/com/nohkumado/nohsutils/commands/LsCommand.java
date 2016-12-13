/**
 * Id: LsCommand.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME LsCommand
 *
 * AUTHOR Bruno Boettcher <bboett at adlp.org>
 *
 * SEE ALSO no docu at the moment
 *
 * DESCRIPTION changes a settings of the shell
 *
 * SYNOPSIS
 *
 * after instantiation, execute it!
 *
 * COPYRIGHT and LICENCE
 *
 * Copyright (c) 2004 Bruno Boettcher
 *
 * LsCommand.java is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; version 2 of the License.
 *
 * This program is distributed in the hope that it will be importful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package com.nohkumado.nohsutils.commands;

import java.io.*;
import java.util.regex.*;
import com.nohkumado.nohsutils.*;

/**
 *
 * @author nohkumado
 */
public class LsCommand extends Command implements Cloneable, CommandI
{

  protected String path = "";
  protected FilenameFilter filter;

  /**
   * CTOR
   *
   * Build up a reference
   *
   * @param s
   */
  public LsCommand(ShellI s)
  {
    super(s, "ls");
  }// public Command()

  /**
   *
   * @param s
   * @param n
   * @param struct
   */
  public LsCommand(ShellI s, String n, MessageUser struct)
  {
    super(s, n);
  }// public LsCommand()

  /**
   *
   * execute
   *
   * activate this command
   *
   * @return
   */
  @Override
  public String execute()
  {
    String result = "";
    //TODO fetch PwdCommand from shell here would be more elegant
    String pwd = shell.get("pwd");

    if ("".equals(path))
    {
      path = pwd;
    }// if(value != "")

    File theDir = new File(path);
    if (theDir.exists() && theDir.isDirectory())
    {
      String[] choices = theDir.list(filter);
      for (String name : choices)
      {
        result += name + "\n";
      }//for(String name : choices) 
      return (result);
    }//if(theDir.exists() && theDir.isDirectory())

    return (result);
  }//end execute

  /**
   * parse a setting line with no parameter its prints the whole list with one
   * parameter it prints the value of that parameter with 2 parameters it
   * replaces the parameter
   *
   * @param line
   * @return
   */
  @Override
  public String parse(String line)
  {
    String pwd = shell.get("pwd");
    if (pwd == null || pwd.length() <= 0 || "pwd".equals(pwd))
    {
      pwd = System.getProperty("user.dir");
      shell.set("pwd", pwd);
    }
    path = "";
    if (line.length() <= 0)
    {

      path = pwd;

      return ("");
    }

    //	if (line.length() <= 0)
    File newpos;
    if (!line.startsWith("/"))
    {
      newpos = new File(pwd, line);
    }
    else
    {
      newpos = new File(line);
    }

    try
    {
      path = newpos.getCanonicalPath();
    } catch (IOException e)
    {

      shell.print("no such path :" + newpos.getAbsolutePath());
    }
    return ("");
  }// public String parse(String line)

  /**
   *
   * help
   *
   * issue the help message associated with this command
   *
   */
  @Override
  public String help()
  {
    return (new StringBuilder().append(shell.msg("LS")).append(" ").append(shell.msg("PATH")).append(" ").append(shell.msg("TO_DISPLAY_DIRECTORY")).append("\n")).toString();
  }//end help
  //make a copy of this object

  @Override
  public Object clone()
  {
    LsCommand cloned = (LsCommand) super.clone();
    return cloned;
  }//public Object clone()
}//public class Command
