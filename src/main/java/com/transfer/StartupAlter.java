package com.transfer;

import dboperations.IJavaH2Database;
import dboperations.JavaH2Database;

public class StartupAlter {
	
	public static void Alter()
	{
		IJavaH2Database db = new JavaH2Database();
		db.AlterCreateTransfer();
		db = null;//dispose
	}
}
