package com.example.apppengajuansurat;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "surat.db";
    private static final int DB_VERSION = 1;
    private static final String PREF_USER_ID = "user_id";
    private SharedPreferences sharedPreferences;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        sharedPreferences = context.getSharedPreferences("user_id", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id interger PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, userid text, j_kelamin text, password text, role text)");
        db.execSQL("CREATE TABLE form_mhs_aktif(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, nim text, ttl text, " +
                "jurusan text, kelas text, alamat text, nama_ortu text, pekerjaan text, instansi text, alamat_ortu text)");
        db.execSQL("CREATE TABLE form_transkrip(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_mhs text, nim text, ttl text, " +
                "semester text, jurusan text, kelas text)");
        db.execSQL("CREATE TABLE form_legalisir(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_mhs text, nim text, ttl, text, jurusan text, kelas text)");
        db.execSQL("CREATE TABLE pengajuan(id INTEGER PRIMARY KEY AUTOINCREMENT, formId integer, nama text, nim text, tgl_kirim text, status text, keterangan text)");
        db.execSQL("INSERT INTO session(id, login) VALUES (1,'kosong')");
        db.execSQL("INSERT INTO user(id, nama, userid, j_kelamin, password, role) VALUES (1,'Admin Akademik', '1122334455','Pria', '12345', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int db1, int db2) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //check session
    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //upgrade session
    public Boolean upgradeSession(String sessionValues, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        long update = db.update("session", contentValues, "id="+id, null);
        if (update == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String saveLoggedInUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_ID, userId);
        editor.apply();
        return userId;
    }

    public String getLoggedInUserId() {
        return sharedPreferences.getString(PREF_USER_ID, null);
    }

    //insert user
    public Boolean insertUser(String nama, String userid, String j_kelamin, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("userid", userid);
        contentValues.put("j_kelamin", j_kelamin);
        contentValues.put("password", password);
        contentValues.put("role", role);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //Insert Form Mahasiswa Aktif
    public boolean insertFormMahasiswaAktif(String nama, String nim, String ttl, String jurusan, String kelas, String alamat, String nama_ortu, String pekerjaan, String instansi, String alamat_ortu){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("nim", nim);
        contentValues.put("ttl", ttl);
        contentValues.put("jurusan", jurusan);
        contentValues.put("kelas", kelas);
        contentValues.put("alamat", alamat);
        contentValues.put("nama_ortu", nama_ortu);
        contentValues.put("pekerjaan", pekerjaan);
        contentValues.put("instansi", instansi);
        contentValues.put("alamat_ortu", alamat_ortu);
        long insert = db.insert("form_mhs_aktif", null, contentValues);
        if(insert == -1){
            return false;
        }else{
            return  true;
        }
    }

    //check login
    public Boolean checkLogin(String userid, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE userid = ? AND password = ?", new String[]{userid, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Cek Role
    public String checkRole(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM user WHERE userid = ?", new String[]{userid});
        String role = null;
        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
        }
        cursor.close();
        return role;
    }

    //Get User data
    public UserData getUserData (String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE userid = ?", new String[]{userid});
        UserData userData = null;
        if(cursor.moveToFirst()){
            String valueNama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
            String valueUserid = cursor.getString(cursor.getColumnIndexOrThrow("userid"));
            String valueJkelamin = cursor.getString(cursor.getColumnIndexOrThrow("j_kelamin"));
            userData = new UserData(valueNama, valueUserid, valueJkelamin);
        }
        cursor.close();
        return userData;
    }

    public class UserData{
        private String valueNama;
        private String valueUserid;
        private String valueJkelamin;

        public UserData (String valueNama, String valueUserid, String valueJkelamin){
            this.valueNama = valueNama;
            this.valueUserid = valueUserid;
            this.valueJkelamin = valueJkelamin;
        }

        public String getValueNama(){
            return valueNama;
        }
        public String getValueUserid(){
            return valueUserid;
        }
        public String getValueJkelamin(){
            return valueJkelamin;
        }
    }
}
