package com.example.budgetbuddy
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class SQLHelper(context: Context): SQLiteOpenHelper(context, "USERDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // initialize the USERS table
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT, FULLNAME TEXT, EMAIL TXT, BDATE DATE)")
        db?.execSQL("INSERT INTO USERS(UNAME, PWD, FULLNAME, EMAIL, BDATE) VALUES('user1', 'password1', 'User 1', 'user1@gmail.com', '2001-06-29')")
        db?.execSQL("INSERT INTO USERS(UNAME, PWD, FULLNAME, EMAIL, BDATE) VALUES('user2', 'password2', 'User 2', 'user2@gmail.com', '2001-06-29')")

        // initialize the TRANSACTIONS TABLE
        db?.execSQL("CREATE TABLE WALLETS(WID INTEGER PRIMARY KEY AUTOINCREMENT, UID INTEGER, BAL INTEGER, WNAME TEXT, WTYPE TEXT, WDATE DATE)")
        db?.execSQL("INSERT INTO WALLETS(UID, BAL, WNAME, WTYPE, WDATE) VALUES(1, 1000, 'BDO', 'Bank', '2023-03-15')")
        db?.execSQL("INSERT INTO WALLETS(UID, BAL, WNAME, WTYPE, WDATE) VALUES(1, 2000, 'Wallet', 'Cash', '2023-03-15')")
        db?.execSQL("INSERT INTO WALLETS(UID, BAL, WNAME, WTYPE, WDATE) VALUES(2, 20000, 'BPI', 'Bank', '2023-03-15')")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}