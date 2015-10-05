/* $Id: MessageHandlerInterface.java,v 1.1 2005/10/29 10:26:40 bboett Exp $ -*- java -*-
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

/** DESCRIPTION:
 * 
 * @author Bruno Boettcher
 * @author Email: bboett@adlp.org
 */
package com.gnu.utils;
import java.util.*;


/** 
 * interface for all classes handling the mechanisms to haggle with different sets of
 * token- message systems, e.g. for internationalisation of programs
 * 
 * @author Bruno Boettcher
 * @version $Revision: 1.1 $
 */
public interface MessageHandlerInterface
{
  //----------------------------------------------
  
  public ResourceBundle getI8nBoundle();
  /** 
   * save the database in the native format of the implementation 
   */
  public void save();
  
  /** 
   * load the default database nativeto the implementation 
   */
  public void load();
  
  /** 
   * a tag is missing in the db, add one per default, and save the base?
   TODO should search for all languages and add to their respective files the new tag...?
   * 
   * @param fnmae  String the tag to add
   */
    public void addTag(String m);
    public void setLocale(Locale m);
}//public interface MessageHandlerInterface

