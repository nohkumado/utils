/** Id: PwdCommand.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME PwdCommand 
 *
 * AUTHOR Bruno Boettcher <bboett at adlp.org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * DESCRIPTION 
 * shows the actual working directory
 *
 * SYNOPSIS 
 *
 * after instantiation, execute it!
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  PwdCommand.java is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; version 2 of the License.
 *
 *  This program is distributed in the hope that it will be importful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE.  See the GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.nohkumado.nohsutils.commands;
import com.nohkumado.nohsutils.*;

/**
 *
 * @author nohkumado
 */
public class PwdCommand extends Command implements CommandI
{
  /**
    CTOR

    Build up a reference
   * @param s
   */
  public PwdCommand(ShellI s)
  {
    super(s,"pwd");
  }// public Command()

  /**
   *
   * @param s
   * @param n
   * @param struct
   */
  public PwdCommand(ShellI s,String n,MessageUser struct)
  {
    super(s,n);
  }// public PwdCommand()
  /**

    execute

    activate this command

 
   * @return 
   */
  @Override
  public String execute()
  {
    String result = "";
    String pwd = shell.get("pwd");
    if(pwd == null || pwd.length() <= 0 || "pwd".equals(pwd)) pwd = System.getProperty("user.dir");
    shell.set("pwd",pwd);
    return(pwd+"\n");
  }//end execute
  /**

    help

    issue the help message associated with this command

   */
  @Override
  public String help()
  {
    return(shell.msg("pwd")+" "+shell.msg("TO_DISPLAY_ACTUAL_WORKING_DIRECTORY")+"\n");
  }//end help
  /** 
   * copy this object 
   * 
   * @return 
   */
  @Override
  public Object clone()
  {
    //beware! shallow copy! if you command has some arrays or other deep structures, only the ref will be copied!
    PwdCommand cloned = (PwdCommand)super.clone();
    return cloned;
  }//public Object clone()
}//public class Command
