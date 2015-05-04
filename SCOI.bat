net stop spooler
del %systemroot%\system32\spool\PRINTERS\*.* /Q
net start spooler
c:\scoi\scoi.jar