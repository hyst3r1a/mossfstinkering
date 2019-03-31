
@cd C:\Users\Mishon\Desktop\SysProg

javac *.java

@echo Using new FS:
java mkfs filesys.dat 256 64
@echo LS:
java ls /
@echo Creating new file. Check its mode:
dir /b *.java | java tee /newfile.lis
@echo LS:
java ls /
@echo Running umask: standard value: 22; example value: 325. Also creates a new file with a new mode
@echo calculated as "(mode & ~Kernel.process.getUmask())""
java umask 325

@echo Check new file's mode. LS:
java ls /

@echo Now we try using chmod() to update file permissions
java chmod /newfile.lis 400
@echo Now check ls:
java ls /
PAUSE