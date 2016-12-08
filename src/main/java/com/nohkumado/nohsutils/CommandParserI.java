/** Id: CommandParserI.java,v 1+4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME CommandParserI 
 *
 * AUTHOR Bruno Boettcher <bboett at adlp+org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * DESCRIPTION 
 * takes a line of text and tries to split it by hash value
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  CommandParserI.java is free software; you can redistribute it and/or
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
import java.util.*;

/**
 *
 * @author nohkumado
 */
public  interface CommandParserI //extends com.gnu.jtrader.EventGeneratorI
{
  /** -------------------------- feed Command --------------------------
   *
   * @param cmds
   * @return 
   */
  public  HashMap<String,CommandI>  feedCmds(HashMap<String,CommandI> cmds);
  /** -------------------------- feed Command --------------------------
   *
   * @param cmds
   * @return 
   */
  public  HashMap<String,CommandI>  feedCmds(ArrayList<CommandI> cmds);
  /** -------------------------- setRessource --------------------------
   *
   * @param arg0
   */
  public void  setRessource (Properties arg0);
  /** -------------------------- formatProperties --------------------------
   *
   * @param arg0
   * @return 
   */
  public String  formatProperties (Properties arg0);
  /** -------------------------- parse --------------------------
   *
   * @param line
   * @return 
   */
  public ArrayList<CommandI> parse (String line);
   /** 
    * find a command, means try key completion if not found directly clone the command and return it
    * 
    * @param token 
    * @return 
    */
   public CommandI findCmd(String token);
  /** -------------------------- getIntString --------------------------
   * setter/getter a reference on the actual shell
   * @return 
   */
  public ShellI shell();

  /**
   *
   * @param s
   */
  public void shell(ShellI  s);
  /** -------------------------- getHelp --------------------------
   * compile the Help from the commands
   * @return 
   */
  public String help();
}
