/** Id: Shell.java,v 1+4 2005/09/30 16:24:48 bboett Exp  -*- java -*-
 *
 * NAME Shell 
 *
 * AUTHOR Bruno Boettcher <bboett at adlp+org> 
 *
 * SEE ALSO no docu at the moment 
 *
 * DESCRIPTION 
 * a class of object that takes a plan and produces something
 *
 * COPYRIGHT and LICENCE
 *
 *  Copyright (c) 2004 Bruno Boettcher
 *
 *  Shell.java is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; version 2
 *  of the License+
 *
 *  This program is distributed in the hope that it will be importful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE+  See the
 *  GNU General Public License for more details+

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc+, 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA+
*/

package com.nohkumado.nohsutils;

//import com.gnu.utils.*;
import com.nohkumado.nohsutils.commands.CdCommand;
import com.nohkumado.nohsutils.commands.LsCommand;
import com.nohkumado.nohsutils.commands.PwdCommand;
import com.nohkumado.nohsutils.commands.QuitCommand;
import com.nohkumado.nohsutils.commands.SetCommand;
import java.io.*;
import java.util.*;

/**
 * Shell
 * @author nohkumado
 */
public class Shell extends ConfigUser implements ShellI
{
  protected HashMap<String,Object> environment = new HashMap<>();
  protected MessageUser messageUser = new MessageUser();
  protected MessageHandlerInterface messageHandler = null;
  protected CommandParserI cmdParser = null;
  protected  OutputStream out = System.out;
  protected  InputStream in = System.in;

  protected boolean batchMode = false;
  protected boolean running = true;
  protected String scanType = null;

  /** 
   * CTOR
*/
  //public Shell(CmdLineParserI p)
  public Shell()
  {
    super();

    setConfHandler(new ConfigHandler());
    setI8nHandler(new MessageHandler(configHandler));
    
     cmdParser = new CmdLineParser(configHandler, messageHandler);
    
    cmdParser.shell(this);
    

    ArrayList<CommandI> cmds = new ArrayList<CommandI>();
    cmds.add(new CdCommand(this));
    cmds.add(new LsCommand(this));
    cmds.add(new PwdCommand(this));
    cmds.add(new QuitCommand(this));
    cmds.add(new SetCommand(this));
    
    
    cmdParser.feedCmds(cmds);
    
    in = System.in;
    ressource("shell",this);
    }// public Shell()
  /** CTOR
   * @param p
   * @param c
   * @param m
   */
  //public Shell(CmdLineParserI p)
  public Shell(CommandParserI p, ConfigHandlerInterface c, MessageHandlerInterface m)
  {
    super(c,m);
    if(p == null) cmdParser = new CmdLineParser(c,m);
    else cmdParser = p;
    cmdParser.shell(this);
    in = System.in;
    ressource("shell",this);
    messageUser.setI8nHandler(m);
  }// public Shell()

  /**
   * Shell 
   * @param c
   * @param m
   */
  public Shell(ConfigHandler c, MessageHandler m)
  {
    this(null,c,m);
  }// public Shell()
  /**

    init

    after instantiation initialisation
   * @return
   */
  @Override
  public boolean init()
  {
    if(ressource("prompt") == null) ressource("prompt","> ");
    return(true);
  }//public void init
  /**

    process

    this Method is the one that catches the commands and interprets them
    should copy some parts of nSim:lineShell concerning batches!
   * @param line
   * @return
   */
  @Override
  public String process(String line)
  {
    //TODO check if shell is allready running otherwise push into a TODO stack
    String retVal = "";
     ArrayList<CommandI> toWorkOf = cmdParser.parse(line);
     if(toWorkOf.size() > 0) 
       for (CommandI aCmd : toWorkOf) {
         if(aCmd != null)
         {
           //System.out.println("abpout to exe: "+aCmd);
           retVal = aCmd.execute();
           if(retVal != "") print(retVal);
           //System.out.println("retVal = "+retVal);
         }//if(aCmd != null)
         else
           retVal= "cmd was null??";
    } //for(Iterator<CommandI> i = toWorkOf.iterator(); i.hasNext();)
    
    return(retVal);
  }//end process
  /**

    process

    this Method is the one that catches the commands and interprets them
    should copy some parts of nSim:lineShellI concerning batches!

    this one is for internal use, when invoking commands through the shell programmately, so no command parsing is needed, parameters are in a hastable
   * @param line
   * @param parm
   * @return 
   */
  @Override
  public String process(String line,HashMap<String,Object> parm)
  {
    //TODO check if shell is allready running otherwise push into a TODO stack
    String retVal = "";
    CommandI aCmd = cmdParser.findCmd(line);
    if(aCmd != null) 
    {
      aCmd.setParameters(parm);
      retVal = aCmd.execute();
    }//if(aCmd != null) 
    return(retVal);
  }//end process
  /**

    prompt

    build up the prompt
    try to emulate in some future time the bash prompt:

             \a     an ASCII bell character (07)
              \d     the date in "Weekday Month Date" format (e.g., "Tue May 26")
              \D{format}
                     the format is passed to strftime(3) and the result is inserted into the prompt string; an empty for‐
                     mat results in a locale-specific time representation.  The braces are required
              \e     an ASCII escape character (033)
              \h     the hostname up to the first `.'
              \H     the hostname
              \j     the number of jobs currently managed by the shell
              \l     the basename of the shell's terminal device name
              \n     newline
              \r     carriage return
              \s     the name of the shell, the basename of $0 (the portion following the final slash)
              \t     the current time in 24-hour HH:MM:SS format
              \T     the current time in 12-hour HH:MM:SS format
              \@     the current time in 12-hour am/pm format
              \A     the current time in 24-hour HH:MM format
              \\u     the username of the current user
              \v     the version of bash (e.g., 2.00)
              \V     the release of bash, version + patch level (e.g., 2.00.0)
              \w     the  current  working  directory,  with  $HOME  abbreviated  with  a  tilde  (uses  the value of the
                     PROMPT_DIRTRIM variable)
              \W     the basename of the current working directory, with $HOME abbreviated with a tilde
              \!     the history number of this command
              \#     the command number of this command
              \$     if the effective UID is 0, a #, otherwise a $
              \nnn   the character corresponding to the octal number nnn
              \\     a backslash
              \[     begin a sequence of non-printing characters, which  could  be  used  to  embed  a  terminal  control
                     sequence into the prompt
              \]     end a sequence of non-printing characters
   * @return
   */
  @Override
  public String prompt()
  {
    String prompt = (String) ressource("prompt");
    if(prompt == null) 
    {
      prompt = "> ";
       ressource("prompt",prompt);
    }// if(prompt == null) 
    if(prompt.matches(".*(\\w).*"))
    {
      String pwd = (String)ressource("pwd");
      if(pwd == null ) pwd = System.getProperty("user.dir");
      prompt = prompt.replaceAll("\\\\w", pwd);
    }// if(prompt.matches(".*\\\\w.*")pp)
    return(prompt);
  }//end prompt
  /** 
   * setter for promtp 
   * 
   * @param p 
   */
  @Override
  public void prompt(String p) { ressource("prompt", p); }
  /**

    exit

    quit ans close the shell


   */
  @Override
  public void exit()
  {
    settings.remove("pwd");
    running = false;
    //System.out.println("set running to false....");
  }//end exit
  /** 
   * ressources 
   *
   * equivalent to the environment variables of a shell....
   TODO check with the other projects here a confusion between settings from config handler which are stored betweend sessions and local vars that should be dropped::::
   * 
   * @param envname 
   * @return  
   */
  @Override
  public java.lang.Object ressource(String envname)
  {
    //TODO somehow check if a ressource change needs to trigger something e.g. chanign the basename requires reloading of the data...
    if(envname == null) return(environment);
    //TODO need to separate settings from environment, the same as shellvars are separated from global vars,,,,,
    //need a set method in shell diffreent from ressource....
    //if(settings.get(envname,null) != null) return(settings.get(envname,null));
    else if(!"".equals(envname)) return(environment.get(envname));
    else return(null);
  }// public Object ressource(String envname)
  /** 
   * rmRessources 
   *
   * equivalent to the rm environment variables of a shell....
   * 
   * @param envname 
   * @return  
   */
  public java.lang.Object rmRessource(String envname)
  {
    Object result = null;
    try
    {
    if(settings.nodeExists(envname)) settings.remove(envname);
    }
    catch(java.util.prefs.BackingStoreException e) {}
    if(environment.containsKey(envname)) result = environment.remove(envname);
    return(result);
  }// public Object rmRessource(String envname)
  /** 
   * get 
   *
   * equivalent to the environment variables of a shell....
   * 
   * @param envname 
   * @return the objet of the setting to get
   */
  @Override
  public java.lang.Object get(String envname)
  {
    if(envname == null) return(settings);
    if(settings.get(envname,null) != null) return(settings.get(envname,null));
    else return(settings);
  }// public Object get(String envname)
  /** 
   * ressources 
   *
   * equivalent to the environment variables of a shell....
   * 
   * @param envname 
   * @param obj 
   */
  @Override
  public void ressource(String envname, Object obj)
  {
    environment.put(envname, obj);
    if(obj instanceof String) settings.put(envname,(String) obj);
    else if(obj instanceof Integer) settings.putInt(envname, ((Integer) obj));
    environment.put(envname,obj);
    //TODO and the rest
  }// public Object ressource(String envname)
  /** 
   * prototype for a help function 
   * 
   * @return 
   */
  @Override
  public String help()
  {

    return("");
  }//end help
  @Override
  public void setI8nHandler(MessageHandlerInterface m)
  {   
    messageHandler = m;
    messageUser.setI8nHandler(m);
  }// public void setI8nHandler(MessageHandlerInterface m)
  @Override
  public MessageHandlerInterface getI8nHandler()
  {
    return(messageHandler); 
  }// public MessageHandlerInterface getI8nHandler()
  /** 
   * returns the message associated with a token
   * 
   * @param m 
   * @return 
   */
  @Override
  public String msg(String m)
  {
    return(messageUser.msg(m));
  }// public String msg(String m)
  /**
run: 
event loop 
   */
  @Override
 public void run()
 {
   /** if necessary issue the prompt a first time!*/
   /** now initialise the loop that will read from the inputStream until
    *exhaustion */
   //System.out.println("runnning is "+running);
   //TODO check if the todo list is filled and exe those commands prior to anything else
   while (running == true) 
   {
     if(!batchMode) writeStream(prompt());
     ArrayList<CommandI> toWorkOf = cmdParser.parse(read());
     if(toWorkOf.size() > 0) 
       for (CommandI aCmd : toWorkOf) {
         if(aCmd != null)
         {
           //System.out.println("abpout to exe: "+aCmd);
           String retVal = aCmd.execute();
           if(!"".equals(retVal)) print(retVal);
           //System.out.println("retVal = "+retVal);
         }//if(aCmd != null)
         else
           System.out.println("cmd was null??");
     } //for(Iterator<CommandI> i = toWorkOf.iterator(); i.hasNext();)
     
   }//while (running == true) 
 }//public void run()
  /**
  read: 
  read a line of commands
   * @return 
   */
  public String read()
  {
    //TODO at the moment we drop the batchfile func and drop the buggy Input Stream stuff
    /** now initialise the loop that will read from the inputStream until
     *exhaustion */
    Scanner scanner = new Scanner(System.in);
    String inputLine = "";
    if(scanType != null && scanType.equals("numeric"))
    {
      System.out.println("question is numeric");
      boolean isNumeric = false;
      do
      {
	try
	{
	  inputLine = scanner.nextDouble()+"";
	  isNumeric = true;
	}// try
	catch(InputMismatchException e)
	{
	  print(msg("not_a_numeric")+"\n>");
	  isNumeric = false;
	}// catch(InputMismatchException e)
      }
      while(isNumeric == false);
      scanType = null;
    }// if(scanType != null && scanType.equals("numeric"))
    else if(scanType != null && scanType.equals("int"))
    {
      System.out.println("question is int");
      boolean isNumeric = false;
      do
      {
	try
	{
	  inputLine = scanner.nextInt()+"";
	  isNumeric = true;
	}// try
	catch(InputMismatchException e)
	{
	  print(msg("not_a_int")+"\n>");
	  isNumeric = false;
	}// catch(InputMismatchException e)
      }
      while(isNumeric == false);
      scanType = null;
    }// if(scanType != null && scanType.equals("numeric"))
    else
      inputLine = scanner.nextLine();
    //print("read: scanner returned '"+inputLine+"'\n");
    return(inputLine);
  }//end private String ReadFile(FileInputStream a)
  //------------------------------------------------------------------

  /**
   * fedThroughString
   * @param aBatch
   */
  public void fedThroughString(String aBatch)
  {
    in = new StringBufferInputStream(aBatch);
    //  catch (IOException e)
    //  {
    //     print("#OpenFile : " + e+"\n");
    //     exit(0);
    //  }//end catch (IOException e)
    batchMode = true;
  }//public void fedThroughFile(String aFile)
  //------------------------------------------------------------------

  /**
   *
   * @param aFile
   */
  public void fedThroughFile(String aFile)
  {
    /** open the file, else exit */
    in = null;
    File theFile;
    theFile = new File(aFile);
    try
    {
      in = new FileInputStream(theFile);
      //TODO pass the lines through process!!
    }//end try
    catch (FileNotFoundException e)
    {
      print("#The file couln't be found\n");
      exit();
    }// catch (FileNotFoundException e)
    //catch (IOException e)
    //{
    //  print("#OpenFile : " + e+"\n");
    //  exit();
    //}//end catch (IOException e)
    batchMode = true;
  }//public void fedThroughFile(String aFile)
  //------------------------------------------------------------------

  /**
   *
   * @param s
   */
  public void writeStream(Object s)
  {
    print(s);
    //TODO dont remind what this was for.... maybe to allow for completion?
   //if (!batchMode)
   //{
   //  //if (s instanceof String)
   //  //{
   //  //  if (((String)s).indexOf("\n") != -1)
   //  //  {
   //  //    if(s != prompt) print(prompt);
   //  //  }//if (s.indexOf("\n") != -1)
   //  //}//if (s instanceof String)

   //}//if (!batchMode)·

  }// public void writeStream(Object s)
  //------------------------------------------------------------------

  /**
   *
   * @param something
   */
  protected void print(Object something)
  {
     print(something.toString());
  }//protected void print(String something)
  //------------------------------------------------------------------
  @Override
  public void print(String something)
  {
     try
     { out.write(something.getBytes());}
     catch (IOException e)
     { exit (""+e);}
  }//protected void print(String something)
   //------------------------------------------------------------------
  @Override
  public void error(String aMessage) 
  {
    print("Error:"+aMessage);
  }//public void error(String aMessage) 

  /**
   *
   * @param a
   */
  protected void setInputStream(InputStream a) { in = a; }

  /**
   *
   * @return
   */
  protected InputStream getInputStream() { return in; }
   //------------------------------------------------------------------
   //protected void setOutputStream(OutputStream a) { out = a; lstContent = ""; }

  /**
   *
   * @param a
   */
   protected void setOutputStream(OutputStream a) { out = a; }

  /**
   *
   * @return
   */
  protected OutputStream getOutputStream() { return out; }
   //------------------------------------------------------------------

  /**
   *
   * @param aMessage
   */
  @Override
   public void exit(String aMessage)
   {
     
     System.out.println(aMessage);
     exit();
    }//public void die(String aMessag)
   //------------------------------------------------------------------
   /** in fact read a line..
   * @param question.
   * @return */
  @Override
   public String ask(String question)
   {
     if(question.length() > 0) print(question+" ");
     return(read());
   }//public String ask()
   /**
    * in fact read a line...
    * 
    * @param question a string to issue to prompts for an answer
    * @param options more data, e.g. default value, a range selection, captions for range selection,  
    * @return 
    */
  @Override
   public String ask(String question,HashMap<String,Object> options) 
   {
      //for(Iterator<String> i = ((List<String>) values.get("select")).iterator() ; i.hasNext();) console.print("debug: "+i.next()+"\n");
     if(options.get("select") != null)
     {
       String defVal = (String)options.get("default");
       if(question.length() > 0) print(question+" ");
       question = "";
       List select = (List) options.get("select");
       List<String> captions = (List<String>) options.get("captions");
       
       List selectCopy = new ArrayList<>();
       List captionsCopy = new ArrayList<>();
       if(defVal != null && defVal.length() > 0)
       //if(defVal != null && defVal.length() > 0 && !defVal.equals("null"))
       {
	 System.out.println("adding default: '"+defVal+" = "+defVal.getClass());
	 selectCopy.add(defVal);
	 captionsCopy.add("");
       }//if(defVal != null && defVal.size() > 0)
       for(int i = 0 ; i < select.size(); i++)
       {
	 if(!select.get(i).equals(defVal))
	 {
	   selectCopy.add(select.get(i));
	   if(captions != null && captions.size() > i)captionsCopy.add(captions.get(i));
	   else captionsCopy.add("");
	 }//if(!select.get(i).equals(defVal))
       }// for(int i = 0 ; i < select.length; i++)

       for(int i = 0 ; i < selectCopy.size(); i++)
       {
	 question += "["+i+"] : "+selectCopy.get(i);
	 String cap = ""+captionsCopy.get(i);
	 if(cap != null && cap.length() > 0) question += " ("+cap+")";
	 question += "\n>";
       }// for(int i = 0 ; i < select.length; i++)

       int index = 0;
       String answer = "";
       do
       {
	 answer = ask(question);
	 if(answer.equals("")) {index = 0;}
	 else
	 {
	   try
	   {
	     index = (new Integer(answer));
	   }// try
	   catch(NumberFormatException e)
	   {
	     System.out.println("no valid number: "+answer); 
	     index = -1;
	   }// catch(NumberFormatException e)
	 }//else
       }// do
       while(index < 0  || index >= select.size()); 
       answer = selectCopy.get(index)+"";
       return(answer);
     }// if(options.get("select") != null)
     else if(options.get("type") != null) scanType = options.get("type")+"";
     else
       System.out.println("need to do something with the options:"+options);
     return(ask(question));
   }//public String ask(String question,HashMap<String,.Object> options) 
   /** in fact read a line..
   * @param question
   * @param defaultValue.
   * @return */
  @Override
   public String ask(String question,String defaultValue) 
   {
     String result = ask(question+"["+defaultValue+"]");
     if(result == null || result.equals("")) result = defaultValue;
     return(result);
   }//public String ask(String question,HashMap<String,.Object> options) 
   //the sames and forcing numerical reading

  /**
   *
   * @param question
   * @return
   */
  @Override
   public String askNum(String question)
   {
    scanType = "numeric";
    return(ask(question));
   }// public String askNum(String question)
 
  /**
   *
   * @param question
   * @param defaultValue
   * @return
   */
  @Override
  public String askNum(String question,String defaultValue) 
   {
    scanType = "numeric";
    return(ask(question,defaultValue));
   }// public String askNum(String question)
 
  /**
   *
   * @param question
   * @param options
   * @return
   */
  @Override
  public String askNum(String question,HashMap<String,Object> options) 
   {
    scanType = "numeric";
    return(ask(question,options));
   }// public String askNum(String question,HashMap<String,Object> options) 
  /** Load a filename, parse it and instantiate the correkt elements, using
   * the Member datastring MemberClassName for the instantiation, generic
   * code that is able to load JournalEintrag, as well as KontoEintrag or
   * Currencies...
   * 
   * @param baseName 
   */
  public void load(String baseName )
  {
  }//public boolean load(String baseName)
  @Override
  public void load()
  {
  }//public boolean load(String baseName)
  @Override
  public void save()
  {
  }// public void save()
  @Override
   public boolean isRunning() { return(running);}
    /** 
     * set Locale set the locale for this app 
     * 
     * @param m 
     */
  @Override
    public void setLocale(Locale m)
    {
    messageHandler.setLocale(m);
    }// public void setLocale(Locale m)
  /** 
   * a tag is missing in the db, add one per default, and save the base?
   TODO should search for all languages and add to their respective files the new tag...?
   * 
   * @param m fnmae  String the tag to add
   */
  @Override
  public void addTag(String m)
  {
    messageHandler.addTag(m);
  }// public void addTag(String m)
  @Override
  public ResourceBundle getI8nBoundle()
  {
    return(messageHandler.getI8nBoundle());
  }
    /**
     *
     *  feedCmds
     *
     * @param cmds 
     * @return 
     */
  @Override
    public HashMap<String,CommandI> feedCmds(HashMap<String,CommandI> cmds)
    {
      return(cmdParser.feedCmds(cmds));
    }// public HashMap<String,CommandI> feedCmds(HashMap<String,CommandI> cmds)
}//public class Shell
