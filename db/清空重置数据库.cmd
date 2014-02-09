set SSO_DATA_PATH=d:\sso_database\
set PGHOME=%CD%\pgsql
set PGDATA=%SSO_DATA_PATH%
set PGLIB=%PGHOME%\lib
set PGHOST=localhost
set PATH=%PGHOME%\bin;%PATH%
rem system service start
net stop PostgreSQL
rem ȡ��ϵͳע��...
pg_ctl unregister -N PostgreSQL
del /S /A /F /Q %PGDATA%\*
rd /S /Q %PGDATA%\
rem wait for 2second
ping -n 3 localhost > nul
rem ��ʼ��ʼ�����ݿ�... 
initdb --locale=C -U postgres
rem ����������...
pg_ctl -D %PGDATA% -l logfile start
rem �ȴ�5�룬���ݿ��������...
ping -n 6 localhost > nul
rem ��ʼ��...
createuser -S -D -R -Upostgres ssodbuser
rem set the user password
psql -Upostgres -c "alter user postgres WITH PASSWORD 'bzm@lib'"
psql -Upostgres -c "alter user ssodbuser WITH PASSWORD '123456'"
rem ��ʼ�����ݿ�...
createdb -E Unicode -Upostgres -Ttemplate0 -Ossodbuser ssodb
rem ��ʼ������...
psql -dssodb -Ussodbuser -f "sqlscript.sql"
rem ע�����ݿ����...
pg_ctl register -N PostgreSQL -D %PGDATA%
rem stop db
pg_ctl -D %PGDATA% stop
rem wait for 2second
ping -n 3 localhost > nul
rem system service start
net start PostgreSQL