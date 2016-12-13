/**
 * Id:
 *
 * SYNOPSIS
 *
 * after instantiation, execute it!
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  SetCommand.java is free software; you can redistribute it and/or modify it under
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

import com.nohkumado.nohsutils.*;
import java.util.*;
import java.util.regex.*;
//import com.gnu.utils.*;

/**
 * changes a settings of the shell
 *
 * @author nohkumado
 */
public class SetCommand extends Command implements CommandI
{

  protected String value = "";

  /**
   * CTOR
   *
   * Build up a reference
   *
   * @param s
   */
  public SetCommand(ShellI s)
  {
    super(s, "set");
  }// public Command()

  /**
   *
   * @param s
   * @param n
   */
  public SetCommand(ShellI s, String n)
  {
    super(s, n);
  }// public SetCommand()

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
    StringBuilder result = new StringBuilder();
    //System.out.println("set checking '" + name + "'");
    if (!"".equals(name))
    {
      //System.out.println("set name valid");
      result.append(name).append(" : ");
      if (!"".equals(value))
      {
        //System.out.println("set value valid");
        shell.set(name, value);
      }
      if (shell.ressource(name) != null)
      {
        //System.out.println("set ressource present");
        result.append(shell.ressource(name)).append("\n");
      }
      else
      {
        //System.out.println("set no ressource present");
        result.append(shell.get(name)).append("\n");
      }
    }// if(value != "")
    else
    {
      //System.out.println("set name invalid, issuing list");
      result.append(shell.msg("VARIABLE_LIST")).append("\n").append(shell.msg("ENVIRONMENT")).append("\n");
      HashMap<String, Object> environment = (HashMap<String, Object>) shell.ressource(null);
      for (String argName : environment.keySet())
      {
        result.append(argName).append(" : ").append(environment.get(argName)).append("\n");
      } // for(Iterator<String> e = environment.keySet().iterator(); e.hasNext();)
      //result.append(shell.msg("VARIABLES")).append("\n");
      

      result.append(shell.msg("PREFERENCES")).append(" ");

      result.append(shell.listPrefs());

      result.append(shell.msg("ENDLIST")).append("\n");
    }// else
    return (result.toString());
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
    //System.out.print("entering parse: '" + line + "'\n");
    //TODO need to be able to set differently env vars and local vars and need to be able to destroy env vars or local vars
    Pattern pattern = Pattern.compile("^(\\S+)\\s+(.*)$");
    Matcher matcher = pattern.matcher(line);
    Pattern patternWoA = Pattern.compile("^(\\S+)");
    Matcher matcherWoA = patternWoA.matcher(line);
    if (matcher.find())
    {
      name = matcher.group(1);
      value = matcher.group(2);
    }//if(matcher.find())
    else if (matcherWoA.find())
    {
      name = matcherWoA.group(1);
      value = "";
    }//if(matcher.find())
    else
    {
      name = "";
      value = "";
    }
    //System.out.print("parsed: '" + line + "' into (" + name + "," + value + ")\n");
    return ("");
  }// public String parse(String line)«»

  /**
   * instead of parsing the options, give them directly, eg when invoking a
   * command from the program code directly
   *
   * @param parms the hashtable with the options
   */
  @Override
  public void setParameters(HashMap<String, Object> parms)
  {
    if (parms.containsKey("name"))
    {
      name = (String) parms.get("name");
    }
    if (parms.containsKey("value"))
    {
      name = (String) parms.get("value");
    }
  }// public void setParameters(HashMap<String,Object> parms)«»

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
    return (shell.msg("value") + " " + shell.msg("TO_SET_A_VALUE") + "\n");
  }//end help
  //make a copy of this object

  @Override
  public Object clone()
  {
    //beware! shallow copy! if you command has some arrays or other deep structures, only the ref will be copied!
    SetCommand cloned = (SetCommand) super.clone();
    //  SetCommand cloned = new SetCommand(shell);
    //cloned.type = type;
    //cloned.name = name;
    //cloned.group = group;
    //cloned.messageHandler = messageHandler;
    //cloned.shell = shell;
    return cloned;
  }//public Object clone()
}//public class Command
