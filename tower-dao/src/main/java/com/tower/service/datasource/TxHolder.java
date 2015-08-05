package com.tower.service.datasource;

public class TxHolder {
	private static final ThreadLocal<Boolean> TRANSACTION = new InheritableThreadLocal<Boolean>();

	public static void set() {
		TRANSACTION.set(true);
	}

	public static void unset() {
		TRANSACTION.remove();
	}

	public static Boolean started() {
		Boolean flg = TRANSACTION.get();
		if (flg == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Boolean isSingleTx() {
		Boolean flg = TRANSACTION.get();
		if (flg == null) {
			return true;
		} else {
			return false;
		}
	}
}
