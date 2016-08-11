package example.tacademy.miniproject.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.tacademy.miniproject.MyApplication;
import example.tacademy.miniproject.data.ChatContract;

/**
 * Created by Tacademy on 2016-08-11.
 */
public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private static final String DB_NAME = "chat_db";
    private static final int DB_VERSION = 1;

    public DBManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + ChatContract.ChatUser.TABLE + "(" +
                ChatContract.ChatUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatUser.COLUMN_SERVER_ID + " INTEGER," +
                ChatContract.ChatUser.COLUMN_NAME + " TEXT," +
                ChatContract.ChatUser.COLUMN_EMAIL + " TEXT NOT NULL," +
                ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE " + ChatContract.ChatMessage.TABLE + "(" +
                ChatContract.ChatMessage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatMessage.COLUMN_USER_ID + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_TYPE + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_MESSAGE + " TEXT," +
                ChatContract.ChatMessage.COLUMN_CREATED + " INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long getUserId(long serverId) {
        String selection = ChatContract.ChatUser.COLUMN_SERVER_ID + " = ?";
        String[] args = {"" + serverId};
        String[] column = {ChatContract.ChatUser._ID};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(ChatContract.ChatUser.TABLE, column, selection, args, null, null, null);
        try {
            if (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndex(ChatContract.ChatUser._ID));
                return id;
            }
        } finally {
            c.close();
        }
        return -1;
        }

//    public Cursor getChatUser() {
//
//
//    }
}
