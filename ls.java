/*
 * $Id: ls.java,v 1.6 2001/10/12 02:14:31 rayo Exp $
 */

/*
 * $Log: ls.java,v $
 * Revision 1.6  2001/10/12 02:14:31  rayo
 * better formatting
 *
 * Revision 1.5  2001/10/07 23:48:55  rayo
 * added author javadoc tag
 *
 */

/**
 * A simple directory listing program for a simulated file system.
 * <p>
 * Usage:
 * <pre>
 *   java ls <i>path-name</i> ...
 * </pre>
 * @author Ray Ontko
 */
public class ls
{
  /**
   * The name of this program.  
   * This is the program name that is used 
   * when displaying error messages.
   */
  public static String PROGRAM_NAME = "ls" ;

  /**
   * Lists information about named files or directories.
   * @exception java.lang.Exception if an exception is thrown
   * by an underlying operation
   */
  public static void main( String[] args ) throws Exception
  {
    // initialize the file system simulator kernel
    Kernel.initialize() ;
   

    // for each path-name given
    for( int i = 0 ; i < args.length ; i ++ )
    {
      String name = args[i] ; 
      int status = 0 ;

      // stat the name to get information about the file or directory
      Stat stat = new Stat() ;
      status = Kernel.stat( name , stat ) ;
      if( status < 0 )
      {
        Kernel.perror( PROGRAM_NAME ) ;
        Kernel.exit( 1 ) ;
      }

      // mask the file type from the mode
      short type = (short)( stat.getMode() & Kernel.S_IFMT ) ;

      // if name is a regular file, print the info
      if( type == Kernel.S_IFREG )
      {
        print( name , stat ) ;
      }
   
      // if name is a directory open it and read the contents
      else if( type == Kernel.S_IFDIR )
      {
        // open the directory
        int fd = Kernel.open( name , Kernel.O_RDONLY ) ;
        if( fd < 0 )
        {
          Kernel.perror( PROGRAM_NAME ) ;
          System.err.println( PROGRAM_NAME + 
            ": unable to open \"" + name + "\" for reading" ) ;
          Kernel.exit(1) ;
        }

        // print a heading for this directory
        System.out.println() ;
        System.out.println( name + ":" ) ;

        // create a directory entry structure to hold data as we read
        DirectoryEntry directoryEntry = new DirectoryEntry() ;
        int count = 0 ;

        // while we can read, print the information on each entry
        while( true ) 
        {
          // read an entry; quit loop if error or nothing read
          status = Kernel.readdir( fd , directoryEntry ) ;
          if( status <= 0 )
            break ;

          // get the name from the entry
          String entryName = directoryEntry.getName() ;

          // call stat() to get info about the file
          status = Kernel.stat( name + "/" + entryName , stat ) ;
          if( status < 0 )
          {
            Kernel.perror( PROGRAM_NAME ) ;
            Kernel.exit( 1 ) ;
          }

          // print the entry information
          print( entryName , stat ) ;
          count ++ ;
        }

        // check to see if our last read failed
        if( status < 0 )
        {
          Kernel.perror( "main" ) ;
          System.err.println( "main: unable to read directory entry from /" ) ;
          Kernel.exit(2) ;
        }

        // close the directory
        Kernel.close( fd ) ;

        // print a footing for this directory
        System.out.println( "total files: " + count ) ;
      }
    }

    // exit with success if we process all the arguments
    Kernel.exit( 0 ) ;
  }

  /**
   * Print a listing for a particular file.
   * This is a convenience method.
   * @param name the name to print
   * @param stat the stat containing the file's information
   */
  private static void print( String name , Stat stat )
  {
    IndexNode IndexAccess = new IndexNode();
    try{Kernel.findIndexNode(name, IndexAccess);}catch(Exception e){}
    
    // a buffer to fill with a line of output
    StringBuffer s = new StringBuffer() ;

    // a temporary string
    String t = null ;
  
    t = Integer.toString( stat.getIno() ) ;
    for( int i = 0 ; i < 5 - t.length() ; i ++ )
      s.append( ' ' ) ;
    s.append( t ) ;
    s.append( ' ' ) ;

    // append the size in a field of 10
    t = Integer.toString( stat.getSize() ) ;
    for( int i = 0 ; i < 10 - t.length() ; i ++ )
      s.append( ' ' ) ;
    s.append( t ) ;
    s.append( ' ' ) ;

    t = Integer.toString( IndexAccess.getUid());
    s.append("Uid ");
    s.append(t);
    s.append(' ');

    t = Integer.toString( IndexAccess.getGid());
    s.append("Gid ");
    s.append(t);
    s.append(' ');

    t = Integer.toOctalString(IndexAccess.getMode());
    s.append("Mode ");
    s.append(t.substring(0, 3));
    s.append(' ');
    for(int i = 0; i<3; i++){
      switch(t.charAt(i)){
        case '0':
        s.append("---");
        break;
         case '1':
         s.append("--x");
        break;
         case '2':
         s.append("-w-");
        break;
         case '3':
         s.append("-wx");
        break;
         case '4':
         s.append("r--");
        break;
         case '5':
         s.append("r-x");
        break;
         case '6':
         s.append("rw-");
        break;
         case '7':
         s.append("rwx");
        break;
      }
      s.append("/");
    }
    s.append(' ');

    // append the name
    s.append( name ) ;

    // print the buffer
    System.out.println( s.toString() ) ;
  }

}
