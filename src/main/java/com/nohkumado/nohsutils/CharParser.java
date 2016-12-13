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
 *
 * @author NohKumado <nohkumado@gmail.com>
 */
public class CharParser
{

  public static final String TAG = "CHARP";
  public static final int UNPARSED_ARGS = 1;

  protected CmdLineParser parentParser;
  protected ArrayList<Pattern> patterns = new ArrayList<>();

  //Pattern pat_cmd_arg = Pattern.compile("^(\\S+)\\s+(.*)$");
  protected String errorMsg = "";

  protected int errorCode = 0;
  protected String errorCmd = "";

  private static final int UNPARSABLE = 2;

  /**
   * CTOR builds a list of patterns corresponding to the strings that match this
   * command
   */
  public CharParser(CmdLineParser p)
  {
    parentParser = p;
    for (CommandI aCmd : parentParser.commands.values())
    {
      patterns.add(aCmd.pattern());
      //Log.d(TAG,"added "+aCmd+" pattern "+aCmd.pattern());
    }
  }

  public boolean parse(String line, ArrayList<CommandI> resultStack)
  {
    boolean result = true;
    errorMsg = errorCmd = "";
    errorCode = 0;
    line = line.trim();

 System.err.println("CHAR PARSING "+line);

    Matcher matcher;
    int lineLength = 1;
    do
    {
      lineLength = line.length();
      int cmd_count = resultStack.size();
      for (Pattern cmdPat : patterns)
      {
        //we need to give the line over to cmd.parse, so making the mathcer her...
        //seems irrelevant... especially since we can't be sure to get the right pattern for all commands...
        if ((matcher = cmdPat.matcher(line)).find())
        {
          /*StringBuilder sb = new StringBuilder();
          sb.append(cmdPat).append(" match '").append(line).append("'").append("\n");
          sb.append("groups:");
          for(int i = 0; i <= matcher.groupCount();i++)
          {
            sb.append(" group").append(i).append(" ").append(matcher.group(i));
          }
          Log.d(TAG,sb.toString());
           */
          String cmdPart, argPart;
          if (matcher.groupCount() > 0)
          {
            System.err.println( "NUM OF GROUPS  "+matcher.groupCount()+" FOR "+cmdPat);

            cmdPart = line.substring(0, matcher.start(1));
            argPart = matcher.group(1);
            line = line.substring(matcher.end(1), line.length());
          }
          else
          {
            System.err.println( "NO ARG CMD "+matcher.group()+" IN LINE "+line);
            cmdPart = line;
            argPart = "";
          }

          System.err.println(" SPLIT INTO  '"+cmdPart+"' AND '"+argPart+"' REST 'line'");

          //dont forget to call the parse method of the command  
          //need to split it up 
          //TODO BTW here we add the parsing ehm... 
          //since the Commands hold the shell, they dont need to get the heap 
          //explicitely, no?
          CommandI aCmd = parentParser.findCmd(cmdPart);
          if (aCmd != null)
          {
            aCmd.parse(argPart);
            resultStack.add(aCmd);
            System.err.println(" CMD '"+aCmd+"' ADDED");
          }
          else
          {
            System.err.println(" CMD '"+line+"' NOT FOUND");
            result &= false;
          }
          continue;
        }//if(line.matches("^(\\S+)\\s*$"))
        //else Log.d(TAG,cmdPat+" fail '"+line+"'");
      }//for(Pattern cmPat : patterns)
      if (!result || cmd_count == resultStack.size())
      {
        errorCode = UNPARSABLE;
        errorMsg = line;
        return result;
      }
    } while (line != null && line.length() > 0 && (line.length() - lineLength) != 0);

    return !(line != null && line.length() > 0) && result;
  }

  public String errorCmd()
  {
    return errorCmd;
  }

  public String errorMsg()
  {
    return errorMsg;
  }

  public int errorCode()
  {
    return errorCode;
  }
}
