package me.laravieira.autolaraclone.installer;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// How a profile should be:
//"profiles": {
//	"L4R4": {
//		"created": "2021-07-16T02:55:02.351Z",
//		"gameDir": "C:\\Users\\lara\\AppData\\Roaming\\.minecraft\\1.16.5",
//		"icon": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAB1UlEQVR4nO3dO0oDURxG8TxGfKQJ+GrE3kqwtRXEzsZSsLHLArQSq2zABQimcwEirsLSRkRtRASbqJE8LEPKrxg9yPnV98+9w+E2YcJUNxcORxVh1P76AJpkEBiDwBgExiAwBoExCIxBYAwCYxAYg8AU6cBcvVHGOf6lj0E3nvGGwBgExiAwBoExCIxBYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQGIPAGATGIDAGgTEIjEFgDAJjEJj4Rbm33kMJx/ifZovFeMYbAmMQGIPAGATGIDAGgTEIjEFgDAJjEBiDwBgEpvjsv0YDz+93JR1l7Ki1Fc90Ok/R+t94jpVmPuMNgTEIjEFgDAJjEBiDwBgExiAwBoExCIxBYKpLlbXSv46wvrwdrd/ZG5R0krGry3o8c/tyXcJJJnlDYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQGIPAxP/CbZ+dxJvctO+j9VPN73iPymgYLZ8vZuIt0mc/bp3Ge3hDYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQmOpGczd6UW7/YDXepGhkn2utTee/M6WGva94pt/NPqV6cf4Y7+ENgTEIjEFgDAJjEBiDwBgExiAwBoExCIxBYAwC8wPyyC/U00ILbAAAAABJRU5ErkJggg==",
//		"javaArgs": "-Xmx3G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M",
//		"lastUsed": "2021-08-08T03:54:32.166Z",
//		"lastVersionId": "1.16.5-forge-36.1.0",
//		"name": "Lara 1.16.5",
//		"type": "custom"
//	}
//}

public class Profile {
    public final Date   created;
    public final String gameDir;
    public final String icon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAB1UlEQVR4nO3dO0oDURxG8TxGfKQJ+GrE3kqwtRXEzsZSsLHLArQSq2zABQimcwEirsLSRkRtRASbqJE8LEPKrxg9yPnV98+9w+E2YcJUNxcORxVh1P76AJpkEBiDwBgExiAwBoExCIxBYAwCYxAYg8AU6cBcvVHGOf6lj0E3nvGGwBgExiAwBoExCIxBYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQGIPAGATGIDAGgTEIjEFgDAJjEJj4Rbm33kMJx/ifZovFeMYbAmMQGIPAGATGIDAGgTEIjEFgDAJjEBiDwBgEpvjsv0YDz+93JR1l7Ki1Fc90Ok/R+t94jpVmPuMNgTEIjEFgDAJjEBiDwBgExiAwBoExCIxBYKpLlbXSv46wvrwdrd/ZG5R0krGry3o8c/tyXcJJJnlDYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQGIPAxP/CbZ+dxJvctO+j9VPN73iPymgYLZ8vZuIt0mc/bp3Ge3hDYAwCYxAYg8AYBMYgMAaBMQiMQWAMAmMQmOpGczd6UW7/YDXepGhkn2utTee/M6WGva94pt/NPqV6cf4Y7+ENgTEIjEFgDAJjEBiDwBgExiAwBoExCIxBYAwC8wPyyC/U00ILbAAAAABJRU5ErkJggg==";
    public final String javaArgs = "-Xmx3G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M";
    public final Date   lastUsed;
    public final String lastVersionId;
    public final String name;
    public final String type = "custom";

    public Profile(String name, String gameDir, String version) {
        this.name = name;
        this.gameDir = gameDir;
        this.lastVersionId = version;
        this.created = new Date();
        this.lastUsed = new Date();
    }

    public JSONObject toJSONObject() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        JSONObject json = new JSONObject();
        json.put("created",       df.format(this.created));
        json.put("gameDir",       this.gameDir);
        json.put("icon",          this.icon);
        json.put("javaArgs",      this.javaArgs);
        json.put("lastUser",      df.format(this.lastUsed));
        json.put("lastVersionId", this.lastVersionId);
        json.put("name",          this.name);
        json.put("type",          this.type);
        return json;
    }
}
