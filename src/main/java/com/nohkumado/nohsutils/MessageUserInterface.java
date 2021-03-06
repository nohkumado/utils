/* 
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
package com.nohkumado.nohsutils;


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

  /**
   *
   * @param m
   * @return
   */
  public String msg(String m);

  /**
   *
   * @param m
   */
  public void setI8nHandler(MessageHandlerInterface m);

  /**
   *
   * @return
   */
  public MessageHandlerInterface getI8nHandler();
}//public interface MessageUserInterface

