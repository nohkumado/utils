/* $Id: MessageUser.java,v 1.1 2005/10/29 10:26:40 bboett Exp $ -*- java -*-
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
package com.nohkumado.utils;
import java.util.*;


/** 
 * interface for all grid types
 * 
 * @author Bruno Boettcher
 * @version $Revision: 1.1 $
 */
//public class MessageUser extends JComponent implements MouseListener 
public class MessageUser implements com.nohkumado.utils.MessageUserInterface
{
  protected MessageHandlerInterface messageHandler = null;
  protected ResourceBundle msg = null;
  //----------------------------------------------
  /** 
   * msg 
   *
   * here only a dummy until i backport the stuff i made in perl, fetching the messages
   * from an xml ressourcefile
   * 
   * @param m 
   * @return 
   */
  public String msg(String m)
  {
    String result = "";
    try
    {
      return(msg.getString(m));
    }// try
    catch(MissingResourceException e)
    {
      try
      {
	messageHandler.addTag(m);
      }// try
      catch(NullPointerException p)
      {
	System.out.println("MU::msg please provide a MessageHandler");
      }// catch(NullPointerException p)
    }// catch(MissingResourceException e)
    return(m);
  }// public String msg(String m)
  
  /** 
   * setter for the messageHandler 
   * 
   * @param m 
   */
  public void setI8nHandler(MessageHandlerInterface m)
  {
      messageHandler = m;
      try
      {
      msg = m.getI8nBoundle();
      }// try
      catch(NullPointerException p)
      {
	System.out.println("MU::msg please provide a MessageHandler with an according bundle");
      }// catch(NullPointerException p)
  }// public void setI8nHandler(MessageHandlerInterface m)
  
  /** 
   * getter for the messageHandler 
   * 
   * @return 
   */
  public MessageHandlerInterface getI8nHandler()
  {
      return(messageHandler);
  }// public MessageHandlerInterface getI8nHandler()
}//public interface MessageUser

