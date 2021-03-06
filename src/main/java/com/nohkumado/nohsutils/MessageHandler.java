/* $Id: MessageHandler.java,v 1.1 2005/10/29 10:26:40 bboett Exp $ -*- java -*-
 * Copyright (C) 2005 Bruno Boettcher
 * bboett@adlp.org
 * http://inforezo.u-strasbg.fr/~bboett
 *
 * All rights reserved
 *
 * Permission to use, copy, modify and distribute this material for
 * any purpose and without fee is hereby granted, provided that the
 * above copyright notice and this permission notice appear in all
 * copies, and that my name is not used in advertising or publicity 
 * pertaining to this material without my specific, prior written 
 * permission 
 *
 * I MAKE NO REPRESENTATIONS AND EXTENDS NO WARRANTIES,
 * EXPRESS OR IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR ANY PARTICULAR PURPOSE, AND THE WARRANTY AGAINST
 * INFRINGEMENT OF PATENTS OR OTHER INTELLECTUAL PROPERTY RIGHTS.  THE
 * SOFTWARE IS PROVIDED "AS IS", AND IN NO EVENT SHALL I  BE LIABLE FOR 
 * ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR 
 * CONSEQUENTIAL DAMAGES RELATING  TO THE SOFTWARE.
 */
/**
 * DESCRIPTION:
 *
 * @author Bruno Boettcher
 * @author Email: bboett@adlp.org
 */
package com.nohkumado.nohsutils;

import java.util.*;
import java.io.*;
import java.util.prefs.*;

/**
 * interface for all classes handling the mechanisms to haggle with different
 * sets of token- message systems, e.g. for internationalisation of programs
 *
 * @author Bruno Boettcher
 * @version $Revision: 1.1 $
 */
public class MessageHandler implements MessageHandlerInterface
{

  ResourceBundle msg;
  Locale currentLocale = Locale.getDefault();
  ConfigHandlerInterface config;
  Properties newTags = new Properties();
  boolean modified = false;

  /**
   *
   * @param n
   */
  public MessageHandler(ConfigHandlerInterface n)
  {
    super();
    config = n;
    load();
  }//public MessageHandler()
  //----------------------------------------------

  /**
   *
   * @return
   */
  @Override
  public ResourceBundle getI8nBoundle()
  {
    return (msg);
  }

  /**
   * save the database in the native format of the implementation
   */
  @Override
  public void save()
  {
    if (modified)
    {
      try
      {
        FileWriter fw = new FileWriter(new File(config.container().get("missingmesges", "missingmsges")));
        newTags.store(fw, "no comment");
        fw.close();
      }// try
      catch (IOException e)
      {
        System.out.println("MESSAGEHANDLER:SAVE FAILED " + e);
      }// catch(IOException e)
    }// if(modified)
  }// public void save()

  /**
   * load the default database nativeto the implementation
   */
  @Override
  public void load()
  {
    Preferences settings = config.container();
    Locale currentLocale = Locale.getDefault();

    if (settings.get("lang", null) != null)
    {
      String country = settings.get("country", null);
      String language = settings.get("lang", null);
      if (country == null)
      {
        country = language.toUpperCase();
      }
      else
      {
        country = country.toUpperCase();
      }
      currentLocale = new Locale(language, country);
    }// if(settings.get("lang",null) != null)«me
    /*String[] bundelNamesToTry = new String[]
    {
      settings.get("basename", "configuser"),
      "com/nohkumado/nohsutils/messages",
      "com.nohkumado.nohsutils.messages",
      "messages",
      "resources.messages",
    };
    
    for( String configName: bundelNamesToTry)
    {
       if(loadBundle(configName)) break; 
      
    }
    */
    if(!loadBundle("messages")) System.err.println("failed to load bundle");
  }// public void load()

  private boolean loadBundle(String configName)
  {
    boolean result = true;
    try
    {
      //System.out.println("loading BNDLE " + configName);
      msg = ResourceBundle.getBundle(configName);
    }// try
    catch (MissingResourceException g)
    {
      System.out.println("FAILED NO WAY TO FIND THE BNDLE " + configName);
      result = false;

    }// catch(MissingResourceException e)
    return result;
  }

  /**
   * a tag is missing in the db, add one per default, and save the base? TODO
   * should search for all languages and add to their respective files the new
   * tag...?
   *
   * @param m fnmae String the tag to add
   */
  @Override
  public void addTag(String m)
  {
    newTags.setProperty(m, m);
    modified = true;
  }// public void addTag(String m)

  /**
   *
   * @param key
   * @return
   */
  public String missingTag(String key)
  {
    try
    {
      return (newTags.getProperty(key));
    }// try
    catch (MissingResourceException e)
    {
      return (key);
    }// catch(MissingResourceException e)
  }//public String missingTag(String key)

  /**
   * set Locale set the locale for this app
   *
   * @param m
   */
  @Override
  public void setLocale(Locale m)
  {
    currentLocale = m;
  }// public void setLocale(Locale m)
}//public interface MessageHandler
