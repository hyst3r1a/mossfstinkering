public class chmod
{
  /**
   * The name of this program.  
   * This is the program name that is used 
   * when displaying error messages.
   */
  public static String PROGRAM_NAME = "chmod" ;


  public static void main( String[] args )
  {
    // initialize the file system simulator kernel
    Kernel.initialize();
    Kernel.chmod(args[0], Short.parseShort(args[1], 8));
  }

 
}