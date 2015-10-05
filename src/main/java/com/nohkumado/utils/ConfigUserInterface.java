/** Id: ConfigUserInterface.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME ConfigUserInterface 
 *
 * AUTHOR Bruno Böttcher <bboett at adlp.org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * SYNOPSIS
 *
 * subclass  thing to get the setting method 
 *
 * DESCRIPTION 
 *
 * This class gives access to the settings setter/getter that uses a local or
 * central repository (a hash at the moment) for the settings of an object
 * 
 * COPYRIGHT and LICENCE
 * 
 *  Copyright (c) 2004 Bruno Boettcher
 * 
 *  ConfigUserInterface.java is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; version 2
 *  of the License.

 *  This program is distributed in the hope that it will be importful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package com.gnu.utils;

import java.util.*;
import java.util.regex.*;
import java.util.prefs.*;
import java.math.*;

public interface ConfigUserInterface extends MessageUserInterface,Cloneable
{

    /**

      new, Constructor


      The constructor of a utils::configUser object, initializes a number of
      datastructures that will be used later, creates a blessed reference

arguments :
recognized instances of: utils::configHandler, utils::messageHandler
in any order
first hash: settings in hash form
second hash: template for  object

     * @param config 
     * @param table 
*/
    //public ConfigUserInterface(ConfigHandlerInterface config, MessageHandlerInterface table);

    //public ConfigUserInterface();
    /**

      setting 

      setter/getter for the settings, to be able to choose any format for those
      settings without disturbing the rest of the program+++

     * @param key the token correspnding to the var name
     * @param value its value
     TODO should mirror/delegate the system of the Preferences object
     with getInt etc
*/
    public Object setting(String key, Object value);
    /**

      setting 

      setter/getter for the settings, to be able to choose any format for those
      settings without disturbing the rest of the program+++

     * @param key the token correspnding to the var name
*/
    public Object setting(String key);
    /**

      est

      set the type of  object, erases subsetting!

     * @param argument the new value
*/
    public void est(String argument);
    /**

      est

      get the type of  object

     * @return 
    */
    public String est();
    /**

      setConfHandler

      set the reference on the config handler, allows the element to retrieve its
      specific parts of the settings


     * @param handler 
    */
    public void setConfHandler(ConfigHandlerInterface handler);
    /**

      getConfHandler

      set the reference on the config handler, allows the element to retrieve its
      specific parts of the settings


*/
    public ConfigHandlerInterface getConfHandler();
    /** 
     * 
     */
    public String version();
    /**

      init

      initialisation, notamment on lui passe le système de messages

*/
    public Preferences init(Preferences args);
    /**

      type

      setter/getter for the type of  object, useful forthe section of settings
      thing fetches as subsettings

    */
    public void type(String argument);
    /**

      type

      setter/getter for the type of  object, useful forthe section of settings
      thing fetches as subsettings

    */
    public String type();
}//public class ConfigUserInterface implements Cloneable
