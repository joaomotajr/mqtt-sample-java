SITE - NSSM [https://nssm.cc/usage]


RUN.BAT (Iniciando pela linha de comando e BATCH)
=================================================================================================================================
@ECHO OFF
ECHO
ECHO Iniciando :::: INTEGRADOR ::::::
java -jar -Dlog4j.configuration=file:///%CD%\cfg\log4j.properties  mqtt-eneeyes-code-1.0-SNAPSHOT-jar-with-dependencies.jar subscriber %1 %2
=================================================================================================================================



START.BAT
=================================================================================================================================
@ECHO OFF
REM start.bat  code
java -jar -Dlog4j.configuration=file:///%CD%\cfg\log4j.properties mqtt-eneeyes-code-1.0-SNAPSHOT-jar-with-dependencies.jar %1 %2 %3
=================================================================================================================================



INTALANDO O SERVIÇOS COM O FORMULÁRIO
-------------------------------------

nssm install INTEGRADOR-MQTT

|-----------------------------------------------
| path:: F:\install\integrador_log\start.bat
| args:: subscriber 192.168.15.80 127.0.0.1:8090
| startup directory:: F:\install\integrador_log
| Details:: Integrador MQTT- Eneeyes

--remover
nssm remove INTEGRADOR-MQTT confirm

--editar parametros
nssm edit INTEGRADOR-MQTT
