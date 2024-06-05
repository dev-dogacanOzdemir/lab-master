@echo off
SETLOCAL
SET JAVA_HOME=C:\Program Files\Java\jdk-21
SET PATH=%JAVA_HOME%\bin;%PATH%

REM Veritabanını oluştur ve doldur
call bin\create_db.bat

REM Uygulamayı çalıştır
java -jar bin\lab-master-v1.0-0.0.1-SNAPSHOT.jar

ENDLOCAL
@echo on
