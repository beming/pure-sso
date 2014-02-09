set SSO_DATA_PATH=d:\sso_database\
set PGHOME=%CD%\pgsql
set PGDATA=%SSO_DATA_PATH%
set PGLIB=%PGHOME%\lib
set PGHOST=localhost
set PATH=%PGHOME%\bin;%PATH%
rem system service start
net stop PostgreSQL
rem 取消系统注册...
pg_ctl unregister -N PostgreSQL
del /S /A /F /Q %PGDATA%\*
rd /S /Q %PGDATA%\
rem wait for 2second
ping -n 3 localhost > nul
rem 开始初始化数据库... 
initdb --locale=C -U postgres
rem 数据启动中...
pg_ctl -D %PGDATA% -l logfile start
rem 等待5秒，数据库完成启动...
ping -n 6 localhost > nul
rem 初始化...
createuser -S -D -R -Upostgres ssodbuser
rem set the user password
psql -Upostgres -c "alter user postgres WITH PASSWORD 'bzm@lib'"
psql -Upostgres -c "alter user ssodbuser WITH PASSWORD '123456'"
rem 初始化数据库...
createdb -E Unicode -Upostgres -Ttemplate0 -Ossodbuser ssodb
rem 初始化数据...
psql -dssodb -Ussodbuser -f "sqlscript.sql"
rem 注册数据库服务...
pg_ctl register -N PostgreSQL -D %PGDATA%
rem stop db
pg_ctl -D %PGDATA% stop
rem wait for 2second
ping -n 3 localhost > nul
rem system service start
net start PostgreSQL