/** Id: Command.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME Command 
 *
 * AUTHOR Bruno Boettcher <bboett at adlp.org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * DESCRIPTION 
 *
 * a class of object that takes a plan and produces something
 * commands::Command object destined to be executed, and that will perform on
 * execution one action
 *
 * SYNOPSIS 
 *
 * after instantiation, execute it!
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  Command.java is free software; you can redistribute it and/or modify it under
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
package com.nohkumado.utils;
import java.util.*;
//import com.gnu.utils.*;

public class Command 
implements Cloneable, CommandI
{
    protected String type = null;
    protected String name = null;
    protected String group = "user";
    protected MessageUser messageHandler = null;
    protected ShellI shell = null;
    protected HashMap<String,Object> parameter ;


    /**
      CTOR

      Build up a reference

*/
    public Command(ShellI s)
    {
      shell = s;
    }// public Command()

    public Command(ShellI s, String n,MessageUser struct)
    {
      this(s);
        name = n;
        if(struct != null)
        {
            messageHandler = struct;
        }// if(struct != null)
    }// public Command()
    /** 
     * name
     *
     * return the name of this command
     * 
     * @return the name of this item as a string
     */
    public String name() { return(name); }// public String name()
    /** 
     * name
     *
     * set the name of this command
     * 
     * @return the name of this item as a string
     */
    public void name(String n) {name = n;}

    /**

      execute

      activate this command

     * @return 
     */
    public String execute()
    {
        return("Command::exe : abstract class no code\n");
    }//end execute
    /**

      help

      issue the help message associated with this command

     * @return the help message
*/
    public String help()
    {
        return(shell.msg("no help")+"\n");
    }//end help
    /** 
     * clone this command 
     * make a copy of this object
     * 
     * @return a copy of this object
     */
    public Object clone()
    {
      try
      {
	//beware! shallow copy! if you command has some arrays or other deep structures, only the ref will be copied!
	Command cloned = (Command)super.clone();
	return(cloned);
      }
      catch(CloneNotSupportedException e) {System.err.println("can't clone this"); e.printStackTrace();}
      return(null);
    }//public Object clone()
    /** 
     * parse the command line for evenutal other switches options etc 
     * 
     * @param line line woth ooptions but without the command name
     * @return the unparsed rest
     */
    public String parse(String line) { return(line);}
    /** 
     * instead of parsing the options, give them directly, eg when invoking a command from the program code directly
     * 
     * @param parms the hashtable with the options
     * @param parms 
     */
    public void setParameters(HashMap<String,Object> parms){ parameter = parms;};
}//public class Command
