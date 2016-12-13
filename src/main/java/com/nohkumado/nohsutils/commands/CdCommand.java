/**
 * Id:
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  CdCommand.java is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; version 2 of the License.
 *
 *  This program is distributed in the hope that it will be importful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.nohkumado.nohsutils.commands;

import java.io.*;
import com.nohkumado.nohsutils.*;

/**
 *
 * @author nohkumado
 */
public class CdCommand extends Command implements Cloneable, CommandI
{

  protected String path = "";

  /**
   * CTOR
   *
   * Build up a reference
   *
   * @param s
   */
  public CdCommand(ShellI s)
  {
    super(s, "cd");
  }// public Command()

  /**
   *
   * @param s
   * @param n
   * @param struct
   */
  public CdCommand(ShellI s, String n)
  {
    super(s, n);
  }// public CdCommand()

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
    if (path != null && path.length() > 0)
    {

      File newDir = new File(path);
      if (newDir.exists())
      {
        if (newDir.isDirectory())
        {
          shell.set("pwd", newDir.getAbsolutePath());
        }
        else
        {
          result += shell.msg("NOT_A_DIR");
        }
      }// if(newDir.exists())
      else
      {
        result += shell.msg("DOES_NOT_EXIST");
      }
    }// if(line != null && line.length() > 0)
    else
    {
      result += shell.msg("PROVIDE_A_DIR_TO_ENTER");
    }

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
    /*if(line.matches(".."))
      {
	String pwd = (String)shell.get("pwd");
        String sep = System.getProperty("file.separator");
	String[] result = pwd.split(sep);
	if(result.length > 1)
	{
	  pwd = "";
	  for (int x=0; x<result.length-1; x++) pwd += result[x]+sep;
	}// if(result.lenght > 1)
	path = pwd;
      }// if(line.matches(".."))
      else path = line;
     */

    if (line.length() <= 0)
    {
      if (shell.get("home") != null)
      {
        path = shell.get("home");
      }
      else
      {
        path = System.getProperty("user.dir");
      }
      return ("");
    }
    String pwd = shell.get("pwd");
    if (pwd == null || pwd.length() <= 0 || "pwd".equals(pwd))
    {
      pwd = System.getProperty("user.dir");
      shell.set("pwd", pwd);
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
    return (new StringBuilder().append(shell.msg("CD_CMD")).append(" ").append(shell.msg("PATH")).append("\n")).toString();
  }//end help
  //make a copy of this object

  @Override
  public Object clone()
  {
    //beware! shallow copy! if you command has some arrays or other deep structures, only the ref will be copied!
    CdCommand cloned = (CdCommand) super.clone();

    //cloned.type = type;
    //cloned.name = name;
    //cloned.group = group;
    //cloned.messageHandler = messageHandler;
    //cloned.shell = shell;
    return cloned;
  }//public Object clone()
}//public class Command
