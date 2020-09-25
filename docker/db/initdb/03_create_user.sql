/* Create maintenance user and grant rights */
-- docker-compose でユーザを作成・設定しているため不要
-- CREATE USER 'fsedu'@'%' IDENTIFIED BY 'secret';
-- GRANT ALL PRIVILEGES ON *.* TO 'fsedu'@'%';
-- SET PASSWORD FOR fsedu = 'secret'
