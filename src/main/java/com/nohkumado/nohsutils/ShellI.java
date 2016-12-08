/** Id: ShellI.java,v 1+4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME ShellI 
 *
 * AUTHOR Bruno Boettcher <bboett at adlp+org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * DESCRIPTION 
 * a class of object that takes a plan and produces something
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  ShellI.java is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; version 2
 *  of the License+
 *
 *  This program is distributed in the hope that it will be importful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE+  See the
 *  GNU General Public License for more details+

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc+, 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA+
*/

package com.nohkumado.nohsutils;

//import com.gnu.utils.*;
import java.util.*;

/**
 *
 * @author nohkumado
 */
public interface ShellI  extends MessageHandlerInterface,MessageUserInterface
{
  /**

    init

    after instantiation initialisation
   * @return
   */
  public boolean init();
  /**

    process

    this Method is the one that catches the commands and interprets them
    should copy some parts of nSim:lineShellI concerning batches!
   * @param line
   * @return 
   */
  public String process(String line);
  /**

    process

    this Method is the one that catches the commands and interprets them
    should copy some parts of nSim:lineShellI concerning batches!

    this one is for internal use, when invoking commands through the shell programmately, so no command parsing is needed, parameters are in a hastable
   * @param line
   * @param parm
   * @return 
   */
  public String process(String line,HashMap<String,Object> parm);
  /**

    prompt

    build up the short help string
   * @return 
   */
  public String prompt();

  /**
   *
   * @param p
   */
  public void prompt(String p);
  /**

    exit

    quit ans close the shell


   */
  public void exit();

  /**
   *
   * @param endMsg
   */
  public void exit(String endMsg);
  /** 
   * ressources 
   *
   * equivalent to the environment variables of a shell....
   * 
   * @param envname 
   * @return  
   */
  public java.lang.Object ressource(String envname);
  
  /** 
   * local settings 
   * 
   * @param envname 
   * @return 
   */
  public java.lang.Object get(String envname);
  /** 
   * ressources 
   *
   * equivalent to the environment variables of a shell....
   * 
   * @param envname 
   * @param obj 
   */
  public void ressource(String envname, Object obj);
  /** 
   * prototype for a help function 
   * 
   * @return 
   */
  public String help();
  /**
      run: 
       event loop 
   */
  public void run();
  /**
    issue a statement....
   * @param n
   */
  public void print(String n);
  /**
    issue a question and expect a return
   * @param n
   * @return 
   */
  public String ask(String n);

  /**
   *
   * @param question
   * @param defaultValue
   * @return
   */
  public String ask(String question, String defaultValue);

  /**
   *
   * @param question
   * @param options
   * @return
   */
  public String ask(String question, HashMap<String,Object> options);

  /**
   *
   * @param n
   * @return
   */
  public String askNum(String n);

  /**
   *
   * @param question
   * @param defaultValue
   * @return
   */
  public String askNum(String question, String defaultValue);

  /**
   *
   * @param question
   * @param options
   * @return
   */
  public String askNum(String question, HashMap<String,Object> options);
  /**
    issue an error
   * @param errorMsg
   */
  public void error(String errorMsg);
  /**
     isRunning
     return if the shell is running or not
   * @return 
   */
   public boolean isRunning();
    /**
     *
     *  feedCmds
     *
     * @param cmds 
     * @return 
     */
    public HashMap<String,CommandI> feedCmds(HashMap<String,CommandI> cmds);
}//public class ShellI
