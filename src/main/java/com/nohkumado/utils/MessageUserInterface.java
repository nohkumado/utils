/* $Id: MessageUserInterface.java,v 1.1 2005/10/29 10:26:40 bboett Exp $ -*- java -*-
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


/** 
 * interface for all grid types
 * 
 * @author Bruno Boettcher
 * @version $Revision: 1.1 $
 */
//public class MessageUserInterface extends JComponent implements MouseListener 
public interface MessageUserInterface
{
  //----------------------------------------------
  public String msg(String m);
  public void setI8nHandler(MessageHandlerInterface m);
  public MessageHandlerInterface getI8nHandler();
}//public interface MessageUserInterface

