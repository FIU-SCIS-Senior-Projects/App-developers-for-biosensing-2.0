USE [master]
GO

CREATE LOGIN [client] WITH PASSWORD='bio', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

USE [biosensor] ALTER USER [client] WITH LOGIN = client
GO




