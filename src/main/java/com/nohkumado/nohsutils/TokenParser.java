/*
 * Copyright (C) 2016 NohKumado <nohkumado@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nohkumado.nohsutils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tokenized parser
 *
 * @author NohKumado <nohkumado@gmail.com>
 */
public class TokenParser
{

  public static final String TAG = "TOKP";
  public static final int UNPARSED_ARGS = 1;

  protected CmdLineParser parentParser;
  Pattern pat_cmd_arg = Pattern.compile("^(\\S+)\\s+(.*)$");
  Pattern pat_semic1 = Pattern.compile("^(\\S+)\\s*;\\s*(.*)$");
  Pattern pat_semic = Pattern.compile("^(\\S+)\\s*(.*);\\s*(.*)$");

  protected String errorMsg = "";

  protected int errorCode = 0;
  protected String errorCmd = "";

  private static final int UNPARSABLE = 2;

  /**
   * CTOR
   *
   * @param p
   */
  public TokenParser(CmdLineParser p)
  {
    parentParser = p;
  }

  /**
   * parse a line
   * @param line
   * @param resultStack
   * @return list of commands found
   */
  public boolean parse(String line, ArrayList<CommandI> resultStack)
  {
    boolean result = true;
    errorMsg = errorCmd = "";
    errorCode = 0;

    Matcher matcher;
    int lineLength = 1;
    do
    {
      lineLength = line.length();
      if (line.matches("^(\\S+)\\s*$"))
      {

        line = line.trim();
        //dont forget to call the parse method of the command  
        //need to split it up 
        //TODO BTW here we add the parsing ehm... 
        //since the Commands hold the shell, they dont need to get the heap 
        //explicitely, no?
        CommandI aCmd = parentParser.findCmd(line);
        if (aCmd != null)
        {
          aCmd.parse("");
          resultStack.add(aCmd);
        }
        else
        {
          result = false;
        }
      }//if(line.matches("^(\\S+)\\s*$"))
      else if ((matcher = pat_semic1.matcher(line)).find() || (matcher = pat_semic.matcher(line)).find())
      {
        String cmd = matcher.group(1);
        String args;
        if (matcher.groupCount() == 2)
        {
          args = "";
          line = matcher.group(2);
        }
        else
        {
          args = matcher.group(2);
          line = matcher.group(3);
        }

        CommandI aCmd = parentParser.findCmd(cmd);
        if (aCmd != null)
        {
          String rest = aCmd.parse(args);
          resultStack.add(aCmd);
          if (rest != null && rest.length() > 0)
          {
            result = false;
            errorCode = UNPARSED_ARGS;
            errorMsg = rest;
            errorCmd = cmd;
            return result;
          }
        }//if(aCmd != null)
        else
        {
          result = false;
        }
      }//else if((matcher = pat_semic.matcher(line)).find())
      else if ((matcher = pat_cmd_arg.matcher(line)).find())
      {
        String cmd = matcher.group(1);
        String args = matcher.group(2);
        CommandI aCmd = parentParser.findCmd(cmd);
        if (aCmd != null)
        {
          line = aCmd.parse(args);
          resultStack.add(aCmd);
        }//if(aCmd != null) 
        else
        {
          result = false;
        }
      }//else
      else
      {
        result = false;
        errorCode = UNPARSABLE;
        errorMsg = line;
        return result;
      }
    } while (line != null && line.length() > 0 && (line.length() - lineLength) != 0);

    return result && !(line != null && line.length() > 0);
  }

  /**
   * if an error occurred, heres the error code
   * @return
   */
  public String errorCmd()
  {
    return errorCmd;
  }

  /**
   * the message associated ith the eventuel error
   * @return
   */
  public String errorMsg()
  {
    return errorMsg;
  }

  /**
   * the error code
   * @return
   */
  public int errorCode()
  {
    return errorCode;
  }
}
