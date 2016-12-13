/**
 * Id: QuitCommand.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME QuitCommand
 *
 * AUTHOR Bruno Boettcher <bboett at adlp.org>
 *
 * SEE ALSO no docu at the moment
 *
 * DESCRIPTION stops the exectuion of a shell oject
 *
 * SYNOPSIS
 *
 * after instantiation, execute it!
 *
 * COPYRIGHT and LICENCE
 *
 * Copyright (c) 2004 Bruno Boettcher
 *
 * QuitCommand.java is free software; you can redistribute it and/or modify it
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

import com.nohkumado.nohsutils.*;

/**
 *
 * @author nohkumado
 */
public class QuitCommand extends Command implements CommandI
{

  /**
   * CTOR
   *
   * Build up a reference
   *
   * @param s
   */
  public QuitCommand(ShellI s)
  {
    super(s,"quit");
  }// public Command()

  /**
   *
   * @param s
   * @param n
   * @param struct
   */
  public QuitCommand(ShellI s, String n)
  {
    super(s, n);
  }// public QuitCommand()

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
    shell.exit();
    return (shell.msg("SHELL_EXITING") + "\n");
  }//end execute

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
    return (shell.msg("TYPE") + " " + shell.msg("QUIT_CMD") + " " + shell.msg("to exit") + "\n");
  }//end help
  //make a copy of this object

  @Override
  public Object clone()
  {
    //beware! shallow copy! if you command has some arrays or other deep structures, only the ref will be copied!
    QuitCommand cloned = (QuitCommand) super.clone();
    //  QuitCommand cloned = new QuitCommand(shell);
    //cloned.type = type;
    //cloned.name = name;
    //cloned.group = group;
    //cloned.messageHandler = messageHandler;
    //cloned.shell = shell;
    return cloned;
  }//public Object clone()
}//public class Command
