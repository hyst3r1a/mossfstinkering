public class umask
{
  /**
   * The name of this program.  
   * This is the program name that is used 
   * when displaying error messages.
   */
  public static String PROGRAM_NAME = "umask" ;

  /**
   * Commits change to current process's umask
   *
   * by an underlying operation
   */
  public static void main( String[] args )
  {
    // initialize the file system simulator kernel
    Kernel.initialize();
    short newUmask = Short.parseShort(args[0], 8);
    if(newUmask >= 0 && newUmask<= 0777){
    System.out.println("Old umask: " + Integer.toOctalString(Kernel.umask((short)newUmask)));
    //example file creation
    try{
    Kernel.creat("//newfile2.lis", (short)0700);
    }catch(Exception e){}
    }
    else System.out.println("Invalid umask!");
    
    //throw new Exception();
  }

 
}