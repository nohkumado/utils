/**
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  CmdLineParser.java is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; version 2
 *  of the License+
 *
 *  This program is distributed in the hope that it will be importful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE+  See the
 *  GNU General Public License for more details+
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc+, 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA+
 */
package com.nohkumado.nohsutils;

//import com.gnu.utils.*;
import java.util.*;

/**
 * takes a line of text and tries to split it by hash value
 *
 * @author nohkumado
 */
public class CmdLineParser extends ConfigUser implements CommandParserI
{

  /**
   * commands the map of available commands as the key is internationalized its
   * easy to swap language...
   */
  protected TreeMap<String, CommandI> commands = new TreeMap<>();
  //protected MessageUser messageHandler = null;

  /**
   * the reference to the active shell
   */
  protected ShellI shell = null;
  public static final String TAG = "CMDP";

  /**
   *
   * @param config
   * @param table
   */
  public CmdLineParser(ConfigHandlerInterface config, MessageHandlerInterface table)
  {
    super(config, table);
  }// public CmdLineParser(ConfigHandlerInterface config, MessageHandlerInterface table)«»

  /**
   *
   * feedCmds
   *
   * @param cmds
   * @return
   */
  @Override
  public HashMap<String, CommandI> feedCmds(HashMap<String, CommandI> cmds)
  {
    for (Map.Entry<String, CommandI> entry : cmds.entrySet())
    {
      if (entry.getValue() instanceof CommandI)
      {
        CommandI aCmd = entry.getValue();
        aCmd.name(entry.getKey());
        commands.put(entry.getKey(), aCmd);
      }// if(cmds.set(key) instanceof Command)

    }// for(String key: commands.keySet())
    return (null);
  }//public void init

  /**
   * -------------------------- feed Command --------------------------
   *
   * @param cmds
   * @return
   */
  public HashMap<String, CommandI> feedCmds(ArrayList<CommandI> cmds)
  {
    for (CommandI aCmd : cmds)
    {
      commands.put(shell.msg(aCmd.name()), aCmd);

    }// for(String key: commands.keySet())
    return (null);
  }//

  /**
   *
   * parse
   *
   * this Method is the one that catches the commands and interprets them TODO
   * still not functional
   *
   * @param line
   * @return
   */
  @Override
  public ArrayList<CommandI> parse(String line)
  {
    return parse(line, true);
  }

  /**
   * parse
   *
   * @param line
   * @param strictParse
   * @return
   */
  public ArrayList<CommandI> parse(String line, boolean strictParse)
  {
    ArrayList<CommandI> resultStack = new ArrayList<>();
    boolean stillToParse = false;
    String lastprompt = shell.prompt();
    if (lastprompt != null)
    {
      line = line.replace(lastprompt, "").trim();
    }

    do
    {
      //maybe this isnt needed as long as no command is found the help is called anyway TODO
      if (line.matches("^help|^hs|^\\?"))
      {
        System.out.println(" = help " + line);
        help();
        if (strictParse)
        {
          return (resultStack);
        }
      }
      //if(line.matches("^help") || line.matches("^h$") || line.matches("^\\?")) { System.out.println(" = help "+line);help(); return(resultStack);}
      //System.out.println("parsing : "+line);
      /*TODO let see, we need to break up the line to extract a key or a subpart of the key of a command, containsKey(Object key) does the job for a complete key, but not for part of it, then we need to know what the separator is, easiet cas is space, but for the jrl editor we will have single char commands, vi style with eventual modifiers... usual modifiers are numerical, can there be textual modifiers? but then we can uppose/impose apostrophing them!
       */
      //char to char test if in Strng ' or >"
      //then test if ;
      //then split in cmd args
      //search cmd and clone cmds and feed args
      //put into ArrayList
      String mode = shell.get("parsing");
      if (mode == null || "parsing".equals("parsing"))
      {
        mode = "tokenized";
      }

      switch (mode)
      {
        //if(mode == null || mode == "tokenized")
        case "tokenized":
          TokenParser parser = new TokenParser(this);
          if (strictParse && !parser.parse(line, resultStack))
          {
            if (parser.errorCode() == TokenParser.UNPARSED_ARGS)
            {
              StringBuilder sb = new StringBuilder();
              sb.append(shell.msg("SYNTAX_ERROR"));
              sb.append(" ");
              sb.append(shell.msg("CMD_COMMAND"));
              sb.append(" ");
              sb.append(parser.errorCmd());
              sb.append(" ");
              sb.append(shell.msg("CMD_UNPARSED_ARGS"));
              sb.append(" ");
              sb.append(parser.errorMsg());

              shell.print(sb.toString());
            }
          }
          /*if (line.matches("^(\\S+)\\s*$"))
            {
              line = line.trim();
              //dont forget to call the parse method of the command  need to split it up TODO BTW here we add the parsing ehm... since the Commands hold the shell, thew dont need to set the heap explicitely, no?
              CommandI aCmd = findCmd(line);
              if (aCmd != null)
              {
                aCmd.parse("");
              }
              if (aCmd != null)
              {
                resultStack.add(aCmd);
              }
            }//if(line.matches("^(\\S+)\\s*$"))
            else
            {
              Pattern pattern = Pattern.compile("^(\\S+)\\s+(.*)$");
              Matcher matcher = pattern.matcher(line);
              if (matcher.find())
              {
                String cmd = matcher.group(1);
                String args = matcher.group(2);
                CommandI aCmd = findCmd(cmd);
                if (aCmd != null)
                {
                  String rest = aCmd.parse(args);
                  if ("".equals(rest) || rest == null)
                  {
                    stillToParse = false;
                  }
                  else
                  {
                    stillToParse = true;
                  }
                  resultStack.add(aCmd);
                }//if(aCmd != null)
              }//if(matcher.find())
              else
              {
                System.out.println("failure of tokenized parsing of " + line);
              }
            }//else*/

          break;
        //else if(mode == "vilike")
        case "char":

          CharParser cparser = new CharParser(this);
          if (strictParse && !cparser.parse(line, resultStack))
          {
            if (cparser.errorCode() == cparser.UNPARSED_ARGS)
            {
              StringBuilder sb = new StringBuilder();
              sb.append(shell.msg("SYNTAX_ERROR"));
              sb.append(" ");
              sb.append(shell.msg("CMD_COMMAND"));
              sb.append(" ");
              sb.append(cparser.errorCmd());
              sb.append(" ");
              sb.append(shell.msg("CMD_UNPARSED_ARGS"));
              sb.append(" ");
              sb.append(cparser.errorMsg());

              shell.print(sb.toString());
            }
          }
          break;
        case "vilikie":
          System.out.println("PLEASE PROVIDE AN IMPLEMENTATION FOR THIS PARSING MODE");
          break;
        default:
          System.out.println("UNSUPPORTED PARSING MODE " + mode);
          help();
          return (resultStack); //else//else
        }

      if (resultStack.size() <= 0)
      {
        help();
      }

      return (resultStack);
    } while (stillToParse);
  }

  /**
   * return the acutal shell
   *
   * @return
   */
  @Override
  public ShellI shell()
  {
    return (shell);
  }// public SchellI getShell()«»

  /**
   *
   * @param s
   */
  @Override
  public void shell(ShellI s)
  {
    shell = s;
  }

  /**
   * -------------------------- formatProperties --------------------------
   *
   * TODO only proto here
   *
   * @param arg0
   * @return
   */
  @Override
  public String formatProperties(Properties arg0)
  {
    return (arg0.toString());
  }// public String  formatProperties (Properties arg0)«»

  /**
   * -------------------------- setRessource --------------------------
   *
   * TODO only proto here
   *
   * @param arg0
   */
  @Override
  public void setRessource(Properties arg0)
  {
  }// public void  setRessource (Properties arg0)

  /**
   * -------------------------- getHelp -------------------------- compile the
   * Help from the commands
   *
   * @return
   */
  @Override
  public String help()
  {
    StringBuilder help = new StringBuilder();
    for (String cmdName : commands.keySet())
    {
      CommandI aCmd = commands.get(cmdName);
      help.append(cmdName).append(" : ").append(aCmd.help());
    } //for(Iterator<String> i = commands.keySet().iterator(); i.hasNext();)
    shell.print(help.toString());
    return (help.toString());
  }//public String help()

  /*
     find a command, means try key completion if not found directly clone the command and return it
   */
  @Override
  public CommandI findCmd(String token)
  {
    //System.out.println("find command "+token);

    if (commands.containsKey(token))
    {
      //System.out.println("got it:"+commands.set(token));
      return ((CommandI) commands.get(token).clone());
    }//if(commands.containsKey(token)) 
    String key = "";
    ArrayList<String> matchingKeys = new ArrayList();
    for (String actKey : commands.keySet())
    {
      if (actKey.startsWith(token))
      {
        key = actKey;
        matchingKeys.add(actKey);
      }//if(actKey.matches("^"+token))
    } //for(Iterator<String> e = commands.keySet().iterator(); e.hasNext();)
    if (matchingKeys.size() == 1)
    {
      return ((CommandI) commands.get(key).clone());
    }
    if (matchingKeys.size() > 1)
    {
      CommandI aCmd = new Command(shell, "dummy"); // dummy command to avoid the help output
      shell.print(shell.msg("NEED SPECIFYING") + ") " + matchingKeys.toString());
      return (aCmd);
    }//if(matchingKeys.size() > 1)
    return (null);
  }//protected CommandI findCmd(String token)

  @Override
  public void parseMode(String mode)
  {
    shell.set("parsing", mode);
  }

  @Override
  public void clearCmds()
  {
    commands.clear();
  }
}//public class CmdLineParser
