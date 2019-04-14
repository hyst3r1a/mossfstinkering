# mossfstinkering

MOSS File System Simulator exercises by Andrew Kopyl and Michael Gorshenin

Work done:


-.bat file with initialization created, also running it showcases all the work done in an automated way

     * -to run it, edit the exampleCodeRunner.bat line 1 to match your local MOSS instalation
	
	
-"ls" command extended to show uid and gid as well as file mode


-"umask" added to Kernel.java, and umask.java utility added. Usage: java umask [mask]
	
	  * -IMPORTANT! As MOSS initializes it's process every time any utility is run umask result
		is lost immediately after each utility start
		
	  * -To showcase that it works properly umask() function also creates a file with a new mask,
		so the file's mode differs from standard


-"chmod" added to Kernel.java, and chmod.java utility added. Usage: java chmod [path] [mode]
	
	  *-Works properly, accepts octal numbers by default, changes the mode for a file if you're file's owner
		or superuser
		
	  *-IMPORTANT! For this particular case uid '0' is referred to as "superuser", while standart
		configuration gives uid '1'
