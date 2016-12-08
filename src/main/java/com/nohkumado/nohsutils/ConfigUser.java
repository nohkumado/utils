/**
 * Id: ConfigUser.java,v 1.4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME ConfigUser
 *
 * AUTHOR Bruno Böttcher <bboett at adlp.org>
 *
 * SEE ALSO no docu at the moment
 *
 * SYNOPSIS
 *
 * subclass thing to get the setting method
 *
 * DESCRIPTION
 *
 * This class gives access to the settings setter/getter that uses a local or
 * central repository (a hash at the moment) for the settings of an object
 *
 * COPYRIGHT and LICENCE
 *
 * Copyright (c) 2004 Bruno Boettcher
 *
 * ConfigUser.java is free software; you can redistribute it and/or modify it
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
package com.nohkumado.nohsutils;

import java.util.*;
import java.util.prefs.*;

//public class ConfigUser<Item> extends Item<Item>
/**
 *
 * @author nohkumado
 */
public class ConfigUser extends MessageUser implements ConfigUserInterface, Cloneable
{

  protected Preferences settings = null;
  //TODO don't know how to tranlsate in Pref system protected Preferences subsettings = null;
  protected ConfigHandlerInterface configHandler = null;
  protected MessageHandlerInterface traducs = null;
  protected String type = "com.gnu.utils.ConfigUser";
  protected String configName = "ConfigUser";

  /**
   *
   * new, Constructor
   *
   *
   * The constructor of a utils::configUser object, initializes a number of
   * datastructures that will be used later, creates a blessed reference
   *
   * arguments : recognized instances of: utils::configHandler,
   * utils::messageHandler in any order first hash: settings in hash form second
   * hash: template for object
   *
   * @param config
   * @param table
   */
  public ConfigUser(ConfigHandlerInterface config, MessageHandlerInterface table)
  {
    if (table != null)
    {
      setI8nHandler(table);
    }// if(table != null)
    if (config != null)
    {
      setConfHandler(config);
      settings = config.container();
    }// if(config != null)
    //TODO should check what to do here...
    //subsettings = new HashMap<String,Object>();
    //settings.put("subsettings", subsettings);
  }// public ConfigUser(ConfigHandlerInterface config)

  /**
   * null CTOR
   */
  public ConfigUser()
  {
  }// public ConfigUser(ConfigHandlerInterface config)

  /**
   *
   * setting    *
   * setter/getter for the settings, to be able to choose any format for those
   * settings without disturbing the rest of the program+++
   *
   * @param key the token correspnding to the var name
   * @param value its value
   * @return
   */
  @Override
  public Object setting(String key, Object value)
  {
    if (value == null)
    {
      if (key == null)
      {
        return settings;
      }
      return settings.get(key, null);
    }// if (key)
    //if(settings.get(key,null) != null)
    //{
    if (value instanceof String)
    {
      settings.put(key, (String) value);
    }
    else if (value instanceof Integer)
    {
      settings.putInt(key, ((Integer) value));
    }
    else
    {
      settings.put(key, "" + value);
    }
    return (settings.get(key, null));
    //}// if
    //else
    //{
    //  if(value instanceof String) subsettings.put(key,(String) value);
    //  else if(value instanceof Integer) subsettings.putInt(key,((Integer) value).intValue());
    //  else subsettings.put(key , ""+value);
    //  return(subsettings.get(key,null));
    //}// else
  }//setting

  /**
   *
   * setting    *
   * setter/getter for the settings, to be able to choose any format for those
   * settings without disturbing the rest of the program+++
   *
   * @param key the token correspnding to the var name
   * @return
   */
  @Override
  public Object setting(String key)
  {
    return (setting(key, null));
  }// public void setting(String key)

  /**
   *
   * est
   *
   * set the type of object, erases subsetting!
   *
   * @param argument the new value
   */
  @Override
  public void est(String argument)
  {

    if (argument != null)
    {
      type = argument;
      //subsettings = new HashMap<String,Object>();
      //settings.put("subsettings", subsettings);
    }// if(argument)
  }//public void est

  /**
   *
   * est
   *
   * get the type of object
   *
   * @return
   */
  @Override
  public String est()
  {
    return type;
  }//public void est

  /**
   *
   * setConfHandler
   *
   * set the reference on the config handler, allows the element to retrieve its
   * specific parts of the settings
   *
   *
   * @param handler
   */
  @Override
  public void setConfHandler(ConfigHandlerInterface handler)
  {
    if (handler != null)
    {
      configHandler = handler;
      //HashMap<String,Object> backup = subsettings;
      settings = handler.container();

      //if(subsettings.get(est(),null) != null)
      //{
      //    //subsettings.put(est(), backup);
      //    //subsettings = (HashMap<String,Object>) subsettings.get(est());
      //}// 
      //else
      //{
      //    //subsettings = (HashMap<String,Object>)subsettings.get(est());
      //    for(String entry:  backup.keySet())
      //    {
      //        subsettings.put(entry, backup.get(entry));
      //    }//
      //}// else
    }// if(handler)
  }//end setConfHandler

  /**
   *
   * getConfHandler
   *
   * set the reference on the config handler, allows the element to retrieve its
   * specific parts of the settings
   *
   * @return
   */
  @Override
  public ConfigHandlerInterface getConfHandler()
  {
    return configHandler;
  }//end setConfHandler

  /**
   *
   * @return
   */
  @Override
  public String version()
  {
    return ("Revision: 1.6 ");
  }//public void version

  /**
   *
   * init
   *
   * initialisation, notamment on lui passe le système de messages
   *
   * @param args
   * @return
   */
  @Override
  public Preferences init(Preferences args)
  {
    ArrayList recognized = new ArrayList();
    //for(String key: args.keys())
    //{
    //  //TODO not possible with Preferences system
    //   //if(key.equals("i8nhandler"))
    //   //{
    //   //    setI8nHandler((MessageHandlerInterface) args.get(key,null));
    //   //    args.remove("i8nhandler");
    //   //}// if(key.equals("i8nhandler"))
    //   //else if(key.equals("config"))
    //   //{
    //   //    setConfHandler(  (ConfigHandlerInterface) args.get(key));
    //   //    args.remove("config");
    //   //}// else if(key.equals("config"))
    //}// for(String key: args.keySet())
    if (configHandler == null)
    {
      configHandler = new ConfigHandler(args);
      setConfHandler(configHandler);
    }
    return (args);
  }//public void init

  /**
   *
   * type
   *
   * setter/getter for the type of object, useful forthe section of settings
   * thing fetches as subsettings
   *
   * @param argument
   */
  @Override
  public void type(String argument)
  {
    if (argument != null)
    {
      type = argument;
    }// if(argument)
  }//public void type

  /**
   * type
   *
   * setter/getter for the type of object, useful forthe section of settings
   * thing fetches as subsettings
   *
   * @return
   */
  @Override
  public String type()
  {
    return (type);
  }//public void type

  /**
   *
   * parseArgs    *
   * parse the incoming arguments, if allowedkeys hash is present, it is used to
   * translate the keys to the sttings used in the program, otherwise the
   * keyname is used as parameter name+ Also if the allowedkeys hash is present
   * only the keys psecifiyed in there are allowed+    *
   * using the hash implies: the keys are still with the prepended '-''s, if
   * after the '-'s a no is detected it is thought as a negation setting if
   * there isn't a valid value following+
   *
   * TODO don't we have now a new option parser paradigm??
   *
   * @param argv
   * @return
   */
  protected Preferences parseArgs(String argv[])
  {
    String error = "";
    if (argv.length > 0)
    {
      for (int i = 0; i < argv.length; i++)
      {
        //String tmp = argv[i].toUpperCase().trim();
        String tmp = argv[i].trim();
        String next = "";
        //if((i+1) < argv.length) next = argv[i+1].toUpperCase().trim();
        if ((i + 1) < argv.length)
        {
          next = argv[i + 1].trim();
        }
        if (tmp.startsWith("-"))
        {
          //process switch 
          tmp = tmp.substring(1, tmp.length());
          if (next.startsWith("-"))
          {
            next = "true";
          }
          else
          {
            i++;
          }
          settings.put(tmp, next);
        }
        else
        {
          error += msg("couldn't handle") + " " + tmp + "\n";
        }// else  
      }// end for
      if (error.length() > 0)
      {
        settings.put("error", error);
      }
    }//end if( argv.length > 0)
    return (settings);
  }//protected void parseArgs(String argv[])
}//public class ConfigUser implements Cloneable
