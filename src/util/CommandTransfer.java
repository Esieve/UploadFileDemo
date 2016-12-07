package util;

import java.io.Serializable;

public class CommandTransfer implements Serializable{
	private String cmd = null;
	private Object obj = null;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
